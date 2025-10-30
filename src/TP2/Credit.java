package TP2;

import java.time.YearMonth;

public class Credit {
    private String num;
    private YearMonth exp;
    private double solde;

    public Credit(String num, YearMonth exp, double solde) {
        this.num = num;
        setExp(exp);
        this.solde = solde;
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

    public void soustraireSolde(double montant){
        solde -= montant;
    }
    public void addSolde(double montant){
        solde += montant;
    }



    public boolean validCarte(){
        boolean valide = false;
        if(exp.isAfter(YearMonth.now())){
            valide = true;
        }
        return valide;
    }

    public double getSolde() {
        return solde;
    }
}
