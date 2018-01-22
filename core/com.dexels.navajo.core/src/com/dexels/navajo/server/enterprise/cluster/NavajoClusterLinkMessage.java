package com.dexels.navajo.server.enterprise.cluster;

public class NavajoClusterLinkMessage {
    private final String key;
    private final String msg;
    private final String topic;
    

    public NavajoClusterLinkMessage(String topic, String key, String msg) {
        this.key = key;
        this.msg = msg;
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

    public String getTopic() {
        return topic;
    }

}
