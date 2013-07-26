package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckThreadViolationRepaintManager extends RepaintManager {
	private final static Logger logger = LoggerFactory.getLogger(CheckThreadViolationRepaintManager.class);
	
	// it is recommended to pass the complete check
	private boolean completeCheck = true;

	public boolean isCompleteCheck() {
		return completeCheck;
	}

	public void setCompleteCheck(boolean completeCheck) {
		this.completeCheck = completeCheck;
	}

	@Override
	public synchronized void addInvalidComponent(JComponent component) {
		checkThreadViolations(component);
		super.addInvalidComponent(component);
	}

	@Override
	public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
		checkThreadViolations(component);
		super.addDirtyRegion(component, x, y, w, h);
	}

	private void checkThreadViolations(JComponent c) {
		if (!(SwingUtilities.isEventDispatchThread() || Thread.currentThread()
				.getName().indexOf("Image") != -1)
				&& (completeCheck || c.isShowing())) {
			Exception exception = new Exception("Invalid thread: "
					+ Thread.currentThread().getName());
			boolean repaint = false;
			boolean fromSwing = false;
			StackTraceElement[] stackTrace = exception.getStackTrace();
			for (StackTraceElement st : stackTrace) {
				if (repaint && st.getClassName().startsWith("javax.swing.")) {
					fromSwing = true;
				}
				if ("repaint".equals(st.getMethodName())) {
					repaint = true;
				}
			}
			if (repaint && !fromSwing) {
				// no problems here, since repaint() is thread safe
				return;
			}
			if (logger.isTraceEnabled())
			{
				exception.printStackTrace();
			}
			else
			{
				logger.warn(exception.getMessage());
			}
		}
	}
}