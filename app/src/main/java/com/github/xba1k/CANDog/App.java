package com.github.xba1k.CANDog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.datadog.EnableDatadogMetrics;

@SpringBootApplication
@EnableDatadogMetrics
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.NONE)
                .run(args);
        try {
            Thread.currentThread().join();
        } catch(InterruptedException ex) {
        }
    }
}
