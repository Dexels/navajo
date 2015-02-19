package com.dexels.navajo.tipi;

import java.util.Date;

import com.dexels.navajo.document.Navajo;

public class CachedNavajo {
    Navajo n;
    Date cachedAt;

    public CachedNavajo(Navajo n) {
       this(n, new Date());
    }

    public CachedNavajo(Navajo n, Date ts) {
        setNavajo(n);
        setCachedAt(ts);
    }

   /**
    * 
    * @return A copy of the cached Navajo - can be modified without invalidating the cache.
    */
    public Navajo getNavajo() {
        return n.copy();
    }

    /**
     * A copy is made to prevent invalidating the cache by changes to the navajo 
     */
    public void setNavajo(Navajo n) {
        this.n = n.copy();
    }

    public Date getCachedAt() {
        return cachedAt;
    }

    public void setCachedAt(Date cachedAt) {
        this.cachedAt = cachedAt;
    }
    
    protected boolean isValid(int maxAgeHours) {
        Date now = new Date();
        return  (now.getTime() - cachedAt.getTime()) <  (maxAgeHours * 60 * 60 * 1000);
    }

}
