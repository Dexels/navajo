package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.SwingConstants;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingProgressBar;

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
public class TipiProgressBar extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3330147644343514894L;
	private TipiSwingProgressBar myProgressBar = null;

	@Override
	public Object createContainer() {
		myProgressBar = new TipiSwingProgressBar();
		myProgressBar.setMinimum(0);
		myProgressBar.setMaximum(100);
		myProgressBar.setValue(0);
		myProgressBar.setStringPainted(true);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myProgressBar;
	}

	@Override
	public Object getContainer() {
		return myProgressBar;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myProgressBar.setString((String) object);
				}
			});
			return;
		}
		if (name.equals("value")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					int value = (int) Float.parseFloat("" + object);
					myProgressBar.setValue(value);
					myProgressBar.setString("" + value + "%");
				}
			});
			return;
		}
		if (name.equals("orientation")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					String or = (String) object;
					if ("horizontal".equals(or)) {
						myProgressBar.setOrientation(SwingConstants.HORIZONTAL);
					}
					if ("vertical".equals(or)) {
						myProgressBar.setOrientation(SwingConstants.VERTICAL);
					}
				}
			});
			return;
		}
		if (name.equals("indeterminate")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myProgressBar.setIndeterminate(((Boolean) object)
							.booleanValue());
					if (!((Boolean) object).booleanValue()) {
						myProgressBar.setMinimum(0);
						myProgressBar.setMaximum(100);
					}
				}
			});
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myProgressBar.getString();
		}
		if (name.equals("value")) {
			return new Integer(myProgressBar.getValue());
		}
		if (name.equals("orientation")) {
			int orientation = myProgressBar.getOrientation();
			switch (orientation) {
			case SwingConstants.HORIZONTAL:
				return "horizontal";
			case SwingConstants.VERTICAL:
				return "vertical";
			}
		}
		return super.getComponentValue(name);
	}
}
