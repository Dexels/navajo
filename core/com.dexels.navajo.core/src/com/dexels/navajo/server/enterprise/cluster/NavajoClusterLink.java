package com.dexels.navajo.server.enterprise.cluster;

import java.util.function.Consumer;

public interface NavajoClusterLink {
    public void subscribeTopic(String consumerId, String topic, Consumer<String> callback);
    public void unsubscribeTopic(String consumerId, String topic);
    
    public void transmit(String topic, NavajoClusterLinkMessage message);
}
