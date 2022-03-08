package com.github.xba1k.CANDog.server;

import com.github.xba1k.CANDog.frame.CANFrame;
import com.github.xba1k.CANDog.frame.SIIdFrame;
import com.github.xba1k.CANDog.frame.SISocFrame;
import com.github.xba1k.CANDog.frame.SIVoltageFrame;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.Tag;
import org.springframework.stereotype.Component;

@Component
public class CANMetricsReporterImpl implements CANMetricsReporter {

    final Logger logger = LoggerFactory.getLogger(CANMetricsReporterImpl.class);
    
    private final MeterRegistry meterRegistry;
    
    public CANMetricsReporterImpl(final MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void processFrame(final String source, final CANFrame frame) {

        final List<Tag> tags = Arrays.asList(Tag.of("source", source));
        
        meterRegistry.counter("CANFrames", Arrays.asList(Tag.of("type", CANFrame.FrameType.forValue(frame.getFrameId()).name()),
                Tag.of("id", String.valueOf(frame.getFrameId())), Tag.of("source", source))).increment();
        
        switch (CANFrame.FrameType.forValue(frame.getFrameId())) {

            case SI_SOC_FRAME: {
                final SISocFrame socFrame = (SISocFrame) frame;
                meterRegistry.gauge("SIStateOfCharge", tags, socFrame.getStateOfCharge());
                meterRegistry.gauge("SIStateOfHealth", tags, socFrame.getStateOfHealth());
                break;
            }
            case SI_ID_FRAME: {
                final SIIdFrame idFrame = (SIIdFrame) frame;
                final List<Tag> extraTags = Arrays.asList(Tag.of("source", source), Tag.of("Chemistry", idFrame.getChemistry()));
                meterRegistry.gauge("SISwVer", extraTags, idFrame.getSw_ver());
                meterRegistry.gauge("SIHwVer", extraTags, idFrame.getHw_ver());
                meterRegistry.gauge("SICapacity", extraTags, idFrame.getCapacity());
                break;
            }
            case SI_VOLTAGE_FRAME: {
                final SIVoltageFrame voltageFrame = (SIVoltageFrame) frame;
                meterRegistry.gauge("SIBatteryCurrent", tags, voltageFrame.getBatteryCurrent());
                meterRegistry.gauge("SIBatteryTemp", tags, voltageFrame.getBatteryTemp());
                meterRegistry.gauge("SIBatteryVoltage", tags, voltageFrame.getBatteryVoltage());
                break;
            }
        }

    }  
    
}
