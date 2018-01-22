package com.dexels.navajo.server.enterprise.cluster;

import java.util.function.Consumer;


public interface NavajoClusterLink {
    public void subscribeNavajoLinkTopic(String navajoLinkConsumerId, String topic, Consumer<NavajoClusterLinkMessage> callback);
    public void unsubscribeNavajoLinkTopic(String navajoLinkConsumerId, String topic);
    
    public void transmit(NavajoClusterLinkMessage message);
}
