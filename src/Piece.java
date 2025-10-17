public class Piece {
    private int valeur;

    public Piece(int valeur) {
        this.valeur = valeur;
    }
    public void setValeur(int valeur){
        if(valeur == 25 || valeur == 100 || valeur == 200) {
            this.valeur = valeur;
        }
    }

}