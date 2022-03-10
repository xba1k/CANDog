package com.github.xba1k.CANDog.server;

import com.github.xba1k.CANDog.frame.CANFrame;

public interface CANMetricsReporter {
    void processFrame(String source, CANFrame frame);
}
