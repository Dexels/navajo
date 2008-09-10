package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.core.*;

public class TipiThreadActivityLogger extends TipiSwingComponentImpl {

	private JTextArea tja;
	@Override
	public Object createContainer() {
		tja = new JTextArea();
		myContext.addThreadStateListener(new ThreadActivityListener(){

			public void threadActivity(Map<TipiThread, String> threadStateMap, TipiThread tt, String state, int queueSize) {
				System.err.println("Thread activity: "+threadStateMap);
				tja.append(threadStateMap.toString());
			}});
		return tja;
	}

}
