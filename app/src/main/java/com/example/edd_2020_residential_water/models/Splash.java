package com.example.edd_2020_residential_water.models;

public class Splash {
    private String mFixture;
    private String mLeaking;
    private double mFlowL;
    private double mFlowML;
    private double mVolume;

    public Splash() {}  // Needed for Firebase

    public Splash(String fixture, String leaking, double flowL, double flowML, double volume) {
        mFixture = fixture;
        mLeaking = leaking;
        mFlowL = flowL;
        mFlowML = flowML;
        mVolume = volume;
    }

    public String getFixture() { return mFixture; }

    public void setFixture(String fixture) { mFixture = fixture; }

    public String getLeaking() { return mLeaking; }

    public void setLeaking(String leaking) { mLeaking = leaking; }

    public double getFlowL() { return mFlowL; }

    public void setFlowL(double flowL) { mFlowL = flowL; }

    public double getFlowML() { return mFlowML; }

    public void setFlowML(double flowML) { mFlowML = flowML; }

    public double getVolume() { return mVolume; }

    public void setVolume(double volume) { mVolume = volume; }
}
