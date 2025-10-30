package TP2;

import java.time.YearMonth;
import java.util.Objects;
import java.util.Scanner;



public class Transaction {
    private int duree;
    private int tarif;
    private int montant;
    private Credit carte;
    private String zone;
    private String typePaiement;
    private boolean placeConfirmer;

    //zone G = 4,25/h
    // Lun a Ven 8-23
    // Sam 9-23
    //Dim 13-18
    //zone SQ = 2,25/h
    // Lun a Ven 9-21
    // Sam 9-18

  public Transaction(String zone){
      this.zone = zone;
      tarif = Objects.equals(zone, "SQ") ? 225 : 425;
      duree = 0;
      montant = 0;
      carte = null;
      typePaiement = "inconnu";
      placeConfirmer = false;
  }

    public boolean isPlaceConfirmer() {
        return placeConfirmer;
    }
    public void setPlaceConfirmer(boolean placeConfirmer) {
        this.placeConfirmer = placeConfirmer;
    }

    public void setCarte(Credit carte) {
        this.carte = carte;
    }

    public Credit getCarte() {
        return carte;
    }
    public  void init(){
      duree = 0;
      montant = 0;
      tarif = 0;
      carte = null;
      zone = "";
      typePaiement = "inconnu";
    }

    public int getTarif() {
        return tarif;
    }

    public int getDuree() {
        return duree;
    }

    public int getMontant() {
        return montant;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public String getTypePaiement() {
        return typePaiement;
    }


}