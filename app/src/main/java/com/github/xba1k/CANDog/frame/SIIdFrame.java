package com.github.xba1k.CANDog.frame;

public class SIIdFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 8;
    private final int hwVer;
    private final int swVer;
    private final String chemistry;
    private final double capacity;

    public SIIdFrame(final String chemistry, final int hwVer, final double capacity, final int swVer) {
        this.chemistry = chemistry;
        this.hwVer = hwVer;
        this.capacity = capacity;
        this.swVer = swVer;
        this.frameId = FrameType.SI_ID_FRAME.getType();
    }

    public int getHwVer() {
        return hwVer;
    }

    public int getSwVer() {
        return swVer;
    }

    public String getChemistry() {
        return chemistry;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public int getFrameId() {
        return frameId;
    }

    @Override
    public String toString() {
        return "SIIdFrame{" + "hw_ver=" + hwVer + ", sw_ver=" + swVer + ", chemistry=" + chemistry + ", capacity=" + capacity + '}';
    }

}
