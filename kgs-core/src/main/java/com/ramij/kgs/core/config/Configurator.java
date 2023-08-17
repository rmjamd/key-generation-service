package com.ramij.kgs.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Configurator {
    @Autowired
    Environment env;

    public String getZkHost() {
        return env.getProperty(Properties.ZK_HOST_NAME);
    }

    public String getZkPort() {
        return env.getProperty(Properties.ZK_PORT_NAME);
    }

}
