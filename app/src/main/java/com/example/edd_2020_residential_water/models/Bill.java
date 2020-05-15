package com.example.edd_2020_residential_water.models;

public class Bill {

    // Variables for Bill model
    private String place;
    private double totalBill;

    public Bill() {
        // Required empty public constructor
    }

    public Bill(String place, double totalBill) {
        this.place = place;
        this.totalBill = totalBill;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
}
