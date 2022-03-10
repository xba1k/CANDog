package com.github.xba1k.CANDog.frame;

public class SINameFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 1;
    private final String name;

    public SINameFrame(final String name) {
        this.name = name;
        this.frameId = CANFrame.FrameType.SI_NAME_FRAME.getType();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SINameFrame{" + "name=" + name + '}';
    }

    @Override
    public int getFrameId() {
        return frameId;
    }

}
