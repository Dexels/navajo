/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.authentication.api;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class AbstractLoginStatistics implements LoginStatistics {
    
    protected LoadingCache<String, Integer> cache = null;
    
    // Default values
    protected Integer failedLoginAbortThreshold = 15;
    protected Integer failedLoginSlowpoolThreshold = 3;
    protected Integer expireAfterSeconds = 600; // 10 min
    
    
    public void createCache() {
        cache = CacheBuilder.newBuilder().expireAfterWrite(expireAfterSeconds, TimeUnit.SECONDS).softValues().build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    
    @Override
    public boolean reachedAbortThreshold(String username) {
        Integer count = cache.getUnchecked(username);
        return count > failedLoginAbortThreshold;
    }

    @Override
    public boolean reachedRateLimitThreshold(String username) {
        Integer count = cache.getUnchecked(username);
        return count > failedLoginSlowpoolThreshold;
    }

    

}
