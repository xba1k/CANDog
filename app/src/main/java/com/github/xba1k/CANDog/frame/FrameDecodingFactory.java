package com.github.xba1k.CANDog.frame;

public interface FrameDecodingFactory {
    CANFrame getDecodedFrame(byte[] frameData);
}
