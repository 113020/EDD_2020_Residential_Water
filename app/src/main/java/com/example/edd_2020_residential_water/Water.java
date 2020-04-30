package com.example.edd_2020_residential_water;

import java.text.SimpleDateFormat;

/**
 * This class is a blueprint for the Water object.
 */
public class Water {
    private int day; // Private 2-digit integer for day (01-31)
    private int month; // Private 2-digit integer for month (01 to 12)
    private int year; // Private 4-digit integer for year (1900 to 9999)
    private int hour; // Private 2-digit integer for hours (00 to 23)
    private int minute; // Private 2-digit integer for minutes (00 to 59)
    private int second; // Private 2-digit integer for seconds (00 to 59)
    private int millisecond; // Private 3-digit integer for milliseconds (000 to 999)
    private String fixture; // Private string for fixture
    private double flowRate; // Private double value for the detected water speed
    private double extent; // Private double value for length of faucet run time (in seconds)
    private boolean leak; // Private boolean for detecting leaks
    private double volumeFlow; // Private boolean for detecting leaks
    private String billMethod; // Private string for method to calculate water bill
    private double waterBill; // Private double value for total projected water bill (in $$)
    private String waterFact; // Private string for water conservation facts

    /**
     * Water class constructor, for testing data display
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     * @param fixture
     * @param flowRate
     * @param extent
     * @param leak
     * @param volumeFlow
     * @param billMethod
     * @param waterBill
     * @param waterFact
     */
    public Water(int day, int month, int year, int hour, int minute, int second, int millisecond,
                 String fixture, double flowRate, double extent, boolean leak,
                 double volumeFlow, String billMethod, double waterBill, String waterFact) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
        this.fixture = fixture;
        this.flowRate = flowRate;
        this.extent = extent;
        this.leak = leak;
        this.volumeFlow = volumeFlow;
        this.billMethod = billMethod;
        this.waterBill = waterBill;
        this.waterFact = waterFact;
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

    // Return the millisecond (000 to 999)
    public int getMillisecond() {
        return millisecond;
    }

    // Modify the millisecond (000 to 999)
    public void setMillisecond(int millisecond) {
        this.millisecond = millisecond;
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
    public double getFlowRate() {
        return flowRate;
    }

    // Modify volumetric flow rate
    public void setFlowRate(double flowRate) {
        this.flowRate = flowRate;
    }

    // Return extent of water use
    public double getExtent() {
        return extent;
    }

    // Modify extent
    public void setExtent(double extent) {
        this.extent = extent;
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

    // Return bill method
    public String getBillMethod() {
        return billMethod;
    }

    // Modify bill method
    public void setBillMethod(String billMethod) {
        this.billMethod = billMethod;
    }

    // Return water bill
    public double getWaterBill() {
        return waterBill;
    }

    // Modify water bill
    public void setWaterBill(double waterBill) {
        this.waterBill = waterBill;
    }

    // Return water fact/suggestion
    public String getWaterFact() {
        return waterFact;
    }

    // Modify water fact/suggestion
    public void setWaterFact(String waterFact) {
        this.waterFact = waterFact;
    }

    //****** These methods return "dd/mm/yyyy...." and "hh:mm ******//
    public String toDateString() {
        String d = day + "";
        String m = month + "";

        if (day < 10) {
            d = "0" + day;
        }
        if (month < 10) {
            m = "0" + month;
        }

        return d + "/" + m + "/" + year;
    }

    public String toTimeString() {
        String h = hour + "";
        String m = minute + "";
        String s = second + "";
        String ms = millisecond + "";

        if (hour < 10) {
            h = "0" + hour;
        }
        if (minute < 10) {
            m = "0" + minute;
        }
        if (second < 10) {
            s = "0" + second;
        }
        if (millisecond < 10) {
            ms = "0" + millisecond;
        }

        return h + ":" + m + ":" + s + ":" + ms;
    }

    @Override
    public String toString() {
        return toDateString() + "," + toTimeString() + "," + fixture + "," + flowRate + "," + extent
                + "," + leak + "," + volumeFlow + "," + billMethod + "," + waterBill + "," + waterFact + "|";
    }

    /*@Override
    public String toString() {
            return day + "/" + month + "/" + year + ": " + (hour) + ":" + (minute);
    }*/
}
