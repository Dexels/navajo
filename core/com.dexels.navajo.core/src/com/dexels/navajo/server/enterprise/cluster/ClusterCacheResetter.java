package com.dexels.navajo.server.enterprise.cluster;


public interface ClusterCacheResetter {
    public void resetUserCredential(String username);
    
    public void resetOauthToken(String token);
}