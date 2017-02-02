package com.dexels.navajo.authentication.impl;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.LoginStatisticsProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class LoginStatistics implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginStatistics.class);
    
    private LoadingCache<String, Integer> cache = null;
    private Integer failedLoginAbortThreshold = 20;
    private Integer failedLoginSlowpoolThreshold = 3;
    
    
    public void activate(Map<String, Object> settings) {
        LoginStatisticsProvider.setInstance(this);
        if (settings.containsKey("failedLoginAbortThreshold")) {
            failedLoginAbortThreshold = Integer.valueOf((String) settings.get("failedLoginAbortThreshold"));

        }
        if (settings.containsKey("failedLoginSlowpoolThreshold")) {
            failedLoginSlowpoolThreshold = Integer.valueOf((String) settings.get("failedLoginSlowpoolThreshold"));

        }

        cache = CacheBuilder.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).softValues().build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }
    
    public void deactivate() {
        cache.cleanUp();
        LoginStatisticsProvider.setInstance(null);
        
    }
    
    public boolean reachedAbortThreshold(String username, String ip) {
        String key = username + ip;
        try {
            Integer count = cache.get(key);
            if (count > failedLoginAbortThreshold) {
                return true;
            }

        } catch (ExecutionException e1) {
            logger.warn("ExecutionException exception on checking failed attemps for {}: ", key, e1);
        }
        return false;
    }

    public boolean reachedRateLimitThreshold(String username, String ip) {
        String key = username + ip;
        try {
            Integer count = cache.get(key);
            if (count > failedLoginSlowpoolThreshold) {
                return true;
            }
        } catch (ExecutionException e1) {
            logger.warn("ExecutionException exception on checking failed attemps for {}: ", key, e1);
        }
        return false;
    }

    @Override
    public void handleEvent(Event e) {
        if (e.getTopic().equals("aaa/failedlogin")) {
            // Failed login
            String username = (String) e.getProperty("username");
            String ip = (String) e.getProperty("ipaddress");

            if (username.equals("")) {
                // cannot log
                return;
            }
            String key = username + ip;
            Integer count;
            try {
                count = cache.get(key);
            } catch (ExecutionException e1) {
                count = 0;
            }
            cache.put(key, ++count);
            logger.debug("Failed attempt for {} - updated count to: {}", key, count);
        }

    }

}
