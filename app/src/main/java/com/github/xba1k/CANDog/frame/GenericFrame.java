package com.github.xba1k.CANDog.frame;

import com.github.xba1k.CANDog.util.Utils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class GenericFrame implements CANFrame {

    private final int frameId;
    public static final int MIN_LENGTH = 1;
    private final byte[] frameData;

    @SuppressFBWarnings("EI2")
    public GenericFrame(final int frameId, final byte[] frameData) {
        this.frameId = frameId;
        this.frameData = frameData;
    }

    @SuppressFBWarnings("EI")
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

