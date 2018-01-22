package com.dexels.navajo.server.enterprise.cluster;

public class NavajoClusterLinkMessage {
    private String key;
    private String msg;

    public NavajoClusterLinkMessage(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }

}
