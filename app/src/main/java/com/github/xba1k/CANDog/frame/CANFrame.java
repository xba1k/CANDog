package com.github.xba1k.CANDog.frame;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface CANFrame {

    enum FrameType {
        SI_CHARGE_PARAMS_FRAME(0x351),
        SI_VOLTAGE_FRAME(0x356),
        SI_SOC_FRAME(0x355),
        SI_ID_FRAME(0x35F),
        SI_NAME_FRAME(0x35E),
        SI_FAULT_FRAME(0x35A),
        UNKNOWN(0);

        private final int type;
        private static final Map<Integer, FrameType> FRAMETYPES = Arrays
                .stream(FrameType.values())
                .collect(Collectors.toMap(FrameType::getType, Function.identity()));

        FrameType(final int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public static FrameType forValue(final int type) {
            return FRAMETYPES.containsKey(type) ? FRAMETYPES.get(type) : FrameType.UNKNOWN;
        }

    };

    int getFrameId();

}
