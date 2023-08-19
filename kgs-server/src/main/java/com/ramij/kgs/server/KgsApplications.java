package com.ramij.kgs.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.ramij.kgs.server", "com.ramij.kgs.core"})
public class KgsApplications {

    public static void main(String[] args) {
        SpringApplication.run(KgsApplications.class, args);
    }
    @RestController
    public class ShutdownController {

        @PostMapping("/shutdown")
        public void shutdown() {
            System.exit(0);
        }
    }
}
