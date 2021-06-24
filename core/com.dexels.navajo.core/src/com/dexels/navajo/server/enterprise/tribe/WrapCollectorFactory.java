/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;

public class WrapCollectorFactory extends GenericThread {

    private static WrapCollector instance;

    private static final Logger logger = LoggerFactory.getLogger(WrapCollectorFactory.class);

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
