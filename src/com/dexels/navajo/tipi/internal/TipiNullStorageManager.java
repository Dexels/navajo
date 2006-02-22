/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

public class TipiNullStorageManager implements TipiStorageManager {


    
    public Navajo getStorageDocument(String id) throws TipiException {
        System.err.println("TipiNullStorageManager: Asked for: "+id);
        return null;
    }

    public void setStorageDocument(String id, Navajo n) throws TipiException {
        System.err.println("TipiNullStorageManager: Stored: "+id);
    }

    public void setInstanceId(String id) {
        // whatever
    }

}
