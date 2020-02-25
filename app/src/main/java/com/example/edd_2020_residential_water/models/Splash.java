package com.example.edd_2020_residential_water.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * This class is a blueprint for the Splash object, which is an Entity of the Room Database.
 * These entities serve really as tables. Each Splash object or entity stores values which
 */
@Entity(tableName = "splashes", primaryKeys = {"fixture", "time_interval"})
public class Splash {

    @ColumnInfo(name = "date") // Column for date: month, day, year
    private String date; // Private string for date
    @ColumnInfo(name = "time") // Column for time
    private String time; // Private string for time
    @ColumnInfo(name = "fixture") // Column for household fixture: either sink faucet or shower
    private String fixture; // Private string for fixture
    @ColumnInfo(name = "water_speed") // Column for the detected water speed
    private double waterSpeed; // Private double value for the detected water speed
    @ColumnInfo(name = "extent_of_use") // Column for how long the faucet was on: in seconds
    private double waterExtent; // Private double value for length of faucet run time
    @ColumnInfo(name = "time_interval") // Column for time interval (hourly, daily, weekly, monthly)
    private String timeInterval; // Private string for time interval of water flow detection
    @ColumnInfo(name = "frequency_of_use_hourly") // Column for how frequently the fixture is used hourly
    private int hourlyFrequency; // Private inteeger for daily frequency of use
    @ColumnInfo(name = "frequency_of_use_daily") // Column for how frequently the fixture is used daily
    private int dailyFrequency; // Private inteeger for daily frequency of use
    @ColumnInfo(name = "frequency_of_use_weekly") // Column for how frequently the fixture is used weekly
    private int weeklyFrequency; // Private inteeger for weekly frequency of use
    @ColumnInfo(name = "frequency_of_use_monthly") // Column for how frequently the fixture is used monthly
    private int monthlyFrequency; // Private inteeger for monthly frequency of use
    @ColumnInfo(name = "frequency_of_use_yearly") // Column for how frequently the fixture is used yearly
    private int yearlyFrequency; // Private inteeger for yearly frequency of use
    @ColumnInfo(name = "leak_frequency") // Column for frequency of leaks occurring.
    private int leakFrequency; // Private integer for frequency of leaks
    @ColumnInfo(name = "water_bill_method") // Method of calculating water bill
    private String billMethod; // Private string for method to calculate water bill
    @ColumnInfo(name = "final_bill") // Projected water bill from water use data
    private double waterBill; // Private double value for total projected water bill (in $$)
    @ColumnInfo(name = "water_fact") // Facts about conserving water, tips and tricks
    private String waterFact; // Private string for water conservation facts


    /**
     * Splash class constructor
     * @param t
     * @param f
     */
    public Splash(String t, String f) {
        timeInterval = t;
        fixture = f;
    }

    /**
     * Splash class constructor, for testing data display
     * @param date
     * @param time
     * @param fixture
     * @param waterSpeed
     * @param waterExtent
     * @param timeInterval
     * @param hourlyFrequency
     * @param dailyFrequency
     * @param weeklyFrequency
     * @param monthlyFrequency
     * @param yearlyFrequency
     * @param leakFrequency
     * @param billMethod
     * @param waterBill
     * @param waterFact
     */
    public Splash(String date, String time, String fixture, double waterSpeed, double waterExtent, String timeInterval,
                  int hourlyFrequency, int dailyFrequency, int weeklyFrequency, int monthlyFrequency, int yearlyFrequency,
                  int leakFrequency, String billMethod, double waterBill, String waterFact) {
        this.date = date;
        this.time = time;
        this.fixture = fixture;
        this.waterSpeed = waterSpeed;
        this.waterExtent = waterExtent;
        this.timeInterval = timeInterval;
        this.hourlyFrequency = hourlyFrequency;
        this.dailyFrequency = dailyFrequency;
        this.weeklyFrequency = weeklyFrequency;
        this.monthlyFrequency = monthlyFrequency;
        this.yearlyFrequency = yearlyFrequency;
        this.leakFrequency = leakFrequency;
        this.billMethod = billMethod;
        this.waterBill = waterBill;
        this.waterFact = waterFact;
    }

    /**
     * Blank constructor
     */
    public Splash() {}

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

    // Return time interval of water flow detection (hourly, daily, weekly, monthly, yearly
    public String getTimeInterval() { return timeInterval; }

    // Return hourly frequency of fixture use
    public int getHourlyFrequency() { return hourlyFrequency; }

    // Return daily frequency of fixture use
    public int getDailyFrequency() { return dailyFrequency; }

    // Return weekly frequency of fixture use
    public int getWeeklyFrequency() { return weeklyFrequency; }

    // Return monthly frequency of fixture use
    public int getMonthlyFrequency() { return monthlyFrequency; }

    // Return yearly frequency of fixture use
    public int getYearlyFrequency() { return yearlyFrequency; }

    // Return frequency of leak occurrences
    public int getLeakFrequency() { return leakFrequency; }

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

    // Modifying time interval
    public void setTimeInterval(String timeInterval) { this.timeInterval = timeInterval; }

    // Modifying hourly frequency of fixture use
    public void setHourlyFrequency(int hourlyFrequency) { this.hourlyFrequency = hourlyFrequency; }

    // Modifying daily frequency of fixture use
    public void setDailyFrequency(int dailyFrequency) { this.dailyFrequency = dailyFrequency; }

    // Modifying weekly frequency of fixture use
    public void setWeeklyFrequency(int weeklyFrequency) { this.weeklyFrequency = weeklyFrequency; }

    // Modifying monthly frequency of fixture use
    public void setMonthlyFrequency(int monthlyFrequency) { this.monthlyFrequency = monthlyFrequency; }

    // Modifying yearly frequency of fixture use
    public void setYearlyFrequency(int yearlyFrequency) { this.yearlyFrequency = yearlyFrequency; }

    // Modifying frequency of leak occurrences
    public void setLeakFrequency(int leakFrequency) { this.leakFrequency = leakFrequency; }

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
                + "," + timeInterval
                + "," + hourlyFrequency
                + "," + dailyFrequency
                + "," + weeklyFrequency
                + "," + monthlyFrequency
                + "," + yearlyFrequency
                + "," + leakFrequency
                + "," + billMethod
                + "," + waterBill
                + "," + waterFact
                + "|";
    }
}
