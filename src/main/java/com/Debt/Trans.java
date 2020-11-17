package com.Debt;

public class Trans {
    private int autoID;
    private int userID;
    private String  date;
    private int debtID;
    private int boughtScale;

    public int getAutoID() {
        return autoID;
    }

    public void setAutoID(int autoID) {
        this.autoID = autoID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDebtID() {
        return debtID;
    }

    public void setDebtID(int debtID) {
        this.debtID = debtID;
    }

    public int getBoughtScale() {
        return boughtScale;
    }

    public void setBoughtScale(int boughtScale) {
        this.boughtScale = boughtScale;
    }

}
