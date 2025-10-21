package TP2;

public class Piece {


    private int valeur;

    public Piece(int valeur) {
        setValeur(valeur);
    }
    public void setValeur(int valeur){
        if(valeur == 25 || valeur == 100 || valeur == 200) {
            this.valeur = valeur;
        }
    }
    public int getValeur() {
        return valeur;
    }

}