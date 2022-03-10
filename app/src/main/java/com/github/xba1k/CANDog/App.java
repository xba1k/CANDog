package com.github.xba1k.CANDog;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
        }
    }
}
