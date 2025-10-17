import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Borne {
    private Transaction transactionCourante;
    private static double banque;

    public static final String ZONE_SQ = "SQ";
    public static final String ZONE_G = "G";

    //zone G = 4,25/h
    // Lun a Ven 8-23
    // Sam 9-23
    //Dim 13-18

    public Transaction getTransactionCourante() {
        return transactionCourante;
    }
    //zone SQ = 2,25/h
    // Lun a Ven 9-21
    // Sam 9-18



    public void verifStationnement(String placeStationnement){
        boolean zoneG = false;
        boolean zoneSQ = false;

        if(placeStationnement.matches("[S][Q][0-9]+")){
            zoneSQ = true;
        }
        else if(placeStationnement.matches("G[0-9]+")){
            zoneG = true;
        }
        if(zoneG) {
            creeTransactionZoneG();
        }
        else if(zoneSQ){
            creeTransactionZoneSQ();
        }
        else {
            System.out.println("Place invalide");
        }
    }
    public void creeTransactionZoneG(){
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek jour = now.getDayOfWeek();
        LocalTime heure = now.toLocalTime();

        if( jour == DayOfWeek.SATURDAY){
            if (heure.isBefore(LocalTime.of(23,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_G);
            }
            else {
                System.out.println("Stationnement gratuit jusqu'a 9:00.");
            }
        } else if (jour == DayOfWeek.SUNDAY) {
            System.out.println("Stationnement gratuit les dimanches.");
        }
        else {
            if(heure.isBefore(LocalTime.of(23,0)) && heure.isAfter(LocalTime.of(8,0))){
                transactionCourante = new Transaction(ZONE_G);
            }
            else {
                System.out.println("Stationnement gratuit entre 23h-8h la semaine.");
            }
        }

    }
    public void creeTransactionZoneSQ(){
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek jour = now.getDayOfWeek();
        LocalTime heure = now.toLocalTime();

        if( jour == DayOfWeek.SATURDAY){
            if (heure.isBefore(LocalTime.of(18,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_SQ);
                System.out.println("Veuillez entrer votre carte de credit ou votre monnaie.");
            }
            else {
                System.out.println("Stationnement gratuit jusqu'a 9:00.");
            }
        } else if (jour == DayOfWeek.SUNDAY) {
            System.out.println("Stationnement gratuit les dimanches.");
        }
        else {
            if(heure.isBefore(LocalTime.of(21,0)) && heure.isAfter(LocalTime.of(9,0))){
                transactionCourante = new Transaction(ZONE_SQ);
                System.out.println("Veuillez entrer votre carte de credit ou votre monnaie.");
            }
            else {
                System.out.println("Stationnement gratuit de 21h-9h la semaine.");
            }
        }

    }


}
