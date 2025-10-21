package TP2;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Scanner;


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
    public String paiement(int piece){

        int montant = transactionCourante.getMontant();
        int duree = transactionCourante.getDuree();
        montant += piece;
        duree += Math.round(60 * ((float) piece / transactionCourante.getTarif()));
        if (duree > 120){
            duree = 120;
        }
        transactionCourante.setDuree(duree);
        transactionCourante.setMontant(montant);

        return "Pour ce montant : " + (double) montant / 100 + "$ \nVous avez cet durée : " + duree + "minutes.";

    }
    public String validCarte(String s, String exp){
        Credit carte = new Credit(s,YearMonth.parse(exp));
        String message;
        if(carte.validCarte()){
//            while( duree == 120){
//                if (s.nextLine() == "+"){
//                    duree += 15;
//                    montant += tarif / 4;
//
//                }
//                else if (s.nextLine() == "-" && duree != 0){
//                    duree -= 15;
//                    montant -= tarif / 4;
//
//                }
//                else if( s.nextLine() == "max"){
//                    duree = 120;
//                    montant = tarif * 2;
//                }
//                else if (s.nextLine() == "confirm") {
//                    confirmed = true;
//                }
//                else if (s.nextLine() == "cancel") {
//                    duree = 0;
//                    montant = 0;
//                    confirmed = true;
//                }
//                System.out.println("Duree : " + duree / 100);
//                System.out.println("Montant : " + montant / 100);
//            }
            message = "Carte valid veuillez continuer.";
            transactionCourante.setTypePaiement("Carte");
        }
        else {
            message = "Carte invalid.";
        }
        return message;

    }
    public String plus(){
        String message = "";
        if (transactionCourante.getDuree() == 120){
            message = "Vous êtes déja au maximum, veuillez confirmer la transaction ou retirer du temps.";
        }
        //else if  {

        //}
        return  message;
    }


}
