package com.github.xba1k.CANDog.frame;

import com.github.xba1k.CANDOG.util.Utils;

public class CorruptedFrame extends GenericFrame {
    
    public CorruptedFrame(final int frameId, final byte[] frameData) {
        super(frameId, frameData);
    }
    
    @Override
    public String toString() {
        return "CorruptedFrame{" + "frameId=" + this.getFrameId() + ", frameData=" + Utils.hex(this.getFrameData()) + '}';
    }

}
