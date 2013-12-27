package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingSlider;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiSlider extends TipiSwingComponentImpl {

	private static final long serialVersionUID = -8845463487710029071L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSlider.class);
	private TipiSwingSlider mySlide;

	public TipiSlider() {
	}

	@Override
	public Object createContainer() {
		mySlide = new TipiSwingSlider();

		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);

		mySlide.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				try {
					performTipiEvent("onFocusGained", null, false);
				} catch (TipiBreakException e1) {
					logger.error("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					performTipiEvent("onFocusLost", null, false);
				} catch (TipiBreakException e1) {
					logger.error("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}

			}
		});

		mySlide.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					performTipiEvent("onValueChanged", null, false);
				} catch (TipiBreakException e1) {
					logger.error("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

		});
		return mySlide;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("value")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setValue((Integer) object);
				}
			});
		}
		if (name.equals("min")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setMinimum((Integer) object);
				}
			});
		}
		if (name.equals("max")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setMaximum((Integer) object);
				}
			});
		}
		if (name.equals("orientation")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					String or = (String) object;
					if ("horizontal".equals(or)) {
						mySlide.setOrientation(SwingConstants.HORIZONTAL);
					}
					if ("vertical".equals(or)) {
						mySlide.setOrientation(SwingConstants.VERTICAL);
					}
				}
			});
			return;
		}
		if (name.equals("snaptoticks")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setSnapToTicks((Boolean) object);
				}
			});
		}

		if (name.equals("showticks")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setPaintTicks((Boolean) object);
				}
			});
		}
		if (name.equals("minortick")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setMinorTickSpacing((Integer) object);
				}
			});
		}
		if (name.equals("majortick")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					mySlide.setMajorTickSpacing((Integer) object);
				}
			});
		}

		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("value")) {
			return mySlide.getValue();
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

	}

}
