package com.github.xba1k.CANDog;

import com.github.xba1k.CANDog.configuration.TestConfiguration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class, args = {"--datadog-app-key=dappkey","--datadog-api-key=dapikey","--listen_port=54321"})
public class AppTest {
    
    @Value("${management.metrics.export.datadog.api-key}")
    private String datadogApiKey;
    
    @Value("${management.metrics.export.datadog.application-key}")
    private String datadogAppKey;
    
    @Value("${udp_listen_port}")
    private String listenPort;
    
    @Test
    void testCommandLineArgsPropagate() {
        
        assertEquals("dappkey", datadogAppKey);
        assertEquals("dapikey", datadogApiKey);
        assertEquals("54321", listenPort);
        
    }
    
}
