package com.dexels.navajo.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.Access;

public interface TmlRunnable extends Runnable {

	boolean isCommitted();
	void setCommitted(boolean b);
	void setScheduledAt(long currentTimeMillis);
	public void endTransaction() throws IOException;
	
	public Navajo getInputNavajo() throws IOException;
	public void setResponseNavajo(Navajo n) ;
	
	public RequestQueue getRequestQueue();
	public void setRequestQueue(RequestQueue rq);
	
	public Object getAttribute(String name);
	
	public boolean isAborted();
	public void abort(String string);
	
	public String getUrl();

	public Access getAccess();
	public void setAccess(Access access);

	public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException;

}
