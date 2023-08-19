package com.ramij.kgs.core.model;

public class ZookeeperHolder {
    private final String zkAddress;
    private final String hostIp;
    private final String hostPort;

    public ZookeeperHolder(String zkAddress, String hostIp, String hostPort) {
        this.zkAddress = zkAddress;
        this.hostIp = hostIp;
        this.hostPort = hostPort;
        init();
    }

    private void init() {
    }
    public int getWorkerId(){
        return 0;
    }

}
