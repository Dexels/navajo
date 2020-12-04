/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.util.Map;

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.core.ThreadActivityListener;
import com.dexels.navajo.tipi.components.core.TipiThread;

public class TipiThreadActivityLogger extends TipiSwingComponentImpl {

	private static final long serialVersionUID = -9133341806481655664L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiThreadActivityLogger.class);
	private JTextArea tja;

	@Override
	public Object createContainer() {
		tja = new JTextArea();
		myContext.addThreadStateListener(new ThreadActivityListener() {

			private static final long serialVersionUID = 2927003989608693460L;

			@Override
			public void threadActivity(Map<TipiThread, String> threadStateMap,
					TipiThread tt, String state, int queueSize) {
				logger.debug("Thread activity: " + threadStateMap);
				tja.append(threadStateMap.toString());
			}
		});
		return tja;
	}

}
