package TP2;

import java.time.YearMonth;

public class Credit {
    private String num;
    private YearMonth exp;

    public Credit(String num, YearMonth exp) {
        this.num = num;
        setExp(exp);
    }

    private void setExp(YearMonth exp){
        if (12 >= exp.getMonthValue()){
            this.exp = exp;
        }
        else {
            this.exp = exp.withMonth(12);
        }

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
        if(exp.isAfter(YearMonth.now())){
            valide = true;
        }
        return valide;
    }
}
