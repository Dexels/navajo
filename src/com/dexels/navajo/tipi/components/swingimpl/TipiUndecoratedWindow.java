package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import com.dexels.navajo.document.*;
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
public class TipiUndecoratedWindow extends TipiSwingDataComponentImpl {
	TipiSwingPanel myPanel = null;
	private Rectangle myBounds = new Rectangle();
	public Object createContainer() {
		runSyncInEventThread(new Runnable(){

			public void run() {
				myPanel = new TipiSwingPanel();
				TipiHelper th = new TipiSwingHelper();
				th.initHelper(TipiUndecoratedWindow.this);
				addHelper(th);
				myPanel.setOpaque(false);
			}});

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

	public void loadData(final Navajo n, final String method) throws TipiException {
		myNavajo = n;
		myMethod = method;
		runSyncInEventThread(new Runnable() {
			public void run() {
				try {
					TipiUndecoratedWindow.super.loadData(n, method);
				} catch (TipiException e) {
					e.printStackTrace();
				} catch (TipiBreakException e) {
					e.printStackTrace();
				}

			}
		});
	}

}
