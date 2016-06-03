package com.dexels.navajo.mgmt.statistics;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.server.mgmt.api.ServerStatisticsProvider;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class NavajoRequestsStatistics implements ServerStatisticsProvider, EventHandler {
    private final static Logger logger = LoggerFactory.getLogger(NavajoRequestsStatistics.class);

    private static final String CACHE_NAVAJO_KEY = "Navajo";
    private static final String CACHE_EXCEPTIONS_KEY = "Exceptions";

    private Object sync = new Object();
    private Map<String, LoadingCache<Integer, Integer>> cache = null;

    public void Activate() {
        cache = new HashMap<>();
        LoadingCache<Integer, Integer> navajoCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).softValues()
                .build(new CacheLoader<Integer, Integer>() {
                    public Integer load(Integer key) {
                        return 0;
                    }
                });
        LoadingCache<Integer, Integer> navajoExCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES).softValues()
                .build(new CacheLoader<Integer, Integer>() {
                    public Integer load(Integer key) {
                        return 0;
                    }
                });
        cache.put(CACHE_NAVAJO_KEY, navajoCache);
        cache.put(CACHE_EXCEPTIONS_KEY, navajoExCache);

        logger.info("Navajo Status statistics provider activated");
    }


    @Override
    public String getKey() {
        return "requestcount";
    }

    @Override
    public String getValue() {
        String res = "";

        Integer navajo = 0;
        Integer exceptions = 0;
        synchronized (sync) {
            Map<Integer, Integer> navajoRequests = cache.get(CACHE_NAVAJO_KEY).asMap();
            for (Integer minute : navajoRequests.keySet()) {
                navajo += navajoRequests.get(minute);
            }
            Map<Integer, Integer> exceptionsRequests = cache.get(CACHE_EXCEPTIONS_KEY).asMap();
            for (Integer minute : exceptionsRequests.keySet()) {
                exceptions += exceptionsRequests.get(minute);
            }
        }
        res = exceptions.toString() + "/" + navajo.toString();
        return res;
    }

    @Override
    public void handleEvent(Event event) {
        try {
            int minute = Calendar.getInstance().get(Calendar.MINUTE);

            String type = (String) event.getProperty("type");
            String key = null;
            if (type.equals("navajoexception")) {
                key = CACHE_EXCEPTIONS_KEY;
            } else if (type.equals("navajo")) {
                key = CACHE_NAVAJO_KEY;
            }

            synchronized (sync) {

                LoadingCache<Integer, Integer> currentMap = cache.get(key);

                Integer currentCount = currentMap.get(minute);
                currentMap.put(minute, ++currentCount);
                cache.put(key, currentMap);
            }
        } catch (Exception e) {
            logger.error("Someweird weird happened while trying to handle an event! ", e);

        }
    }

}
