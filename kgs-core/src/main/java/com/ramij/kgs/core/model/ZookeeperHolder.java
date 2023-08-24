package com.ramij.kgs.core.model;

import com.ramij.kgs.core.exceptions.InitializationFailedException;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class ZookeeperHolder {
    private static final int SESSION_TIMEOUT = 5000;
    private static final String PARENT_NODE = "/worker_nodes";

    private final String zkAddress;
    private final String hostIp;
    private final String hostPort;
    private int workerId = -1;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public ZookeeperHolder(String zkAddress, String hostIp, String hostPort) {
        log.info("ZookeeperHolder created with zkAddress: {}, hostIp: {}, hostPort: {}",
                zkAddress, hostIp, hostPort);
        this.zkAddress = zkAddress;
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        init();
    }

    private void init() {
        try (CuratorFramework curatorFramework = createCuratorFramework()) {
            curatorFramework.start();
            log.info("Curator Started:");

            createParentNodeIfNotExists(curatorFramework);

            List<String> existingNodes = curatorFramework.getChildren().forPath(PARENT_NODE);
            String sequentialNodePath = findSequentialNode(existingNodes);

            if (sequentialNodePath == null) {
                // Create a sequential node with IP:port-sequence format
                sequentialNodePath = createSequentialNode(curatorFramework);
                log.info("New sequential node created: " + sequentialNodePath);
                workerId = extractWorkerId(sequentialNodePath);
                saveWorkerIdToFileAsync(workerId);
            } else {
                log.info("Node already exists: " + sequentialNodePath);
                workerId = extractWorkerId(sequentialNodePath);
            }
            log.info("Node ID sequence: " + workerId);
        } catch (Exception e) {
            handleInitializationError(e);
        }
    }

    // Other methods...

    private CuratorFramework createCuratorFramework() {
        return CuratorFrameworkFactory.newClient(zkAddress, new ExponentialBackoffRetry(1000, 3));
    }

    private void createParentNodeIfNotExists(CuratorFramework curatorFramework) throws Exception {
        if (curatorFramework.checkExists().forPath(PARENT_NODE) == null) {
            curatorFramework.create().forPath(PARENT_NODE);
            log.info("Parent Node created:");
        }
    }

    private String findSequentialNode(List<String> existingNodes) {
        for (String node : existingNodes) {
            if (node.startsWith(hostIp + ":" + hostPort + "-")) {
                return node;
            }
        }
        return null;
    }

    private String createSequentialNode(CuratorFramework curatorFramework) throws Exception {
        String sequentialNodePath = curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                .forPath(PARENT_NODE + "/" + hostIp + ":" + hostPort + "-", "data".getBytes());
        return sequentialNodePath;
    }

    private void handleInitializationError(Exception e) {
        log.info("Exception occurred while initializing Zookeeper!", e);
        log.info("Trying to fetch Id from local file");
        workerId = retrieveWorkerIdFromFile();
        log.debug("WorkerId = {}",workerId);
        if (workerId != -1) {
            log.info("Worker Id is Found!");
            return;
        } else {
            log.info("Worker Id is not found");
        }
        throw new InitializationFailedException(e.getMessage());
    }

    private int retrieveWorkerIdFromFile() {
        Path path = Paths.get("workerId.txt");
        if (Files.exists(path)) {
            try {
                String savedWorkerId = Files.readAllLines(path).get(0);
                return Integer.parseInt(savedWorkerId);
            } catch (IOException e) {
                log.error("Error reading workerId.txt: {}", e.getMessage());
            }
        }
        return -1; // Return -1 if file doesn't exist or there's an error
    }

    private int extractWorkerId(String sequentialNodePath) {
        String[] parts = sequentialNodePath.split("-");
        String sequenceNumber = parts[parts.length - 1];
        return Integer.parseInt(sequenceNumber);
    }
    private void saveWorkerIdToFileAsync(int workerId) {
        executorService.submit(() -> {
            Path path = Paths.get("workerId.txt");
            try {
                if (!Files.exists(path) || Files.exists(path) && Files.isRegularFile(path) && Files.size(path) == 0) {
                    Files.write(path, String.valueOf(workerId).getBytes());
                }
            } catch (IOException e) {
                log.error("Error saving workerId to file: {}", e.getMessage());
            }
        });
    }


    public int getWorkerId() {
        return workerId;
    }

}
