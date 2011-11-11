package com.dexels.navajo.tipi.components.swingimpl;

import java.util.Map;

import javax.swing.JTextArea;

import com.dexels.navajo.tipi.components.core.ThreadActivityListener;
import com.dexels.navajo.tipi.components.core.TipiThread;

public class TipiThreadActivityLogger extends TipiSwingComponentImpl {

	private static final long serialVersionUID = -9133341806481655664L;
	private JTextArea tja;

	@Override
	public Object createContainer() {
		tja = new JTextArea();
		myContext.addThreadStateListener(new ThreadActivityListener() {

			private static final long serialVersionUID = 2927003989608693460L;

			public void threadActivity(Map<TipiThread, String> threadStateMap,
					TipiThread tt, String state, int queueSize) {
				System.err.println("Thread activity: " + threadStateMap);
				tja.append(threadStateMap.toString());
			}
		});
		return tja;
	}

}
