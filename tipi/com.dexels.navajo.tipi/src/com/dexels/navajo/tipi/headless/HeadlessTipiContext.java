package com.dexels.navajo.tipi.headless;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.BaseTipiErrorHandler;

public class HeadlessTipiContext extends TipiContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000625714811093584L;
	StringBuffer infoBuffer = new StringBuffer();
	Queue<String> infoQueue = new LinkedList<String>();

	// private final Thread myInvokingThread;

	public HeadlessTipiContext(List<TipiExtension> ed) {
		super(null, ed);
		// myInvokingThread = Thread.currentThread();
	}

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
		eHandler.initResource();

	}

	public void exit() {
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
	public void showInfo(String text, String title) {
		// System.err.println("Title: "+title+" text: "+text);
		infoBuffer.append(text);
		infoBuffer.append("\n");
		infoQueue.offer(text);
	}

	@Override
	public void showQuestion(String text, String title, String[] options)
			throws TipiBreakException {
		// System.err.println("Ask question: "+text+". I don't care about your answer. I assume yes.");
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
