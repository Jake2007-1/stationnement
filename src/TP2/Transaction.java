package TP2;

import java.util.Scanner;

public class Transaction {
    private int duree;
    private int tarif;
    private int montant;
    private String zone;

    //zone G = 4,25/h
    // Lun a Ven 8-23
    // Sam 9-23
    //Dim 13-18
    //zone SQ = 2,25/h
    // Lun a Ven 9-21
    // Sam 9-18

  public Transaction(String zone){
      this.zone = zone;
      tarif = zone == "SQ" ? 225 : 425;
      duree = 0;
      montant = 0;
  }

  public int paiement(Credit carte){
      if(carte.validCarte()){
          Scanner s = new Scanner(System.in);
          boolean confirmed = false;
          while(!confirmed || duree == 120){
              if (s.nextLine() == "+"){
                  duree += 15;
                  montant += tarif / 4;

              }
              else if (s.nextLine() == "-" && duree != 0){
                  duree -= 15;
                  montant -= tarif / 4;

              }
              else if( s.nextLine() == "max"){
                  duree = 120;
                  montant = tarif * 2;
              }
              else if (s.nextLine() == "confirm") {
                  confirmed = true;
              }
              else if (s.nextLine() == "cancel") {
                  duree = 0;
                  montant = 0;
                  confirmed = true;
              }
              System.out.println("Duree : " + duree / 100);
              System.out.println("Montant : " + montant / 100);
          }
      }
      else {
          System.out.println("Carte invalide");
      }
      return montant;

  }
  public int paiement(Piece piece){
      boolean confirmed = false;
      Scanner s = new Scanner(System.in);
      montant += piece.getValeur();
      duree += Math.round(60 * (piece.getValeur() / tarif));
      if(s.nextLine() == "confirm") confirmed = true;
      while(!confirmed){
        Piece p = new Piece(s.nextInt());
        montant += p.getValeur();
        duree += Math.round(60 * (p.getValeur() / tarif));
        if (duree > 120) {
            duree = 120;
            confirmed = true;
        }
        if(s.nextLine() == "confirm") {
            confirmed = true;
        }
      }
      return montant;
  }

}