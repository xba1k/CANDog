package com.github.xba1k.CANDog.frame;

public class SIIdFrame implements CANFrame {
    
    private final int frameId;
    public static final int MIN_LENGTH = 8;
    private final int hw_ver, sw_ver;
    private final String chemistry;
    private final double capacity;
    
    public SIIdFrame(final String chemistry, final int hw_ver, final double capacity, final int sw_ver) {
        this.chemistry = chemistry;
        this.hw_ver = hw_ver;
        this.capacity = capacity;
        this.sw_ver = sw_ver;
        this.frameId = FrameType.SI_ID_FRAME.getType();
    }    

    public int getHw_ver() {
        return hw_ver;
    }

    public int getSw_ver() {
        return sw_ver;
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
        return "SIIdFrame{" + "hw_ver=" + hw_ver + ", sw_ver=" + sw_ver + ", chemistry=" + chemistry + ", capacity=" + capacity + '}';
    }
    
}
