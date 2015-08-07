package com.dexels.navajo.server.enterprise.tribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;

public class WrapCollectorFactory extends GenericThread {

    private static WrapCollector instance;

    private final static Logger logger = LoggerFactory.getLogger(WrapCollectorFactory.class);

    public static void setInstance(WrapCollector wrapper) {
        logger.info("Got a WrapperCollector!");
        instance = wrapper;
    }
    
    public final static WrapCollector getInstance() {

        if (instance != null) {
            return instance;
        }
        logger.warn("No WrapCollector instance!");
        return null;
    }


}
