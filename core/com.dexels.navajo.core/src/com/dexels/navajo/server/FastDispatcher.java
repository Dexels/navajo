package com.dexels.navajo.server;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.FatalException;

public class FastDispatcher {

	
	private final static Logger logger = LoggerFactory
			.getLogger(FastDispatcher.class);
	/**
	 * Non blocking handle.
	 * NOT USES? Seems on functional.
	 * 
	 * @param in
	 * @param responseOutputStream
	 */
	@SuppressWarnings("unchecked")
	public void handle(Navajo in, OutputStream responseOutputStream, Runnable onFinish) {
		
		// System.err.println(">>>> IN FASTDISPATCHER.HANDLE()..." + responseOutputStream);
		// Create event driven workflow to minimize blocking.
		Access a = new Access();
		a.setInDoc(in);
		a.rpcName = in.getHeader().getRPCName();
		a.rpcUser = in.getHeader().getRPCUser();
		
		try {
			Class c = Class.forName("com.dexels.navajo.workflow.TestServiceWorkflow");
			Constructor con = c.getConstructor(new Class[]{Access.class, OutputStream.class, Runnable.class});
			Object o = con.newInstance(new Object[]{a, responseOutputStream, onFinish});
			Method m = c.getMethod("start", (Class[])null);
			m.invoke(o, (Object[])null);
			
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		
	}
	
	/**
	 * Non blocking handle with client callback.
	 * Response is asychronously post to given callback URL.
	 * 
	 * @param in
	 * @param callback URL (e.g. http://clienthost/servlet/Callback, jabber://service/conference/chatroom/nickname,
	 *                           workflow://workflowdefinition/id/state )
	 * @return callback Navajo
	 */
	public Navajo handle(Navajo in, String callback) {
		return null;
	}
	
	/**
	 * Standard blocking request/response handle
	 * 
	 * @param in
	 * @return
	 */
	public Navajo handle(Navajo in) throws FatalException {
		return DispatcherFactory.getInstance().handle(in, true,null);
	}
	
	/**
	 * Handle a Navajo callback (reply from asynchronous request)
	 * Callback uses two header attributes:
	 * 
	 * messageid
	 * workflowid (optional, if message is meant for workflow instance)
	 * 
	 * @param in
	 */
	public void callback(Navajo in) {
		
	}
	
	
}
