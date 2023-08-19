package com.ramij.kgs.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ramij.kgs.server", "com.ramij.kgs.core"})
public class KgsApplications {

    public static void main(String[] args) {
        SpringApplication.run(KgsApplications.class, args);
    }
}
