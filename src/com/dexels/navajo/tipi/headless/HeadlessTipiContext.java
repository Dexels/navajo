package com.dexels.navajo.tipi.headless;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class HeadlessTipiContext extends TipiContext {

	StringBuffer infoBuffer = new StringBuffer();
	Queue<String> infoQueue = new LinkedList<String>();
	private final Thread myInvokingThread;

	
	public HeadlessTipiContext() {
		super();
		myInvokingThread = Thread.currentThread(); 
	}
	
	public void exit() {
		shutdown();
		//myInvokingThread.interrupt();
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
		// TODO Auto-generated method stub

	}
	@Override
	public void showInfo(String text, String title) {
//		System.err.println("Title: "+title+" text: "+text);
		infoBuffer.append(text);
		infoBuffer.append("\n");
		infoQueue.offer(text);
	}

	@Override
	public void showQuestion(String text, String title, String[] options) throws TipiBreakException {
//		System.err.println("Ask question: "+text+". I don't care about your answer. I assume yes.");
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
