package com.github.xba1k.CANDog.server;

import com.github.xba1k.CANDog.frame.CANFrame;
import com.github.xba1k.CANDog.frame.FrameDecodingFactory;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class UDPMessageProcessor {

    private final Logger logger = LoggerFactory.getLogger(UDPMessageProcessor.class);

    @Autowired
    private FrameDecodingFactory frameDecodingFactory;

    @Autowired
    private CANMetricsReporter metricsReporter;

    public void processMessage(final Message message) {

        final String sourceIp = Optional.ofNullable(message.getHeaders().get("ip_address"))
                .map(Object::toString)
                .orElse("unknown");
        final CANFrame frame = frameDecodingFactory.getDecodedFrame((byte[]) message.getPayload());

        logger.debug(String.format("From: {}, Data: {}"), sourceIp, frame.toString());
        metricsReporter.processFrame(sourceIp, frame);

    }

}
