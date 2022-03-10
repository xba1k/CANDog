package com.github.xba1k.CANDog.frame;

public class SISocFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 6;
    private final int stateOfCharge;
    private final int stateOfHealth;
    private final int stateOfChargeHighPrecision;

    public SISocFrame(final int stateOfCharge, final int stateOfHealth, final int stateOfChargeHighPrecision) {
        this.stateOfCharge = stateOfCharge;
        this.stateOfHealth = stateOfHealth;
        this.stateOfChargeHighPrecision = stateOfChargeHighPrecision;
        this.frameId = FrameType.SI_SOC_FRAME.getType();
    }

    public int getStateOfCharge() {
        return stateOfCharge;
    }

    public int getStateOfHealth() {
        return stateOfHealth;
    }

    public int getStateOfChargeHighPrecision() {
        return stateOfChargeHighPrecision;
    }

    public int getFrameId() {
        return frameId;
    }

    @Override
    public String toString() {
        return "SISocFrame{" + "stateOfCharge=" + stateOfCharge + ", stateOfHealth=" + stateOfHealth + ", stateOfChargeHighPrecision=" + stateOfChargeHighPrecision + '}';
    }

}
