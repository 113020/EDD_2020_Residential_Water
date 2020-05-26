package com.example.edd_2020_residential_water.models;

public class Bill {

    // Variables for Bill model
    private String dateOrTime;
    private boolean leak;
    private double totalVolume;
    private double totalBill;

    public Bill() {
        // Required empty public constructor
    }

    public Bill(String dateOrTime, boolean leak, double totalVolume, double totalBill) {
        this.dateOrTime = dateOrTime;
        this.leak = leak;
        this.totalVolume = totalVolume;
        this.totalBill = totalBill;
    }

    public String getDateOrTime() {
        return dateOrTime;
    }

    public void setDateOrTime(String dateOrTime) {
        this.dateOrTime = dateOrTime;
    }

    public boolean isLeak() {
        return leak;
    }

    public void setLeak(boolean leak) {
        this.leak = leak;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public String toBillString(double totalBill) {
        String billString = "$" + (float) Math.round(totalBill * 100) / 100;
        if (totalBill - (float) Math.round(totalBill * 100) / 100 == 0.00) {
            billString = "$" + totalBill + '0';
        }
        return billString;
    }
}
