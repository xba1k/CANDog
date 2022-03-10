package com.github.xba1k.CANDog.frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class SIFrameDecodingFactoryImplTest {

    final byte[] sampleGenericFrame = new byte[]{5, 0, 0, 0, 5, 7, 8, 9};
    final byte[] sampleVoltageFrame = new byte[]{0x55, 0x03, 0, 0, 0x55, 0, 0x50, 0, 0x34, 0x21};
    final byte[] sampleSocFrame = new byte[]{0x55, 0x03, 0, 0, 0x55, 0, 0x50, 0, 0x34, 0x21};
    final byte[] sampleNameFrame = new byte[]{0x5e, 0x03, 0, 0, 'h', 'e', 'l', 'l', 'o'};
    final byte[] sampleFaultFrame = new byte[]{0x5a, 0x03, 1, 0, 2, 0, 3, 0, 4, 0};
    final byte[] sampleChargeParamsFrame = new byte[]{0x51, 0x03, 0, 0, 0x3e, 0x02, 0x2c, 0x01, (byte) 0xe8, 0x03, (byte) 0xa4, 0x01};
    final byte[] sampleIdFrame = new byte[]{0x5f, 0x03, 0, 0, 0x4c, (byte) 0x69, 0x04, 0x0f, (byte) 0x94, 0x02, 0x11, 0x17};

    final byte[] sampleCorruptedFrame1 = new byte[]{0x51, 0x03, 0, 0, 0x3e, 0x02, 0x2c, 0x01};
    final byte[] sampleCorruptedFrame2 = new byte[]{0x51};

    final FrameDecodingFactory factory = new SIFrameDecodingFactoryImpl();

    @Test
    public void testDecodeGenericFrame() {

        CANFrame frame = factory.getDecodedFrame(sampleGenericFrame);

        assertTrue(frame instanceof GenericFrame);
        assertEquals(5, frame.getFrameId());
        assertEquals(4, ((GenericFrame) frame).getFrameData().length);

    }

    @Test
    public void testDecodeCorruptedFrame1() {

        CANFrame frame = factory.getDecodedFrame(sampleCorruptedFrame1);

        assertTrue(frame instanceof CorruptedFrame);
        assertEquals(0x351, frame.getFrameId());

    }

    @Test
    public void testDecodeCorruptedFrame2() {

        CANFrame frame = factory.getDecodedFrame(sampleCorruptedFrame2);

        assertTrue(frame instanceof CorruptedFrame);
        assertEquals(0, frame.getFrameId());

    }

    @Test
    public void testDecodeCorruptedFrame3() {

        CANFrame frame = factory.getDecodedFrame(new byte[0]);

        assertTrue(frame instanceof CorruptedFrame);
        assertEquals(0, frame.getFrameId());

    }

    @Test
    public void testDecodeNameFrame() {

        CANFrame frame = factory.getDecodedFrame(sampleNameFrame);
        assertTrue(frame instanceof SINameFrame);

        final SINameFrame chargeFrame = (SINameFrame) frame;

        assertEquals(CANFrame.FrameType.SI_NAME_FRAME.getType(), chargeFrame.getFrameId());
        assertEquals("hello", chargeFrame.getName());

    }

    @Test
    public void testDecodeIdFrame() {

        CANFrame frame = factory.getDecodedFrame(sampleIdFrame);
        assertTrue(frame instanceof SIIdFrame);

        final SIIdFrame chargeFrame = (SIIdFrame) frame;

        assertEquals(CANFrame.FrameType.SI_ID_FRAME.getType(), chargeFrame.getFrameId());
        assertEquals(5905, chargeFrame.getSwVer());
        assertEquals(3844, chargeFrame.getHwVer());
        assertEquals("Li", chargeFrame.getChemistry());
        assertEquals(66.0, chargeFrame.getCapacity());

    }

    @Test
    public void testDecodeSocFrame() {

        CANFrame frame = factory.getDecodedFrame(sampleSocFrame);
        assertTrue(frame instanceof SISocFrame);

        final SISocFrame chargeFrame = (SISocFrame) frame;

        assertEquals(CANFrame.FrameType.SI_SOC_FRAME.getType(), chargeFrame.getFrameId());
        assertEquals(85, chargeFrame.getStateOfCharge());
        assertEquals(80, chargeFrame.getStateOfHealth());
        assertEquals(8500, chargeFrame.getStateOfChargeHighPrecision());

    }

    @Test
    public void testDecodeChargeParamsFrame() {

        CANFrame frame = factory.getDecodedFrame(sampleChargeParamsFrame);
        assertTrue(frame instanceof SIChargeParamsFrame);

        final SIChargeParamsFrame chargeFrame = (SIChargeParamsFrame) frame;

        assertEquals(CANFrame.FrameType.SI_CHARGE_PARAMS_FRAME.getType(), chargeFrame.getFrameId());
        assertEquals(42.0, chargeFrame.getFinalDischargeVoltage());
        assertEquals(57.4, chargeFrame.getFinalVoltage());
        assertEquals(30.0, chargeFrame.getMaxChargeCurrent());
        assertEquals(100.0, chargeFrame.getMaxDischargeCurrent());

    }

}
