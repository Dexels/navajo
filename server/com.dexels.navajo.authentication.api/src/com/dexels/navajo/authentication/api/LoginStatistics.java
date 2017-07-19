package com.dexels.navajo.authentication.api;

public interface LoginStatistics {

    boolean reachedAbortThreshold(String username);

    boolean reachedRateLimitThreshold(String username);

}
