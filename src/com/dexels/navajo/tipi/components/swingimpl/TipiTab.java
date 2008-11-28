package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiTab extends TipiSwingDataComponentImpl {
	private TipiSwingTab myTab;

	public Object createContainer() {
		myTab = new TipiSwingTab();
		return myTab;
	}

	protected void setComponentValue(String name, Object object) {
		System.err.println("Setting: " + name + " to: " + object);
		if(name.equals("tabIcon")) {
			// Don't know why. The propertyChangeListener seems to miss it when the icon is being set to null.
			myTab.setIconUrl(object);
		}
		super.setComponentValue(name, object);
	}

}
