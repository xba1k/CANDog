package com.github.xba1k.CANDog.configuration;

import com.github.xba1k.CANDog.frame.FrameDecodingFactory;
import com.github.xba1k.CANDog.frame.SIFrameDecodingFactoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

@Configuration
public class Beans {

    @Value("${udp_listen_port}")
    private int udpListenPort;

    @Bean
    public IntegrationFlow processDatagramFlow() {
        return IntegrationFlows.from(new UnicastReceivingChannelAdapter(udpListenPort))
                .handle("UDPMessageProcessor", "processMessage")
                .get();
    }

    @Bean
    @Scope("singleton")
    public FrameDecodingFactory provideFrameDecodingFactory() {
        return new SIFrameDecodingFactoryImpl();
    }

}
