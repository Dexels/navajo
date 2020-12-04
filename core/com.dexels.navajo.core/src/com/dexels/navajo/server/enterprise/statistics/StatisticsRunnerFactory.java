/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.statistics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.util.AuditLog;

import navajocore.Version;

public class StatisticsRunnerFactory {

    private static volatile StatisticsRunnerInterface instance = null;
    private static Object semaphore = new Object();
    private static final Logger logger = LoggerFactory.getLogger(StatisticsRunnerFactory.class);

    public static final StatisticsRunnerInterface getInstance(String storePath, Map parameters, String storeClass) {
        if (instance != null) {
            return instance;
        } else {
            synchronized (semaphore) {

                if (instance == null) {
                    logger.info("Getting statistics runner interface with storePath: {} and parameters: {}", storeClass,
                            parameters);
                    try {
                        instance = getStatisticsRunnerInstanceOSGi();
                        if (instance != null) {
                            logger.info("Acquiring statistics runner from OSGi services succeeded");
                            return instance;
                        }
                        logger.info("Falling back to old school mode.");
                        instance = getStatisticsRunnerInstance(storePath, parameters, storeClass);
                    } catch (ClassNotFoundException e) {
                        logger.warn("Statistics runner not available from classpath.");
                    } catch (Exception e) {
                        AuditLog.log("WARNING: StatisticsRunnner not available", e, Level.WARNING);
                        instance = new DummyStatisticsRunner();
                    }
                }

                return instance;
            }
        }
    }

    private static StatisticsRunnerInterface getStatisticsRunnerInstance(String storePath, Map parameters, String storeClass)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {
        Class<StatisticsRunnerInterface> c = (Class<StatisticsRunnerInterface>) Class
                .forName("com.dexels.navajo.server.statistics.StatisticsRunner");
        StatisticsRunnerInterface dummy = c.newInstance();
        Method m = c.getMethod("getInstance", new Class[] { String.class, Map.class, String.class });
        StatisticsRunnerInterface result = (StatisticsRunnerInterface) m.invoke(dummy, new Object[] { storePath, parameters,
                storeClass });
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static StatisticsRunnerInterface getStatisticsRunnerInstanceOSGi() throws Exception {
        BundleContext bc = null;
        try {
            bc = Version.getDefaultBundleContext();
        } catch (Throwable t) {
            logger.warn("Could not get OSGi instance for statistics runnner. No OSGi?");
            return null;
        }
        if (bc == null) {
            return null;
        }
        ServiceReference[] c = bc.getServiceReferences(StatisticsRunnerInterface.class.getName(), "");
        if (c == null || c.length == 0) {
            return null;
        }
        ServiceReference srir = c[0];
        StatisticsRunnerInterface sri = (StatisticsRunnerInterface) bc.getService(srir);
        if (sri == null) {
            return null;
        }

        return sri;
    }

    public static final void shutdown() {
        if (instance == null) {
            return;
        }
        instance = null;
    }

}
