/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dexels.navajo.embedded;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;

public final class FrameworkService {
    private final ServletContext context;
    private Felix felix;

    public FrameworkService(ServletContext context) {
        this.context = context;
    }

    public void start() {
        try {
            System.err.println("About to start FrameworkService");

            doStart();
        } catch (Exception e) {
            log("Failed to start framework", e);
        }
    }

    public void stop() {
        try {
            doStop();
        } catch (Exception e) {
            log("Error stopping framework", e);
        }
    }

    private void doStart() throws Exception {
        Felix tmp = new Felix(createConfig());
        tmp.start();
        this.felix = tmp;
        log("OSGi framework started", null);
        Dictionary<String, Object> contextSettings = new Hashtable<String,Object>();
        contextSettings.put("context.type", "bridged");
        this.felix.getBundleContext().registerService(ServletContext.class, this.context, contextSettings);
    }

    private void doStop() throws Exception {
        if (this.felix != null) {
            this.felix.stop();
        }

        log("OSGi framework stopped", null);
    }

    private Map<String, Object> createConfig() throws Exception {
        Properties props = new Properties();
        props.load(this.context.getResourceAsStream("/WEB-INF/framework.properties"));

        Map<String, Object> map = new HashMap<>();
        for (Object key : props.keySet()) {
            map.put(key.toString(), props.get(key));
        }
        System.err.println("Created config: " + map);
        map.put("felix.servletcontext.path", this.context.getContextPath());
        map.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, Arrays.asList(new ProvisionActivator(this.context)));
        return map;
    }

    private void log(String message, Throwable cause) {
        this.context.log(message, cause);
    }
}
