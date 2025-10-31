package TP2;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;


public class Borne {
    private Transaction transactionCourante;
    private static double banque = 0;
    private String place;
    private boolean placeConfirmer;
    private final DecimalFormat DF;
    public static final String ZONE_SQ = "SQ";
    public static final String ZONE_G = "G";

    public Borne() {
        this.transactionCourante = null;
        place = "";
        placeConfirmer = false;
        DF = new DecimalFormat("0.00$");
    }

    public Transaction getTransactionCourante() {
        return transactionCourante;
    }

    public String getPlace() {
        return place;
    }

    public boolean isPlaceConfirmer() {
        return placeConfirmer;
    }

    public void setPlaceConfirmer(boolean placeConfirmer) {
        this.placeConfirmer = placeConfirmer;
    }

    public String verifStationnement(String placeStationnement){
        boolean zoneG = false;
        boolean zoneSQ = false;
        String message;

        if(placeStationnement.matches("[S][Q][0-9]+")){
            zoneSQ = true;
        }
        else if(placeStationnement.matches("G[0-9]+")){
            zoneG = true;
        }
        if(zoneG) {
            message = creeTransactionZoneG();
            place = placeStationnement;
        }
        else if(zoneSQ){
            message = creeTransactionZoneSQ();
            place = placeStationnement;
        }
        else {
            message ="Place invalide";
            place = "";
        }

        return message;
    }
    public String creeTransactionZoneG(){
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek jour = now.getDayOfWeek();
        LocalTime heure = now.toLocalTime();
        String message;

        if( jour == DayOfWeek.SATURDAY){
            if (heure.isBefore(LocalTime.of(23,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_G);
                message = "Veuillez entrer votre carte de credit ou votre monnaie.";
            }
            else {
                message = "Stationnement gratuit jusqu'a 9:00.";
            }
        } else if (jour == DayOfWeek.SUNDAY) {
            message = "Stationnement gratuit les dimanches.";
        }
        else {
            if(heure.isBefore(LocalTime.of(23,0)) && heure.isAfter(LocalTime.of(8,0))){
                transactionCourante = new Transaction(ZONE_G);
                message = "Veuillez entrer votre carte de credit ou votre monnaie.";
            }
            else {
                message = "Stationnement gratuit entre 23h-8h la semaine.";
            }
        }

        return message;
    }
    public String creeTransactionZoneSQ(){
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek jour = now.getDayOfWeek();
        LocalTime heure = now.toLocalTime();
        String message;

        if( jour == DayOfWeek.SATURDAY){
            if (heure.isBefore(LocalTime.of(18,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_SQ);
                message = "Veuillez entrer votre carte de credit ou votre monnaie.";
            }
            else {
                message = "Stationnement gratuit jusqu'a 9:00.";
            }
        } else if (jour == DayOfWeek.SUNDAY) {
            message = "Stationnement gratuit les dimanches.";
        }
        else {
            if(heure.isBefore(LocalTime.of(21,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_SQ);
                message = "Veuillez entrer votre carte de credit ou votre monnaie.";
            }
            else {
                message = "Stationnement gratuit de 21h-9h la semaine.";
            }
        }

        return message;
    }
    public String plus(int piece){


        transactionCourante.setMontant(transactionCourante.getMontant() + piece);
        transactionCourante.setDuree((int) ((transactionCourante.getMontant() * 60.0) / transactionCourante.getTarif()));
        if (transactionCourante.getDuree() > 120){
            transactionCourante.setDuree(120);
        }
        if(transactionCourante.getTypePaiement() == "inconnu"){
            transactionCourante.setTypePaiement("comptant");
        }

        return "Pour ce montant : " + DF.format((double) transactionCourante.getMontant() / 100) +".\nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";

    }
    public String validCarte(String s, String exp){
        Credit carte = new Credit(s,YearMonth.parse(exp, DateTimeFormatter.ofPattern("MM/yy")), 1000);
        String message;
        if(carte.validCarte()){
            message = "Carte valid veuillez continuer.";
            if (transactionCourante.getTypePaiement() == "inconnu") transactionCourante.setTypePaiement("Carte");
            else transactionCourante.setTypePaiement("Comptant et carte");
            transactionCourante.setCarte(carte);
        }
        else {
            message = "Carte invalid.";
        }
        return message;

    }
    public String plus(){
        String message = "Pour ce montant : " +  DF.format((double) transactionCourante.getMontant() / 100) +".\nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";;
        if (transactionCourante.getDuree() < 120){
            if (transactionCourante.getCarte().getSolde() * 100 >= transactionCourante.getMontant() + 25){
                transactionCourante.setMontant(transactionCourante.getMontant() +  25);
                transactionCourante.getCarte().soustraireSolde((transactionCourante.getMontant() + 25) / 100);
                transactionCourante.setDuree((int) ((transactionCourante.getMontant() * 60.0) / transactionCourante.getTarif()));
                message = "Pour ce montant : " +  DF.format((double) transactionCourante.getMontant() / 100) +".\nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
            }
            else {
                message = "Manque de fond sur votre carte.";
            }

        }

        return message;
    }
    public String moin(){
        if (transactionCourante.getDuree() != 0){
            transactionCourante.setMontant(transactionCourante.getMontant() - 25);
            transactionCourante.getCarte().addSolde((transactionCourante.getMontant() - 25) / 100);
            transactionCourante.setDuree((int) ((transactionCourante.getMontant() * 60.0) / transactionCourante.getTarif()));
        }
        return "Pour ce montant : " + DF.format((double) transactionCourante.getMontant() / 100) +".\nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
    }
    public String max(){
        transactionCourante.setDuree(120);
        transactionCourante.setMontant(transactionCourante.getTarif() * 2);

        return "Pour ce montant : " + DF.format((double) transactionCourante.getMontant() / 100) +".\nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
    }
    public String ok(){
        banque += (double) transactionCourante.getMontant() / 100;
        String message = "Vous avez " + transactionCourante.getDuree() + "minutes au cout de " + DF.format((double) transactionCourante.getMontant() / 100) +".\n Mode de paiement : "  + transactionCourante.getTypePaiement() + ".\nBonne Journée!";
        transactionCourante = null;
        placeConfirmer = false;
        place = "";
        return message;
    }

    public String annule(){
        transactionCourante = null;
        place = "";
        placeConfirmer = false;
        return "Transaction annulé";
    }

    public String genererRaport(){
        String message =  "Vous avez retirer " + DF.format(banque);
        banque = 0;
        return message;
    }



}
