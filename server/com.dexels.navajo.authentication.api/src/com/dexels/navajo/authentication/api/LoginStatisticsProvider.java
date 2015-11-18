package com.dexels.navajo.authentication.api;

import com.dexels.navajo.authentication.impl.LoginStatistics;

public class LoginStatisticsProvider {

    private static LoginStatistics instance;

    public static void setInstance(LoginStatistics object) {
        instance = object;
    }
    
    public static boolean reachedAbortThreshold(String username, String ip ) {
        if (instance == null) {
            return false;
        }
        return instance.reachedAbortThreshold(username, ip);
    }
    
    public static boolean reachedRateLimitThreshold(String username, String ip ) {
        if (instance == null) {
            return false;
        }
        return instance.reachedRateLimitThreshold(username, ip);
    }
    
    
}
