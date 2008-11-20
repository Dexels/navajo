package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import javax.swing.JProgressBar;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

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
public class TipiSlider extends TipiSwingComponentImpl {
	private TipiSwingSlider mySlide;

	public TipiSlider() {
	}

	public Object createContainer() {
		mySlide = new TipiSwingSlider();

		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);

		mySlide.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				try {
					performTipiEvent("onFocusGained", null, false);
				} catch (TipiBreakException e1) {
					e1.printStackTrace();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void focusLost(FocusEvent e) {
				try {
					performTipiEvent("onFocusLost", null, false);
				} catch (TipiBreakException e1) {
					e1.printStackTrace();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}

			}
		});

		mySlide.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					performTipiEvent("onValueChanged", null, false);
				} catch (TipiBreakException e1) {
					e1.printStackTrace();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

		});
		return mySlide;
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("value")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setValue((Integer) object);
				}
			});
		}
		if (name.equals("min")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setMinimum((Integer) object);
				}
			});
		}
		if (name.equals("max")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setMaximum((Integer) object);
				}
			});
		}
		if (name.equals("orientation")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					String or = (String) object;
					if ("horizontal".equals(or)) {
						mySlide.setOrientation(mySlide.HORIZONTAL);
					}
					if ("vertical".equals(or)) {
						mySlide.setOrientation(mySlide.VERTICAL);
					}
				}
			});
			return;
		}
		if (name.equals("snaptoticks")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setSnapToTicks((Boolean)object);
				}
			});
		}
		
		if (name.equals("showticks")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setPaintTicks((Boolean)object);
				}
			});
		}
		if (name.equals("minortick")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setMinorTickSpacing((Integer)object);
				}
			});
		}
		if (name.equals("majortick")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					mySlide.setMajorTickSpacing((Integer)object);
				}
			});
		}


		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		if (name.equals("value")) {
			return mySlide.getValue();
		}
		return super.getComponentValue(name);
	}

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

	}

}
