package com.Debt;

import org.apache.ibatis.session.SqlSession;
public class Debt {
    private  int autoID;
    private int ID;
    private String name;
    private float netPrice;
    private String clearSpeed;
    private String repayDate;
    private float YTM;
    private int number;

    public int getAutoID() {
        return autoID;
    }

    public void setAutoID(int autoID) {
        this.autoID = autoID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(float netPrice) {
        this.netPrice = netPrice;
    }

    public String getClearSpeed() {
        return clearSpeed;
    }

    public void setClearSpeed(String clearSpeed) {
        this.clearSpeed = clearSpeed;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public float getYTM() {
        return YTM;
    }

    public void setYTM(float YTM) {
        this.YTM = YTM;
    }
//
//    public Debt(int base){
//        this.ID = 10000+base;
//        this.name = "gz" + String.format("%5d",i);
//        Random r1 = new Random();
//        this.netPrice = r1.nextFloat()*5/100 +1;
//
//
//    }
    public String toString(){

        return "autoID:\t"+this.autoID+"\t"+"name:\t"+this.name;
    }
    public Boolean transaction(SqlSession session , Debt debt,int buyCount){
        if(session == null){
            return  false;
        }
        int res = (session.selectOne("selectNumber",debt));
        if(buyCount <=0){
            System.out.println("please input a valid number(>0)");
            return false;
        }else if (buyCount > res){
            System.out.println("please input a valid number(< left number)");
            return false;
        }else {
            debt.setNumber(debt.getNumber() - buyCount);
            session.update("buyDebt",debt);
            //购买完成以后还需要额外与UI控件匹配
            return true;

        }
    }

}
