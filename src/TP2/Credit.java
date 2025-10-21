package TP2;

import java.time.YearMonth;

public class Credit {
    private String num;
    private YearMonth exp;

    public Credit(String num, YearMonth exp) {
        this.num = num;
        this.exp = exp;
    }

    public YearMonth getExp() {
        return exp;
    }

    public String getNum() {
        return num;
    }

    private double solde;



    public boolean validCarte(){
        boolean valide = false;
        if(num.matches("[0-9]{16}") && exp.isAfter(YearMonth.now())){
            valide = true;
        }
        return valide;
    }
}
