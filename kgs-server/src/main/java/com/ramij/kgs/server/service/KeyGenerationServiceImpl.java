package com.ramij.kgs.server.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService{
    @Override
    public String getKey() {
        return UUID.randomUUID().toString();
    }
}
