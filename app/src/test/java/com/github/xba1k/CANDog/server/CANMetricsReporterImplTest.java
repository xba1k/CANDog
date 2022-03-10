package com.github.xba1k.CANDog.server;

import com.github.xba1k.CANDog.frame.CANFrame;
import com.github.xba1k.CANDog.frame.CorruptedFrame;
import com.github.xba1k.CANDog.frame.GenericFrame;
import com.github.xba1k.CANDog.frame.SIFaultFrame;
import com.github.xba1k.CANDog.frame.SIIdFrame;
import com.github.xba1k.CANDog.frame.SISocFrame;
import com.github.xba1k.CANDog.frame.SIVoltageFrame;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CANMetricsReporterImplTest {

    final CANFrame sampleGenericFrame = new GenericFrame(5, new byte[]{5, 7, 8, 9});
    final CANFrame sampleVoltageFrame = new SIVoltageFrame(1.0, 2.0, 3);
    final CANFrame sampleSocFrame = new SISocFrame(2, 3, 4);
    final CANFrame sampleFaultFrame = new SIFaultFrame(3, 4, 5, 6);
    final CANFrame sampleIdFrame = new SIIdFrame("LA", 6, 7.0, 8);
    final CANFrame sampleIdFrame2 = new SIIdFrame("LA", 9, 10.0, 11);
    final CANFrame sampleCorruptedFrame = new CorruptedFrame(6, new byte[]{0x51, 0x03, 0, 0, 0x3e, 0x02, 0x2c, 0x01});

    final MeterRegistry meterRegistry = mock(MeterRegistry.class);
    final Counter frameCounter = mock(Counter.class);

    final CANMetricsReporter metricsReporter = new CANMetricsReporterImpl(meterRegistry);

    @Test
    public void testGenericCanFrameCounted() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);

        final List<Tag> expectedTags = Arrays.asList(Tag.of("type", "UNKNOWN"), Tag.of("id", "5"), Tag.of("source", "127.0.0.1"));

        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());

        metricsReporter.processFrame("127.0.0.1", sampleGenericFrame);

        verify(meterRegistry, times(1)).counter(nameCaptor.capture(), tagCaptor.capture());
        verify(frameCounter, times(1)).increment();
        verify(meterRegistry, times(0)).gauge(anyString(), anyList(), anyDouble());
        verify(meterRegistry, times(0)).gauge(anyString(), anyList(), anyInt());

        assertEquals("CANFrames", nameCaptor.getValue());
        assertEquals(expectedTags, tagCaptor.getValue());

    }

    @Test
    public void testCorruptedFrameCounted() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);
        
        final List<Tag> expectedTags = Arrays.asList(Tag.of("type", "UNKNOWN"), Tag.of("id", "6"), Tag.of("source", "127.0.0.1"));
        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());

        metricsReporter.processFrame("127.0.0.1", sampleCorruptedFrame);
        
        verify(meterRegistry, times(1)).counter(nameCaptor.capture(), tagCaptor.capture());
        verify(frameCounter, times(1)).increment();
        verify(meterRegistry, times(0)).gauge(anyString(), anyList(), anyDouble());
        verify(meterRegistry, times(0)).gauge(anyString(), anyList(), anyInt());
        
        assertEquals("CANFrames", nameCaptor.getValue());
        assertEquals(expectedTags, tagCaptor.getValue());

    }

    @Test
    public void testSocFrameCounted() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);

        final List<Tag> expectedTags = Arrays.asList(Tag.of("source", "127.0.0.1"));

        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());
        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Number> valCaptor = ArgumentCaptor.forClass(int.class);

        metricsReporter.processFrame("127.0.0.1", sampleSocFrame);
        verify(frameCounter, times(1)).increment();
        verify(meterRegistry, times(0)).gauge(anyString(), anyList(), anyDouble());
        verify(meterRegistry, times(2)).gauge(nameCaptor.capture(), tagCaptor.capture(), valCaptor.capture());

        assertEquals("SIStateOfCharge", nameCaptor.getAllValues().get(0));
        assertEquals("SIStateOfHealth", nameCaptor.getAllValues().get(1));
        assertEquals(2, valCaptor.getAllValues().get(0).doubleValue());
        assertEquals(3, valCaptor.getAllValues().get(1).doubleValue());
        assertEquals(expectedTags, tagCaptor.getValue());

    }

    @Test
    public void testIdFrameCounted() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);

        final List<Tag> expectedTags = Arrays.asList(Tag.of("source", "127.0.0.1"), Tag.of("Chemistry", "LA"));

        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());
        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor <Number> valCaptor = ArgumentCaptor.forClass(double.class);

        metricsReporter.processFrame("127.0.0.1", sampleIdFrame);
        verify(frameCounter, times(1)).increment();
        verify(meterRegistry, times(3)).gauge(nameCaptor.capture(), tagCaptor.capture(), valCaptor.capture());

        assertEquals("SISwVer", nameCaptor.getAllValues().get(0));
        assertEquals("SIHwVer", nameCaptor.getAllValues().get(1));
        assertEquals("SICapacity", nameCaptor.getAllValues().get(2));

        assertEquals(8, valCaptor.getAllValues().get(0).doubleValue());
        assertEquals(6, valCaptor.getAllValues().get(1).doubleValue());
        assertEquals(7.0, valCaptor.getAllValues().get(2).doubleValue());
        assertEquals(expectedTags, tagCaptor.getValue());

    }

    @Test
    public void testVoltageFrameCounted() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);

        final List<Tag> expectedTags = Arrays.asList(Tag.of("source", "127.0.0.1"));

        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());
        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor doubleValCaptor = ArgumentCaptor.forClass(double.class);

        metricsReporter.processFrame("127.0.0.1", sampleVoltageFrame);
        verify(frameCounter, times(1)).increment();
        verify(meterRegistry, times(3)).gauge(nameCaptor.capture(), tagCaptor.capture(), (double) doubleValCaptor.capture());

        assertEquals("SIBatteryCurrent", nameCaptor.getAllValues().get(0));
        assertEquals("SIBatteryTemp", nameCaptor.getAllValues().get(1));
        assertEquals("SIBatteryVoltage", nameCaptor.getAllValues().get(2));

        assertEquals(2.0, ((Number)doubleValCaptor.getAllValues().get(0)).doubleValue());
        assertEquals(3, ((Number)doubleValCaptor.getAllValues().get(1)).doubleValue());
        assertEquals(1.0, ((Number)doubleValCaptor.getAllValues().get(2)).doubleValue());
        assertEquals(expectedTags, tagCaptor.getValue());

    }
    
    @Test
    public void tesUpdatedValuesPropagate() {

        when(meterRegistry.counter(anyString(), anyList())).thenReturn(frameCounter);

        final List<Tag> expectedTags = Arrays.asList(Tag.of("source", "127.0.0.1"), Tag.of("Chemistry", "LA"));

        final ArgumentCaptor<List<Tag>> tagCaptor = ArgumentCaptor.forClass(expectedTags.getClass());
        final ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor <Number> valCaptor = ArgumentCaptor.forClass(double.class);

        metricsReporter.processFrame("127.0.0.1", sampleIdFrame);
        metricsReporter.processFrame("127.0.0.1", sampleIdFrame2);
        verify(frameCounter, times(2)).increment();
        verify(meterRegistry, times(6)).gauge(nameCaptor.capture(), tagCaptor.capture(), valCaptor.capture());

        assertEquals("SISwVer", nameCaptor.getAllValues().get(3));
        assertEquals("SIHwVer", nameCaptor.getAllValues().get(4));
        assertEquals("SICapacity", nameCaptor.getAllValues().get(5));
        
        assertEquals(11, valCaptor.getAllValues().get(3).doubleValue());
        assertEquals(9, valCaptor.getAllValues().get(4).doubleValue());
        assertEquals(10, valCaptor.getAllValues().get(5).doubleValue());

        assertEquals(expectedTags, tagCaptor.getValue());

    }

}
