package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.*;
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
public class TipiTimerImpl extends TipiComponentImpl implements Runnable {
	private boolean isRunning;
	private long interval = 1000; // standard is one second
	private Thread t;

	public TipiTimerImpl() {
	}

	private void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
			isRunning = true;
		}
	}

	private final void stop() {
		isRunning = false;
		t.interrupt();
		t = null;
	}

	public Object createContainer() {
		return null;
	}

	public void run() {
		while (isRunning) {
			try {
				this.performTipiEvent("onTimer", null, true);
				Thread.sleep(interval);
			} catch (Exception e) {
				System.err.println("Timer had an exception, stopping");
				e.printStackTrace();
				isRunning = false;
			}
		}
		t = null;
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("interval")) {
			if (object instanceof Integer) {
				this.interval = ((Integer) object).longValue();
			}
			if (object != null) {
				this.interval = Long.parseLong(object.toString());
			}
		}
	}

	/**
	 * performMethod
	 * 
	 * @param methodName
	 *            String
	 * @param invocation
	 *            TipiAction
	 * @param event
	 *            TipiEvent
	 * @throws TipiBreakException
	 * @todo Implement this com.dexels.navajo.tipi.TipiComponent method
	 */
	public void performMethod(String methodName, TipiAction invocation, TipiEvent event) throws TipiBreakException {
		if ("start".equals(methodName)) {
			start();
		}
		if ("stop".equals(methodName)) {
			stop();
		}
	}
}
