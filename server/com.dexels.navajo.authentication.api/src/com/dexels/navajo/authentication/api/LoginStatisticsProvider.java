package com.dexels.navajo.authentication.api;

public class LoginStatisticsProvider {

    private static LoginStatistics instance;

    private LoginStatisticsProvider() {
    	// no instances
    }
    
    public static void setInstance(LoginStatistics object) {
        instance = object;
    }
    
    public static boolean reachedAbortThreshold(String username ) {
        if (instance == null) {
            return false;
        }
        return instance.reachedAbortThreshold(username);
    }
    
    public static boolean reachedRateLimitThreshold(String username ) {
        if (instance == null) {
            return false;
        }
        return instance.reachedRateLimitThreshold(username);
    }
}
