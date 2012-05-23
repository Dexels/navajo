/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStorageManager;

public class TipiNullStorageManager implements TipiStorageManager, Serializable {

	private static final long serialVersionUID = -5812574497740827700L;

	public Navajo getStorageDocument(String id) throws TipiException {
		System.err.println("TipiNullStorageManager: Asked for: " + id);
		return null;
	}

	public void setStorageDocument(String id, Navajo n) throws TipiException {
		System.err.println("TipiNullStorageManager: Stored: " + id);
	}

	public void setInstanceId(String id) {
		// whatever
	}

	public void setContext(TipiContext tc) {

	}

}
