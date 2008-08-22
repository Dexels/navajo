package com.dexels.navajo.tipi.components.swingimpl;

import java.util.Map;

import javax.swing.JTextArea;

import com.dexels.navajo.tipi.components.core.ThreadActivityListener;
import com.dexels.navajo.tipi.components.core.TipiThread;

public class TipiThreadActivityLogger extends TipiSwingComponentImpl {

	private JTextArea tja;
	@Override
	public Object createContainer() {
		tja = new JTextArea();
		myContext.addThreadStateListener(new ThreadActivityListener(){

			public void threadActivity(Map<TipiThread, String> threadStateMap, TipiThread tt, String state) {
				System.err.println("Thread activity: "+threadStateMap);
				tja.append(threadStateMap.toString());
			}});
		return tja;
	}

}
