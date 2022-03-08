package com.github.xba1k.CANDog.util;

import com.github.xba1k.CANDOG.util.Utils;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    
    @Test
    public void testConvertBytesToHex() {
        
       final byte[] testBytes = new byte[] { 1, 2, 3, 4, (byte)0xaa, (byte)0xbb, (byte)0xcc, (byte)0xdd };
       final String result = Utils.hex(testBytes);
        
       assertEquals("01 02 03 04 aa bb cc dd", result);
       
    }
    
    @Test
    public void testConvertEmptyBytesToHex() {
        
       final byte[] testBytes = new byte[] {};
       final String result = Utils.hex(testBytes);
        
       assertEquals("", result);
       
    }
    
    @Test
    public void testConvertNullBytesToHex() {
        
       final String result = Utils.hex(null);
        
       assertEquals("", result);
       
    }
    
}
