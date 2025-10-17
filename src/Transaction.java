

public class Transaction {
    private int duree;
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
  }

  public void paiement(Credit carte){
      if(carte.validCarte()){

      }
      else {
          System.out.println("Carte invalide");
      }

  }
  public void paiement(Piece piece){

  }

}