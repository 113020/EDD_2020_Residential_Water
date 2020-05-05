package com.example.edd_2020_residential_water.models;

public class FixturePercentage {

    // Variables for FixturePercentage model
    private String month;
    private String fixtureOverall;
    private double totalVolume;
    private double percentage;

    public FixturePercentage() {
        // Required empty public constructor
    }

    public FixturePercentage(String month, String fixtureOverall, double totalVolume, double percentage) {
        this.month = month;
        this.fixtureOverall = fixtureOverall;
        this.totalVolume = totalVolume;
        this.percentage = percentage;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFixtureOverall() {
        return fixtureOverall;
    }

    public void setFixtureOverall(String fixtureOverall) {
        this.fixtureOverall = fixtureOverall;
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
