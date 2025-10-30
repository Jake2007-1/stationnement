package TP2;

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

    public static final String ZONE_SQ = "SQ";
    public static final String ZONE_G = "G";


    //zone G = 4,25/h
    // Lun a Ven 8-23
    // Sam 9-23
    //Dim 13-18
    //zone SQ = 2,25/h
    // Lun a Ven 9-21
    // Sam 9-18

    public Borne() {
        this.transactionCourante = null;
        place = "";
        placeConfirmer = false;
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

        int montant = transactionCourante.getMontant();
        int duree = transactionCourante.getDuree();
        montant += piece;
        duree += Math.round(60 * ((float) piece / transactionCourante.getTarif()));
        if (duree > 120){
            duree = 120;
        }
        if(transactionCourante.getTypePaiement() == "inconnu"){
            transactionCourante.setTypePaiement("comptant");
        }
        transactionCourante.setDuree(duree);
        transactionCourante.setMontant(montant);

        return "Pour ce montant : " + (double) montant / 100 + "$ \nVous avez cet durée : " + duree + "minutes.";

    }
    public String validCarte(String s, String exp){
        Credit carte = new Credit(s,YearMonth.parse(exp, DateTimeFormatter.ofPattern("MM/yy")), 1000);
        String message;
        if(carte.validCarte()){
            message = "Carte valid veuillez continuer.";
            transactionCourante.setTypePaiement("Carte");
            transactionCourante.setCarte(carte);
        }
        else {
            message = "Carte invalid.";
        }
        return message;

    }
    public String plus(){
        String message = "Pour ce montant : " +  (double) transactionCourante.getMontant() / 100 + "$ \nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";;
        if (transactionCourante.getDuree() < 120){
            if (transactionCourante.getCarte().getSolde() * 100 >= transactionCourante.getMontant() + 25){
                transactionCourante.setMontant(transactionCourante.getMontant() +  25);
                transactionCourante.setDuree(transactionCourante.getDuree() + Math.round(60 * ((float) 25 / transactionCourante.getTarif())));
                transactionCourante.getCarte().soustraireSolde((transactionCourante.getMontant() + 25) / 100);
                message = "Pour ce montant : " +  (double) transactionCourante.getMontant() / 100 + "$ \nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
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
            transactionCourante.setDuree(transactionCourante.getDuree() -  60 * 25 / transactionCourante.getTarif() );
            transactionCourante.getCarte().addSolde((transactionCourante.getMontant() - 25) / 100);
        }
        return "Pour ce montant : " + (double)transactionCourante.getMontant() / 100 + "$ \nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
    }
    public String max(){
        transactionCourante.setDuree(120);
        transactionCourante.setMontant(transactionCourante.getTarif() * 2);

        return "Pour ce montant : " + (double) transactionCourante.getMontant() / 100 + "$ \nVous avez cet durée : " + transactionCourante.getDuree() + "minutes.";
    }
    public String ok(){
        banque += transactionCourante.getMontant();
        String message = "Vous avez " + transactionCourante.getDuree() + "minutes au cout de " + (double) transactionCourante.getMontant() / 100 +"$.\n Mode de paiement : "  + transactionCourante.getTypePaiement() + ".\nBonne Journée!";
        transactionCourante.init();
        placeConfirmer = false;
        place = "";
        return message;
    }

    public String atteintMax(){
        String message = "";
        if (transactionCourante.getDuree() == 120){
            message = "Vous êtes déja au maximum, veuillez confirmer la transaction ou retirer du temps.";
        }
        return  message;
    }



}
