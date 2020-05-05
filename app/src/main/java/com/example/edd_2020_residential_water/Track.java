package com.example.edd_2020_residential_water;

public class Track {

    // Variables for Track model
    private String dateOrTime;
    private boolean leak;
    private double totalVolume;

    public Track() {
        // Required empty public constructor
    }

    public Track(String dateOrTime, boolean leak, double totalVolume) {
        this.dateOrTime = dateOrTime;
        this.leak = leak;
        this.totalVolume = totalVolume;
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
}
