package com.example.edd_2020_residential_water.models;

public class FixturePercentage {

    // Variables for FixturePercentage model
    private String month;
    private boolean leak;
    private double totalVolume;
    private double percentage;

    public FixturePercentage() {
        // Required empty public constructor
    }

    public FixturePercentage(String month, boolean leak, double totalVolume, double percentage) {
        this.month = month;
        this.leak = leak;
        this.totalVolume = totalVolume;
        this.percentage = percentage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
