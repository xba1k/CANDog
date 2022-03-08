package com.github.xba1k.CANDog.frame;

public class SIVoltageFrame implements CANFrame {
    
    private final int frameId;
    public static final int MIN_LENGTH = 6;
    private final double batteryVoltage, batteryCurrent;
    private final int batteryTemp;
    
    public SIVoltageFrame(final double batteryVoltage, final double batteryCurrent, final int batteryTemp) {
        this.batteryVoltage = batteryVoltage;
        this.batteryCurrent = batteryCurrent;
        this.batteryTemp = batteryTemp;
        this.frameId = CANFrame.FrameType.SI_VOLTAGE_FRAME.getType();
    }

    public double getBatteryVoltage() {
        return batteryVoltage;
    }

    public double getBatteryCurrent() {
        return batteryCurrent;
    }

    public int getBatteryTemp() {
        return batteryTemp;
    }

    public int getFrameId() {
        return frameId;
    }
    
    @Override
    public String toString() {
        return "SIVoltageFrame{" + "batteryVoltage=" + batteryVoltage + ", batteryCurrent=" + batteryCurrent + ", batteryTemp=" + batteryTemp + '}';
    }
    
}
