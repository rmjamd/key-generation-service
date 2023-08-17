package com.ramij.kgs.core.service.impl;

import com.ramij.kgs.core.config.Configurator;
import com.ramij.kgs.server.service.IDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SnowflakeIDGenImpl implements IDGen {
    @Autowired
    Configurator config;
    @Override
    public int getWorkerId() {
        return  new Random().nextInt(1024);
    }
}
