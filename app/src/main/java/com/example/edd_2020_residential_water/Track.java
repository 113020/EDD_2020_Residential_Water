package com.example.edd_2020_residential_water;

public class Track {

    // Variables for Track model
    private String dateOrTime;
    private double leakPercent;
    private double totalVolume;

    public Track() {
        // Required empty public constructor
    }

    public Track(String dateOrTime, double leakPercent, double totalVolume) {
        this.dateOrTime = dateOrTime;
        this.leakPercent = leakPercent;
        this.totalVolume = totalVolume;
    }

    public String getDateOrTime() {
        return dateOrTime;
    }

    public void setDateOrTime(String dateOrTime) {
        this.dateOrTime = dateOrTime;
    }

    public double getLeakPercent() {
        return leakPercent;
    }

    public void setLeakPercent(double leakPercent) {
        this.leakPercent = leakPercent;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }
}
