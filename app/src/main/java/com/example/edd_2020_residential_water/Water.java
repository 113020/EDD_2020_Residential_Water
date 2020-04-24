package com.example.edd_2020_residential_water;

/**
 * This class is a blueprint for the Water object, which is an Entity of the Room Database.
 * These entities serve really as tables. Each Water object or entity stores values which
 */
public class Water {
    private String date; // Private string for date
    private String time; // Private string for time when fixture turns on
    private String fixture; // Private string for fixture
    private double flowRate; // Private double value for the detected water speed
    private double extent; // Private double value for length of faucet run time
    private boolean leak; // Private boolean for detecting leaks
    private double volumeFlow; // Private boolean for detecting leaks
    private String billMethod; // Private string for method to calculate water bill
    private double waterBill; // Private double value for total projected water bill (in $$)
    private String waterFact; // Private string for water conservation facts

    /**
     * Water class constructor, for testing data display
     * @param date
     * @param time
     * @param fixture
     * @param flowRate
     * @param extent
     * @param leak
     * @param volumeFlow
     * @param billMethod
     * @param waterBill
     * @param waterFact
     */
    public Water(String date, String time, String fixture, double flowRate, double extent, boolean leak, double volumeFlow, String billMethod, double waterBill, String waterFact) {
        this.date = date;
        this.time = time;
        this.fixture = fixture;
        this.flowRate = flowRate;
        this.extent = extent;
        this.leak = leak;
        this.volumeFlow = volumeFlow;
        this.billMethod = billMethod;
        this.waterBill = waterBill;
        this.waterFact = waterFact;
    }

    // Return date of fixture use
    public String getDate() {
        return date;
    }

    // Modify date
    public void setDate(String date) {
        this.date = date;
    }

    // Return time of fixture use
    public String getTime() {
        return time;
    }

    // Modify time
    public void setTime(String time) {
        this.time = time;
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

    @Override
    public String toString() {
        return date + "," + time + "," + fixture + "," + flowRate + "," + extent
                + "," + leak + "," + volumeFlow + "," + billMethod + "," + waterBill + "," + waterFact + "|";
    }
}
