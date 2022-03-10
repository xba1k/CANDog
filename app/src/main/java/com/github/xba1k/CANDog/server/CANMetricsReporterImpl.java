package com.github.xba1k.CANDog.server;

import com.github.xba1k.CANDog.frame.CANFrame;
import com.github.xba1k.CANDog.frame.SIIdFrame;
import com.github.xba1k.CANDog.frame.SISocFrame;
import com.github.xba1k.CANDog.frame.SIVoltageFrame;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import static java.util.Collections.singletonList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CANMetricsReporterImpl implements CANMetricsReporter {

    final Logger logger = LoggerFactory.getLogger(CANMetricsReporterImpl.class);
    
    private final ConcurrentMap<String, MutableDouble> gaugeCache = new ConcurrentHashMap<>();
    
    private final MeterRegistry meterRegistry;
    
    public CANMetricsReporterImpl(final MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void processFrame(final String source, final CANFrame frame) {

        final List<Tag> tags = singletonList(Tag.of("source", source));
        
        meterRegistry.counter("CANFrames", 
                Arrays.asList(Tag.of("type", CANFrame.FrameType.forValue(frame.getFrameId()).name()),
                Tag.of("id", String.valueOf(frame.getFrameId())), Tag.of("source", source))).increment();
        
        switch (CANFrame.FrameType.forValue(frame.getFrameId())) {

            case SI_SOC_FRAME: {
                final SISocFrame socFrame = (SISocFrame) frame;
                updateGauge("SIStateOfCharge", tags, socFrame.getStateOfCharge());
                updateGauge("SIStateOfHealth", tags, socFrame.getStateOfHealth());
                break;
            }
            case SI_ID_FRAME: {
                final SIIdFrame idFrame = (SIIdFrame) frame;
                final List<Tag> extraTags = Arrays.asList(Tag.of("source", source), Tag.of("Chemistry", idFrame.getChemistry()));
                updateGauge("SISwVer", extraTags, idFrame.getSw_ver());
                updateGauge("SIHwVer", extraTags, idFrame.getHw_ver());
                updateGauge("SICapacity", extraTags, idFrame.getCapacity());
                break;
            }
            case SI_VOLTAGE_FRAME: {
                final SIVoltageFrame voltageFrame = (SIVoltageFrame) frame;
                updateGauge("SIBatteryCurrent", tags, voltageFrame.getBatteryCurrent());
                updateGauge("SIBatteryTemp", tags, voltageFrame.getBatteryTemp());
                updateGauge("SIBatteryVoltage", tags, voltageFrame.getBatteryVoltage());
                break;
            }
        }

    }
    
    private <T> void updateGauge(final String name, final List<Tag> tags, final Number value) {
        final String key = makeMetricKey(name, tags);
        
        Optional.ofNullable(gaugeCache.putIfAbsent(key, new MutableDouble(value)))
                .ifPresent((n) -> n.setNumber(value));
        
        meterRegistry.gauge(name, tags, gaugeCache.get(key));
    }
    
    private String makeMetricKey(final String name, final List<Tag> tags) {
        return name+"_"+tags
                .stream()
                .sorted()
                .map(t -> String.format("%s_%s", t.getKey(), t.getValue()))
                .collect(Collectors.joining("_"));
    }
    
    class MutableDouble extends Number {
        
        private double doubleValue;
        
        public MutableDouble(Number n) {
            setNumber(n);
        }
        
        public MutableDouble(final double doubleValue) {
            this.doubleValue = doubleValue;
        }
        
        public final void setNumber(final Number n) {
            doubleValue = n.doubleValue();
        }
        
        @Override
        public float floatValue() {
            return (float)doubleValue;
        }
        
        @Override
        public long longValue() {
            return (long)doubleValue;
        }
        
        @Override
        public int intValue() {
            return (int)doubleValue;
        }

        @Override
        public double doubleValue() {
            return doubleValue;
        }
        
    }
       
}
