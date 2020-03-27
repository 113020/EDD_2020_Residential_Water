package com.example.edd_2020_residential_water;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * This class is a blueprint for the Water object, which is an Entity of the Room Database.
 * These entities serve really as tables. Each Water object or entity stores values which
 */
@Entity(tableName = "water")
public class Water {

    @NonNull @PrimaryKey
    @ColumnInfo(name = "date") // Column for date: month, day, year
    private String date; // Private string for date
    @NonNull @ColumnInfo(name = "time") // Column for time when fixture turns on
    private String time; // Private string for time when fixture turns on
    @NonNull @ColumnInfo(name = "fixture") // Column for household fixture: either sink faucet or shower
    private String fixture; // Private string for fixture
    @NonNull @ColumnInfo(name = "water_speed") // Column for the detected water speed
    private double waterSpeed; // Private double value for the detected water speed
    @NonNull @ColumnInfo(name = "extent_of_use") // Column for how long the faucet was on: in seconds
    private double waterExtent; // Private double value for length of faucet run time
    @ColumnInfo(name = "leak") // Column for leak occurring.
    private boolean leak; // Private boolean for detecting leaks
    @ColumnInfo(name = "water_bill_method") // Method of calculating water bill
    private String billMethod; // Private string for method to calculate water bill
    @ColumnInfo(name = "final_bill") // Projected water bill from water use data
    private double waterBill; // Private double value for total projected water bill (in $$)
    @ColumnInfo(name = "water_fact") // Facts about conserving water, tips and tricks
    private String waterFact; // Private string for water conservation facts


    /**
     * Water class constructor
     * @param f
     */
    public Water(String f) {
        fixture = f;
    }

    /**
     * Water class constructor, for testing data display
     * @param date
     * @param time
     * @param fixture
     * @param waterSpeed
     * @param waterExtent
     * @param leak
     * @param billMethod
     * @param waterBill
     * @param waterFact
     */
    public Water(String date, String time, String fixture, double waterSpeed, double waterExtent,
                 boolean leak, String billMethod, double waterBill, String waterFact) {
        this.date = date;
        this.time = time;
        this.fixture = fixture;
        this.waterSpeed = waterSpeed;
        this.waterExtent = waterExtent;
        this.leak = leak;
        this.billMethod = billMethod;
        this.waterBill = waterBill;
        this.waterFact = waterFact;
    }

    /**
     * Blank constructor
     */
    @Ignore
    public Water() {}

    // ***** Get methods for returning values ***** //
    // Return date of fixture use
    public String getDate() { return date; }

    // Return clock time when fixture is first turned on
    public String getTime() { return time; }

    // Return fixture being used
    public String getFixture() { return fixture; }

    // Return detected water speed
    public double getWaterSpeed() { return waterSpeed; }

    // Return faucet run time
    public double getWaterExtent() { return waterExtent; }

    // Return whether leak is occurring
    public boolean isLeak() { return leak; }

    // Return water bill calculation method
    public String getBillMethod() { return billMethod; }

    // Return calculated water bill total in $$
    public double getWaterBill() { return waterBill; }

    // Return water fact
    public String getWaterFact() { return waterFact; }

    // ***** Set methods for modifying the values ***** //
    // Modifying the date value (month, day, year)
    public void setDate(String date) { this.date = date; }

    // Modifying the time
    public void setTime(String time) { this.time = time; }

    // Modifying the fixture
    public void setFixture(String fixture) { this.fixture = fixture; }

    // Modifying the water speed
    public void setWaterSpeed(double waterSpeed) { this.waterSpeed = waterSpeed; }

    // Modifying how long fixture is being used
    public void setWaterExtent(double waterExtent) { this.waterExtent = waterExtent; }

    // Modifying boolean value of leak occurring
    public void setLeak(boolean leak) {
        this.leak = leak;
    }

    // Modifying water bill calculate method
    public void setBillMethod(String billMethod) { this.billMethod = billMethod; }

    // Modifying water bill calculated
    public void setWaterBill(double waterBill) { this.waterBill = waterBill; }

    // Modifying water fact
    public void setWaterFact(String waterFact) { this.waterFact = waterFact; }

    @NonNull
    @Override
    public String toString() {
        return date
                + "," + time
                + "," + fixture
                + "," + waterSpeed
                + "," + waterExtent
                + "," + leak
                + "," + billMethod
                + "," + waterBill
                + "," + waterFact
                + "|";
    }
}