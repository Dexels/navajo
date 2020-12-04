/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.headless;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.BaseTipiErrorHandler;

import tipi.TipiExtension;

public class HeadlessTipiContext extends TipiContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000625714811093584L;
	StringBuffer infoBuffer = new StringBuffer();
	Queue<String> infoQueue = new LinkedList<String>();

	// private final Thread myInvokingThread;

	public HeadlessTipiContext(List<TipiExtension> ed) {
		super(new HeadlessApplicationInstance(), ed);
		
		// myInvokingThread = Thread.currentThread();
	}

	@Override
	public void processProperties(Map<String, String> properties)
			throws MalformedURLException {
		for (Iterator<String> iter = properties.keySet().iterator(); iter
				.hasNext();) {
			String element = iter.next();
			String value = properties.get(element);
			if (element.startsWith("-D")) {
				element = element.substring(2);
			}
			setSystemProperty(element, value);
		}
		eHandler = new BaseTipiErrorHandler();
		eHandler.setContext(this);
	}

	@Override
	public void doExit() {
		shutdown();
		// myInvokingThread.interrupt();
	}

	@Override
	public void clearTopScreen() {

	}

	@Override
	public void runAsyncInEventThread(Runnable r) {
		Thread y = new Thread(r);
		y.start();
	}

	@Override
	public void runSyncInEventThread(Runnable r) {
		r.run();

	}

	@Override
	public void setSplash(Object s) {

	}

	@Override
	public void setSplashInfo(String s) {

	}

	@Override
	public void setSplashVisible(boolean b) {

	}

	@Override
	public void showInfo(String text, String title, TipiComponent tc) {
		infoBuffer.append(text);
		infoBuffer.append("\n");
		infoQueue.offer(text);
	}

	@Override
	public void showQuestion(String text, String title, String[] options, TipiComponent tc)
			throws TipiBreakException {
		infoBuffer.append(text);
		infoBuffer.append("\n");
		infoQueue.offer(text);
	}

	public String getInfoBuffer() {
		return infoBuffer.toString();
	}

	public String expect() {
		return infoQueue.poll();
	}

}
