/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	
	
	@Override
	public Object createContainer() {
		JPanel j = new JPanel();
		j.setLayout(new BorderLayout());
		return j;
	}

	@Override
	public void runAsyncInEventThread(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	@Override
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

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		logger.debug("Adding to TipiTabbedQuestionList container:   " + c
				+ " constraints: " + constraints);
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((Container) getContainer()).add((Component) c,
						BorderLayout.CENTER);
			}
		});
	}

}
