package com.github.xba1k.CANDog.frame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SIFrameDecodingFactoryImpl implements FrameDecodingFactory {

    public static final int IDLENGTH = 4;

    private static final double M_MULTIPLIER = 10;

    private byte[] remainingBytes(final ByteBuffer byteBuffer) {
        final byte[] frameBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(frameBytes);
        return frameBytes;
    }

    private byte[] getBytes(final ByteBuffer byteBuffer, final int count) {
        final byte[] bytes = new byte[count];
        byteBuffer.get(bytes);
        return bytes;
    }

    @Override
    public CANFrame getDecodedFrame(final byte[] frameData) {
        return frameData != null && frameData.length > IDLENGTH
                ? decodeFrame(frameData) : new CorruptedFrame(0, new byte[0]);
    }

    private CANFrame decodeFrame(final byte[] frameData) {

        final ByteBuffer byteBuffer = ByteBuffer.wrap(frameData).order(ByteOrder.LITTLE_ENDIAN);
        final int frameId = byteBuffer.getInt();
        final CANFrame.FrameType frameType = CANFrame.FrameType.forValue(frameId);

        switch (frameType) {

            case SI_CHARGE_PARAMS_FRAME:
                return byteBuffer.remaining() >= SIChargeParamsFrame.MIN_LENGTH
                        ? new SIChargeParamsFrame(byteBuffer.getShort() / M_MULTIPLIER, byteBuffer.getShort() / M_MULTIPLIER, byteBuffer.getShort() / M_MULTIPLIER,
                                byteBuffer.getShort() / M_MULTIPLIER)
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            case SI_VOLTAGE_FRAME:
                return byteBuffer.remaining() >= SIVoltageFrame.MIN_LENGTH
                        ? new SIVoltageFrame(byteBuffer.getShort() / M_MULTIPLIER, byteBuffer.getShort() / M_MULTIPLIER, byteBuffer.getShort())
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            case SI_SOC_FRAME:
                return byteBuffer.remaining() >= SISocFrame.MIN_LENGTH
                        ? new SISocFrame(byteBuffer.getShort(), byteBuffer.getShort(), byteBuffer.getShort())
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            case SI_ID_FRAME:
                return byteBuffer.remaining() >= SIIdFrame.MIN_LENGTH
                        ? new SIIdFrame(new String(getBytes(byteBuffer, 2)), byteBuffer.getShort(), byteBuffer.getShort() / M_MULTIPLIER, byteBuffer.getShort())
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            case SI_NAME_FRAME:
                return byteBuffer.remaining() >= SINameFrame.MIN_LENGTH
                        ? new SINameFrame(new String(remainingBytes(byteBuffer)))
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            case SI_FAULT_FRAME:
                return byteBuffer.remaining() >= SIFaultFrame.MIN_LENGTH
                        ? new SIFaultFrame(byteBuffer.getShort(), byteBuffer.getShort(), byteBuffer.getShort(), byteBuffer.getShort())
                        : new CorruptedFrame(frameId, remainingBytes(byteBuffer));

            default:
                return new GenericFrame(frameId, remainingBytes(byteBuffer));

        }

    }

}
