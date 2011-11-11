package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;

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
 * @deprecated
 */

public class TipiQuestionGroup extends TipiBaseQuestionGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1296232412976886388L;

	public Object createContainer() {
		JPanel j = new JPanel();
		j.setLayout(new BorderLayout());
		return j;
	}

	public void runAsyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	public void runSyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void addToContainer(final Object c, final Object constraints) {
		System.err.println("Adding to TipiTabbedQuestionList container:   " + c
				+ " constraints: " + constraints);
		runSyncInEventThread(new Runnable() {
			public void run() {
				((Container) getContainer()).add((Component) c,
						BorderLayout.CENTER);
			}
		});
	}

}
