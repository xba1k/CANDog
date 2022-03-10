package com.github.xba1k.CANDog.frame;

import com.github.xba1k.CANDog.util.Utils;

public class GenericFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 1;
    private final byte[] frameData;

    public GenericFrame(final int frameId, final byte[] frameData) {
        this.frameId = frameId;
        this.frameData = frameData;
    }

    public byte[] getFrameData() {
        return frameData;
    }

    @Override
    public int getFrameId() {
        return frameId;
    }

    @Override
    public String toString() {
        return "GenericFrame{" + "frameId=" + frameId + ", frameData=" + Utils.hex(frameData) + '}';
    }

}

