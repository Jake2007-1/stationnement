import java.time.YearMonth;

public class Credit {
    private String num;
    private YearMonth exp;

    public YearMonth getExp() {
        return exp;
    }

    public String getNum() {
        return num;
    }

    private double solde;



    public boolean validCarte(){
        boolean valide = false;
        if(num.matches("[0-9]{12}") && exp.isBefore(YearMonth.now())){
            valide = true;
        }
        return valide;
    }
}
