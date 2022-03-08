package com.github.xba1k.CANDog.frame;

public class SIFaultFrame implements CANFrame {
    
    private final int frameId;
    public static final int MIN_LENGTH = 8;
    private final int f0, f1, f2, f3;
    
    public SIFaultFrame(final int f0, final int f1, final int f2, final int f3) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.frameId = FrameType.SI_FAULT_FRAME.getType();
    }

    public int getF0() {
        return f0;
    }

    public int getF1() {
        return f1;
    }

    public int getF2() {
        return f2;
    }

    public int getF3() {
        return f3;
    }
    
    @Override
    public int getFrameId() {
        return frameId;
    }

    @Override
    public String toString() {
        return "SIFaultFrame{" + "f0=" + f0 + ", f1=" + f1 + ", f2=" + f2 + ", f3=" + f3 + '}';
    }
    
}
