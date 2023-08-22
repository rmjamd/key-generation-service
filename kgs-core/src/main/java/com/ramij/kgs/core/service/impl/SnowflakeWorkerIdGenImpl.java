package com.ramij.kgs.core.service.impl;

import com.ramij.kgs.core.service.ServerInfoProvider;
import com.ramij.kgs.core.config.Configurator;
import com.ramij.kgs.core.exceptions.InitializationFailedException;
import com.ramij.kgs.core.model.ServerInfo;
import com.ramij.kgs.core.model.ZookeeperHolder;
import com.ramij.kgs.core.service.WorkerIdGen;
import com.ramij.kgs.core.utils.Tools;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class SnowflakeWorkerIdGenImpl implements WorkerIdGen {

    final Configurator config;
    final ServerInfoProvider serverInfoProvider;
    private final ZookeeperHolder holder;


    @Autowired
    public SnowflakeWorkerIdGenImpl(Configurator config, ServerInfoProvider serverInfoProvider) {
        this.config = config;
        this.serverInfoProvider = serverInfoProvider;
        ServerInfo serverInfo = serverInfoProvider.getServerDetails();
        log.info("Server Details:{}", serverInfo);
        ZookeeperHolder zookeeperHolder = null;
        try {
            zookeeperHolder = new ZookeeperHolder(Tools.getFullAddress(config.getZkHost(), config.getZkPort()), serverInfo.getIpAddress(), serverInfo.getPort());
        } catch (InitializationFailedException ex) {
            log.error("Error Occurred while Initializing Zookeeper Holder!");
        }
        holder = zookeeperHolder;
    }

    @Override
    public int getWorkerId() {
        if (holder == null) {
            return new Random().nextInt(1024);
        }
        log.info("Worker Id: {}", holder.getWorkerId());
        return holder.getWorkerId();
    }
}
