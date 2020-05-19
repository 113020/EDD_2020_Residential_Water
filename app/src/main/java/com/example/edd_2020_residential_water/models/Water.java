package com.example.edd_2020_residential_water.models;

/**
 * This class is a blueprint for the Water object.
 */
public class Water {
    private int day; // Private 2-digit integer for day (01 to 31)
    private int month; // Private 2-digit integer for month (01 to 12)
    private int year; // Private 4-digit integer for year (1900 to 9999)
    private int hour; // Private 2-digit integer for hours (00 to 23)
    private int minute; // Private 2-digit integer for minutes (00 to 59)
    private int second; // Private 2-digit integer for seconds (00 to 59)
    private String fixture; // Private string for fixture
    private double flowRateL; // Private double value for the detected water speed
    private double flowRateML; // Private double value for the detected water speed
    private int secondExtent; // Private integer value for length of faucet run time (in seconds)
    private boolean leak; // Private boolean for detecting leaks
    private double volumeFlow; // Private boolean for detecting leaks

    public Water() {} // Private Constructor

    /**
     * Water class constructor, for testing data display
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param second
     * @param fixture
     * @param flowRateL
     * @param flowRateML
     * @param secondExtent
     * @param leak
     * @param volumeFlow
     */
    public Water(int day, int month, int year, int hour, int minute, int second,
                 String fixture, double flowRateL, double flowRateML, int secondExtent, boolean leak,
                 double volumeFlow) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.fixture = fixture;
        this.flowRateL = flowRateL;
        this.flowRateML = flowRateML;
        this.secondExtent = secondExtent;
        this.leak = leak;
        this.volumeFlow = volumeFlow;
    }

    // Return the day (01-31)
    public int getDay() {
        return day;
    }

    // Modify the day (01-31)
    public void setDay(int day) {
        this.day = day;
    }

    // Return the month (01 to 12)
    public int getMonth() {
        return month;
    }

    // Modify the month (01 to 12)
    public void setMonth(int month) {
        this.month = month;
    }

    // Return the year (1900 to 9999)
    public int getYear() {
        return year;
    }

    // Modify the year (1900 to 9999)
    public void setYear(int year) {
        this.year = year;
    }

    // Return the hour (00 to 23)
    public int getHour() {
        return hour;
    }

    // Modify the hour (00 to 23)
    public void setHour(int hour) {
        this.hour = hour;
    }

    // Return the minute (00 to 59)
    public int getMinute() {
        return minute;
    }

    // Modify the minute (00 to 59)
    public void setMinute(int minute) {
        this.minute = minute;
    }

    // Return the second (00 to 59)
    public int getSecond() {
        return second;
    }

    // Modify the second (00 to 59)
    public void setSecond(int second) {
        this.second = second;
    }

    // Return fixture
    public String getFixture() {
        return fixture;
    }

    // Modify fixture
    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    // Return volumetric flow rate
    public double getFlowRateL() {
        return flowRateL;
    }

    // Modify volumetric flow rate
    public void setFlowRateL(double flowRateL) {
        this.flowRateL = flowRateL;
    }

    // Return volumetric flow rate
    public double getFlowRateML() {
        return flowRateML;
    }

    // Modify volumetric flow rate
    public void setFlowRateML(double flowRateML) {
        this.flowRateML = flowRateML;
    }

    // Return extent of water use
    public int getSecondExtent() {
        return secondExtent;
    }

    // Modify extent
    public void setSecondExtent(int secondExtent) {
        this.secondExtent = secondExtent;
    }

    // Return boolean (true = yes leaking, false = no leaks)
    public boolean isLeak() {
        return leak;
    }

    // Modify leak
    public void setLeak(boolean leak) {
        this.leak = leak;
    }

    // Return total volume
    public double getVolumeFlow() {
        return volumeFlow;
    }

    // Modify total volume
    public void setVolumeFlow(double volumeFlow) {
        this.volumeFlow = volumeFlow;
    }

    //****** These methods return "dd/mm/yyyy...." and "hh:mm ******//
    public String toDateString() {
        String d = day + "";
        String m = (month) + "";

        if (day < 9) {
            d = "0" + day;
        }
        if (month < 9) {
            m = "0" + month;
        }

        return d + "/" + m + "/" + year;
    }
    public String toTimeString() {
        String h = hour + "";
        String m = minute + "";
        String s = second + "";

        if (hour < 10) {
            h = "0" + hour;
        }
        if (minute < 10) {
            m = "0" + minute;
        }
        if (second < 10) {
            s = "0" + second;
        }

        return h + ":" + m + ":" + s;
    }

    @Override
    public String toString() {
        return toDateString() + "," + toTimeString() + "," + fixture + "," + ((double)(flowRateL + flowRateML / 1000)) + "," + secondExtent
                + "," + leak + "," + volumeFlow + " | ";
    }
}
