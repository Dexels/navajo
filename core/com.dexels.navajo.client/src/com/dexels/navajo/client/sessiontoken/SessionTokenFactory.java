/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.sessiontoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTokenFactory {

    private static SessionTokenProvider instance = null;

    private static final Logger logger = LoggerFactory.getLogger(SessionTokenFactory.class);

    private SessionTokenFactory() {

    }

    public static synchronized SessionTokenProvider getSessionTokenProvider() {
        if (instance == null) {
        	logger.error("Uninitialized token manager, falling back to default");
            instance = new DefaultSessionTokenProvider();
        }
        return instance;
    }

    public static synchronized void setSessionTokenProvider(SessionTokenProvider provider) {
        instance = provider;
    }

    public static void main(String[] args) {
        SessionTokenFactory.getSessionTokenProvider().reset();
    }

    public static void clearInstance() {
        instance = null;
    }
}
