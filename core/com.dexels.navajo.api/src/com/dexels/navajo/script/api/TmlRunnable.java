package com.dexels.navajo.script.api;

import java.io.IOException;
import java.util.Set;

import com.dexels.navajo.document.Navajo;

public interface TmlRunnable extends Runnable {

	boolean isCommitted();
	void setCommitted(boolean b);
	void setScheduledAt(long currentTimeMillis);
	public void endTransaction() throws IOException;
	
	public Navajo getInputNavajo() throws IOException;
	public void setResponseNavajo(Navajo n) ;
	public Navajo getResponseNavajo() ;
	
	public RequestQueue getRequestQueue();
	public void setRequestQueue(RequestQueue rq);
	
	
	public boolean isAborted();
	public void abort(String string);
	public AsyncRequest getRequest();

	public String getUrl();
	public String getNavajoInstance();

	public Object getAttribute(String name);

	public void setAttribute(String name, Object value) ;
	public Set<String> getAttributeNames();
	
	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException;

}
