package com.generateDateTable;
import com.Debt.Debt;
public class DebtWithDate extends Debt{
    private String date;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DebtWithDate(Debt debt){
        super.setNumber(debt.getNumber());
        super.setNetPrice(debt.getNetPrice());
        super.setClearSpeed(debt.getClearSpeed());
        super.setID(debt.getID());
        super.setName(debt.getName());
        super.setRepayDate(debt.getRepayDate());
        super.setYTM(debt.getYTM());
        super.setAutoID(debt.getAutoID());
    }


}

