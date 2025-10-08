public class Transaction {
    String placeStationnement;
    int duree;
    int montant;

    public Transaction(String placeStationnement, int duree, int montant) {
        this.placeStationnement = placeStationnement;
        this.duree = duree;
        this.montant = montant;
    }

    public boolean verifStationnement(){
        return placeStationnement.matches("[G, SQ][0-9]+");
    }
}