package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Rectangle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;


public class TipiUndecoratedWindow extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 7585699960529984365L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiUndecoratedWindow.class);
	
	TipiSwingPanel myPanel = null;
	private Rectangle myBounds = new Rectangle();

	public Object createContainer() {
		runSyncInEventThread(new Runnable() {

			public void run() {
				myPanel = new TipiSwingPanel(TipiUndecoratedWindow.this);
				TipiHelper th = new TipiSwingHelper();
				th.initHelper(TipiUndecoratedWindow.this);
				addHelper(th);
				myPanel.setOpaque(false);
			}
		});

		return myPanel;
	}

	public void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (name.equals("x")) {
					myBounds.x = (Integer) object;
				}
				if (name.equals("y")) {
					myBounds.y = (Integer) object;
				}
				if (name.equals("h")) {
					myBounds.height = (Integer) object;
				}
				if (name.equals("w")) {
					myBounds.width = (Integer) object;
				}
				myPanel.setBounds(myBounds);
			}
		});
		super.setComponentValue(name, object);
	}

	public void loadData(final Navajo n, final String method)
			throws TipiException {
		myNavajo = n;
		myMethod = method;
		runSyncInEventThread(new Runnable() {
			public void run() {
				try {
					TipiUndecoratedWindow.super.loadData(n, method);
				} catch (TipiException e) {
					logger.error("Error detected",e);
				} catch (TipiBreakException e) {
					logger.error("Error detected",e);
				}

			}
		});
	}

}
