package com.github.xba1k.CANDog.frame;

public class SIChargeParamsFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 8;
    private final double finalVoltage;
    private final double maxChargeCurrent;
    private final double maxDischargeCurrent;
    private final double finalDischargeVoltage;

    public SIChargeParamsFrame(final double finalVoltage, final double maxChargeCurrent, final double maxDischargeCurrent, final double finalDischargeVoltage) {
        this.finalVoltage = finalVoltage;
        this.maxChargeCurrent = maxChargeCurrent;
        this.maxDischargeCurrent = maxDischargeCurrent;
        this.finalDischargeVoltage = finalDischargeVoltage;
        this.frameId = CANFrame.FrameType.SI_CHARGE_PARAMS_FRAME.getType();
    }

    public double getFinalVoltage() {
        return finalVoltage;
    }

    public double getMaxChargeCurrent() {
        return maxChargeCurrent;
    }

    public double getMaxDischargeCurrent() {
        return maxDischargeCurrent;
    }

    public double getFinalDischargeVoltage() {
        return finalDischargeVoltage;
    }

    @Override
    public int getFrameId() {
        return frameId;
    }

    @Override
    public String toString() {
        return "SIChargeParamsFrame{" + "finalVoltage=" + finalVoltage + ", maxChargeCurrent=" + maxChargeCurrent + ", maxDischargeCurrent=" + maxDischargeCurrent + ", finalDischargeVoltage=" + finalDischargeVoltage + '}';
    }

}
