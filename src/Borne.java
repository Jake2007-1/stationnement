public class Borne {
    Transaction transactionCourante;
    private static double banque;

    //zone G = 4,25/h
    // Lun a Ven 8-23
    // Sam 9-23
    //Dim 13-18
    //zone SQ = 2,25/h
    // Lun a Ven 9-21
    // Sam 9-18


    public Borne() {

    }
    public boolean verifStationnement(String placeStationnement){
        if(placeStationnement.matches("[G|SQ][0-9]+")){
            this.transactionCourante = new Transaction();
        }
    }
}
