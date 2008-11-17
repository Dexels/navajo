/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class TipiMessageTablePanel extends MessageTablePanel {
    private String messagePath;

	public TipiMessageTablePanel(TipiContext tc, TipiComponent component) {
        super(new TipiMessageTable(tc,component));
	}

	
	public String getMessagePath() {
		return messagePath;
	}

	public void setMessagePath(String messagePath) {
		String old = this.messagePath;
		this.messagePath = messagePath;
		firePropertyChange("messagePath", old, messagePath);
	}
	

}
