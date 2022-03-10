package com.github.xba1k.CANDog.util;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Utils {

    public static String hex(final byte[] data) {

        if (data == null) {
            return "";
        }

        final ArrayList<String> octets = new ArrayList<>();

        for (byte b : data) {
            octets.add(String.format("%02x", b));
        }

        return octets.stream()
                .collect(Collectors.joining(" "));
    }

}
