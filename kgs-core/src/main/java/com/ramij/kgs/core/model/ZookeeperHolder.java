package com.ramij.kgs.core.model;

import com.ramij.kgs.core.exceptions.InitializationFailedException;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
public class ZookeeperHolder {
    private static final int SESSION_TIMEOUT = 5000;
    private static final String PARENT_NODE = "/worker_nodes";
    private final String zkAddress;
    private final String hostIp;
    private final String hostPort;
    private int workerId = -1;

    public ZookeeperHolder(String zkAddress, String hostIp, String hostPort) {
        log.info("ZookeeperHolder created with zkAddress: {}, hostIp: {}, hostPort: {}",
                zkAddress, hostIp, hostPort);
        this.zkAddress = zkAddress;
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        init();
    }

    private void init() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zkAddress,
                new ExponentialBackoffRetry(1000, 3)
        );
        try {
            curatorFramework.start();
            log.info("Curator Started:");
        } catch (Exception e) {
            // Handle the exception, e.g., log the error or perform custom actions
            log.error("Failed to start CuratorFramework: " + e.getMessage());
        }

        try {
            if (curatorFramework.checkExists().forPath(PARENT_NODE) == null) {
                curatorFramework.create().forPath(PARENT_NODE);
            }
            log.info("Parent Node  created:");
            List<String> existingNodes = curatorFramework.getChildren().forPath(PARENT_NODE);

            String sequentialNodePath = null;
            for (String node : existingNodes) {
                if (node.startsWith(hostIp + ":" + hostPort + "-")) {
                    sequentialNodePath = node;
                    break;
                }
            }

            if (sequentialNodePath == null) {
                // Create a sequential node with IP:port-sequence format
                sequentialNodePath = curatorFramework.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                        .forPath(PARENT_NODE + "/" + hostIp + ":" + hostPort + "-", "data".getBytes());
                System.out.println("New sequential node created: " + sequentialNodePath);
            } else {
                System.out.println("Node already exists: " + sequentialNodePath);
            }

            // Extract sequence number from the node path
            String[] parts = sequentialNodePath.split("-");
            String sequenceNumber = parts[parts.length - 1];
            workerId = Integer.parseInt(sequenceNumber);
            System.out.println("Node ID sequence: " + sequenceNumber);

            curatorFramework.close();
        } catch (Exception e) {
            throw new InitializationFailedException(e.getMessage());
        }
    }
    private int retrieveWorkerIdFromFile() {
        if (Files.exists(Paths.get("workerId.txt"))) {
            try {
                String savedWorkerId = Files.readAllLines(Paths.get("workerId.txt")).get(0);
                return Integer.parseInt(savedWorkerId);
            } catch (IOException e) {
                log.error("Error reading workerId.txt: {}", e.getMessage());
            }
        }
        return -1; // Return -1 if file doesn't exist or there's an error
    }

    private void saveWorkerIdToFile(int workerId) {
        try {
            Files.write(Paths.get("workerId.txt"), String.valueOf(workerId).getBytes());
        } catch (IOException e) {
            log.error("Error saving workerId to file: {}", e.getMessage());
        }
    }
    public int getWorkerId() {
        return workerId;
    }

}
