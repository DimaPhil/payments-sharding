package com.yandex.payments.sharding.core.distributor.impl;

public class Shard {
    private final String ipAddress;
    private final int port;

    public Shard() {
        this.ipAddress = "10.0.0.1";
        this.port = 8080;
    }

    public Shard(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
