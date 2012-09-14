package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;

@Deprecated

public class TipiQuestionGroup extends TipiBaseQuestionGroup {

	private static final long serialVersionUID = 1296232412976886388L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiQuestionGroup.class);
	
	
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
				logger.error("Error detected",e);
			} catch (InvocationTargetException e) {
				logger.error("Error detected",e);
			}
		}
	}

	public void addToContainer(final Object c, final Object constraints) {
		logger.debug("Adding to TipiTabbedQuestionList container:   " + c
				+ " constraints: " + constraints);
		runSyncInEventThread(new Runnable() {
			public void run() {
				((Container) getContainer()).add((Component) c,
						BorderLayout.CENTER);
			}
		});
	}

}
