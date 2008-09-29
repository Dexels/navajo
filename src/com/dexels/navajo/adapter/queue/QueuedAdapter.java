package com.dexels.navajo.adapter.queue;

import java.util.Date;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;

/**
 * Container class that is used to run queuable objects in seperate thread.
 * This class is instantiated from the RequestResponse queue.
 * 
 * @author arjen
 *
 */
public class QueuedAdapter extends Thread implements Mappable {

	public int runningTime;
	public int timeToRun;
	public String adapterName;
	public int retries;
	public int maxRetries;
	public String accessId;
	public String webservice;
	public String username;
	public String request;
	public String ref;
	public boolean delete;
	public String exception;
	public String stackTraceMessage;
	public Date created;
	private long startTime;
	protected Queuable handler;
	
	public QueuedAdapter(Queuable h) {
		startTime = System.currentTimeMillis();
		handler = h;
	}
	
	/**
	 * Generate a log message for a Queuable object.
	 * 
	 * @param h
	 * @param message
	 * @return
	 */
	public final static String generateLogMessage(Queuable h, String message) {
		
		if ( h.getAccess() == null ) {
			return "";
		}
		String msg = ( h != null ? h.getClass().getName() : "[empty handler]");
		if ( h.getAccess() != null ) {
			msg += " (service=" + h.getAccess().getRpcName() + ", user=" + h.getAccess().getRpcUser() + 
				   ", accessid=" + h.getAccess().getAccessID() + "), message: " + message + ".\nException:\n" +
				   getException(h) +
				   "\nStacktrace:\n" +
				   getStackTraceMessage(h);
		}
		return msg;
	}
	
	/**
	 * Returns timestamp when thread was started.
	 * 
	 * @return
	 */
	public long getStartTime() {
		return startTime;
	}
	
	/**
	 * Returns the time (in millis) when this thread is ready for execution.
	 * 
	 * @return
	 */
	public int getTimeToRun() {
		return (int) ( handler.getWaitUntil() - System.currentTimeMillis() );
	}
	
	/**
	 * Returns the total running time (in millis) thus far.
	 * 
	 * @return
	 */
	public int getRunningTime() {
		return (int) ( System.currentTimeMillis() - startTime );
	}
	
	/**
	 * Returns the classname of the Queuable object.
	 * 
	 * @return
	 */
	public String getAdapterName() {
		return handler.getClass().getName();
	}
	
	/**
	 * Returns a handler to the Queuable object.
	 * 
	 * @return
	 */
	public Queuable getAdapter() {
		return handler;
	}

	/**
	 * Returns total number of retries thus far.
	 * 
	 * @return
	 */
	public int getRetries() {
		return handler.getRetries();
	}
	
	/**
	 * Returns the maximum number of retries.
	 * 
	 * @return
	 */
	public int getMaxRetries() {
		return handler.getMaxRetries();
	}
	
	/**
	 * Returns the original access id of the request that initiated Queuable object.
	 * 
	 * @return
	 */
	public String getAccessId() {
		return handler.getAccess().accessID;
	}
	
	/**
	 * Returns the web service name of the original request hat initiated Queuable object.
	 * 
	 * @return
	 */
	public String getWebservice() {
		return handler.getAccess().rpcName;
	}
	
	/**
	 * Returns the username of the original request that initiated Queuable object.
	 * 
	 * @return
	 */
	public String getUsername() {
		return handler.getAccess().rpcUser;
	}
	
	/**
	 * Get the request Navajo of this queued adapter.
	 * 
	 * @return
	 */
	public String getRequest() {
		java.io.StringWriter sw = new java.io.StringWriter();
		Navajo in = handler.getNavajo();
		try {
			in.write(sw);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	/**
	 * Get the reference String associated with this queued adapter.
	 * 
	 * @return
	 */
	public String getRef() {
		return ref;
	}
	
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Access access) throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
		
	}

	private static final String getException(Queuable q) {
		if ( q != null && q.getAccess() != null && q.getAccess().getException() != null ) {
			return q.getAccess().getException().getLocalizedMessage();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the exception message (if present).
	 * 
	 * @return
	 */
	public String getException() {
		return getException(handler);
	}
	
	private static final String getStackTraceMessage(Queuable q) {
		if ( q != null && q.getAccess() != null && q.getAccess().getException() != null ) {
			StringBuffer result = new StringBuffer();
			StackTraceElement [] elt = q.getAccess().getException().getStackTrace();
			for ( int i = 0; i < elt.length; i++ ) {
				result.append(elt[i].getClassName()+"."+elt[i].getMethodName() + " (" + elt[i].getFileName() + ":" + elt[i].getLineNumber() + ")\n");
			}
			return result.toString();
		} else {
			return null;
		}
	}
	
	public String getStackTraceMessage() {
		return getStackTraceMessage(handler);
	}

	/**
	 * Returns the original creation date for the request that initiated this Queuable object.
	 * @return
	 */
	public Date getCreated() {
		if ( handler != null && handler.getAccess() != null ) {
			return handler.getAccess().created;
		} else {
			return null;
		}
	}

}
