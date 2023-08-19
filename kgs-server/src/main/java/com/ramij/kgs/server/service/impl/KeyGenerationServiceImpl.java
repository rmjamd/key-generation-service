package com.ramij.kgs.server.service.impl;

import com.ramij.kgs.core.service.WorkerIdGen;
import com.ramij.kgs.core.utils.SnowflakeIdGenerator;
import com.ramij.kgs.server.service.KeyGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyGenerationServiceImpl implements KeyGenerationService {
    @Autowired
    WorkerIdGen workerIdGen;

    @Override
    public String getKey() {
        int workerId = workerIdGen.getWorkerId();
        return String.valueOf(SnowflakeIdGenerator.generateId(workerId));
    }
}
