/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
