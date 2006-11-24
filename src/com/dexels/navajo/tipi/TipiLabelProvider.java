/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;

public interface TipiLabelProvider {
    public String getLabel(String locale, String applicationId, String id) throws TipiException;
  
}

