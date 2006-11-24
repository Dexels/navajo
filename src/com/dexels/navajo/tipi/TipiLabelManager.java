/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;

public interface TipiLabelManager {
    public String getLabel(String locale, String id) throws TipiException;
    public void setInstanceId(String id);
    public void setApplicationId(String id);
}

