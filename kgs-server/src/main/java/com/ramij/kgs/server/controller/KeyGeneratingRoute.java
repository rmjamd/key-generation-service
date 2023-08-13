package com.ramij.kgs.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kgs")
public class KeyGeneratingRoute {
    @GetMapping("/key")
    public String getKey() {
        return "Key Generation Service is not created";
    }

}
