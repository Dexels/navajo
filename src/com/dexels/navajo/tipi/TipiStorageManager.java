/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;

public interface TipiStorageManager {
    public Navajo getStorageDocument(String id) throws TipiException;
    public void setStorageDocument(String id, Navajo n) throws TipiException;
    public void setInstanceId(String id);
    
}
