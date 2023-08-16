package com.ramij.kgs.server.service;

import com.ramij.kgs.core.utils.SnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {
    @Override
    public String getKey() {
        int workerId = new Random().nextInt(1024);
        return String.valueOf(SnowflakeIdGenerator.generateId(workerId));
    }
}
