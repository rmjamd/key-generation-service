package com.ramij.kgs.server.controller;

import com.ramij.kgs.server.service.KeyGenerationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/kgs")
public class KeyGeneratingRoute {
    @Autowired
    KeyGenerationService kgs;

    @GetMapping("/key")
    public String getKey() {
        log.info("Received request to generate a key.");

        String key = kgs.getKey();
        log.info("Generated key: {}", key);

        return key;
    }

}
