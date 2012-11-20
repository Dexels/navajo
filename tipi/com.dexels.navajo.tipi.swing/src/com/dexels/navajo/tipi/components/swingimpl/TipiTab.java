package com.dexels.navajo.tipi.components.swingimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingTab;


public class TipiTab extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -6335980072890167067L;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiTab.class);
	private TipiSwingTab myTab;

	public Object createContainer() {
		myTab = new TipiSwingTab();
		return myTab;
	}

	protected void setComponentValue(String name, Object object) {
		logger.debug("Setting: " + name + " to: " + object);
		if (name.equals("tabIcon")) {
			// Don't know why. The propertyChangeListener seems to miss it when
			// the icon is being set to null.
			myTab.setIconUrl(object);
		}
		super.setComponentValue(name, object);
	}

}
