/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.embed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;

import com.dexels.navajo.tipi.components.echoimpl.BorderLayout;
import com.dexels.navajo.tipi.components.echoimpl.TipiPanel;
import com.dexels.navajo.tipi.internal.TipiLayout;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoStandaloneToplevel extends TipiPanel {

	private static final long serialVersionUID = 3945778439490301909L;
	private final ContentPane myPanel; // = new JPanel();
	private final BorderLayout myLayout = new BorderLayout();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiEchoStandaloneToplevel.class);
	
	public TipiEchoStandaloneToplevel(ContentPane jj) {
		// myPanel.setLayout(myLayout);
		super.setName("init");
		if (jj == null) {
			myPanel = new ContentPane();
		} else {
			myPanel = jj;
		}
		setId("init");
		initContainer();
	}

	public TipiEchoStandaloneToplevel() {
		this(null);
		// super.setName("init");
		// myPanel = new ContentPane();
		// setId("init");
		// initContainer();
	}

	public void addToContainer(final Object c, final Object constraints) {
		// runAsyncInEventThread(new Runnable(){

		// public void run() {
		if (myPanel != null) {
			// logger.info("Adding to toplevel: "+c.getClass()+
			// " -- "+c.hashCode());
			logger.info("ADDING TO PANEL: "
					+ myPanel.getComponentCount() + " - " + c);
			try {
				myPanel.add((Component) c);
			} catch (RuntimeException e) {
				logger.info("Whatever");
				logger.error("Error: ",e);
			}
			logger.info("BEWArE: Tiplet hack");

		}
		// }});

	}

	public void setLayout(TipiLayout tl) {
		// no way jose
	}

	public Object getContainerLayout() {
		return myLayout;
	}

	public void setContainerLayout(Object o) {
		// no way jose
	}

	public Object createContainer() {
		return myPanel;
	}

	public Component getGlassPane() {
		return null;
	}

	public void setGlassPane(Component glassPane) {
	}

	// public ContentPane getContentPane() {
	// return myPanel;
	// }
	//
	// public void setContentPane(ContentPane contentPane) {
	// }
	//
}
