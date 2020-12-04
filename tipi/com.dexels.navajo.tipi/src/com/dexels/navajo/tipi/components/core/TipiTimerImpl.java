/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiTimerImpl extends TipiHeadlessComponentImpl implements
		Runnable {
	private static final long serialVersionUID = 4898398670800512047L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTimerImpl.class);
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

	@Override
	public void run() {
		while (isRunning) {
			try {
				this.performTipiEvent("onTimer", null, true);
				Thread.sleep(interval);
			} catch (Exception e) {
				logger.error("Timer had an exception, stopping",e);
				isRunning = false;
			}
		}
		t = null;
	}

	@Override
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

	@Override
	protected void performComponentMethod(String methodName,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		if ("start".equals(methodName)) {
			start();
		}
		if ("stop".equals(methodName)) {
			stop();
		}
	}

}
