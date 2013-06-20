package com.dexels.navajo.script.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

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

//	public Access getAccess();
//	public void setAccess(Access access);
	public Object getAttribute(String name);

	public void setAttribute(String name, Object value) ;
	public Set<String> getAttributeNames();
	
	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException;

}
