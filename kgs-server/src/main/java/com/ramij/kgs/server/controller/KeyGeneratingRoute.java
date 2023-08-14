package com.ramij.kgs.server.controller;

import com.ramij.kgs.server.service.KeyGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kgs")
public class KeyGeneratingRoute {
    @Autowired
    KeyGenerationService kgs;

    @GetMapping("/key")
    public String getKey() {
        return kgs.getKey();
    }

}
