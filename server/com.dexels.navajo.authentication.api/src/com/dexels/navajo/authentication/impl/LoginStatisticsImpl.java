/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.authentication.impl;

import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.authentication.api.AbstractLoginStatistics;
import com.dexels.navajo.authentication.api.LoginStatisticsProvider;

public class LoginStatisticsImpl extends AbstractLoginStatistics implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginStatisticsImpl.class);

    public void activate(Map<String, Object> settings) {
        LoginStatisticsProvider.setInstance(this);
        if (settings.containsKey("failedLoginAbortThreshold")) {
            failedLoginAbortThreshold = Integer.valueOf((String) settings.get("failedLoginAbortThreshold"));

        }
        if (settings.containsKey("failedLoginSlowpoolThreshold")) {
            failedLoginSlowpoolThreshold = Integer.valueOf((String) settings.get("failedLoginSlowpoolThreshold"));
        }
        if (settings.containsKey("expireAfterSeconds")) {
            expireAfterSeconds = Integer.valueOf((String) settings.get("expireAfterSeconds"));
        }

        createCache();
    }
    
    public void deactivate() {
        cache.cleanUp();
        LoginStatisticsProvider.setInstance(null);
        
    }

    @Override
    public void handleEvent(Event e) {
        if (e.getTopic().equals("aaa/failedlogin")) {
            // Failed login
            String username = (String) e.getProperty("username");

            if (username.equals("")) {
                // cannot log
                return;
            }
            Integer count = cache.getUnchecked(username);
            cache.put(username, ++count);
            logger.debug("Failed attempt for {} - updated count to: {}", username, count);
        }
    }

}
