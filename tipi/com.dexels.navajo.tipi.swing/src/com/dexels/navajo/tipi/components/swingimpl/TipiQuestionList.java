/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.components.question.TipiBaseQuestionList;

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
public abstract class TipiQuestionList extends TipiBaseQuestionList {
	// private String messagePath = null;
	// private String questionDefinitionName = null;
	// private String questionGroupDefinitionName = null;
	//
	// private static final String MODE_TABS = "tabs";
	// private static final String MODE_PANEL = "panel";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4882260490569219046L;

	// private String groupMode = MODE_PANEL;
	public TipiQuestionList() {
	}

	@Override
	protected Object getGroupConstraints(Message groupMessage) {
		return null;
	}

	@Override
	public void runSyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
				// logger.debug("Interrupted");
			}
		}
	}

}
