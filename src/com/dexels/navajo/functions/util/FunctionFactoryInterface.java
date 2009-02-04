package com.dexels.navajo.functions.util;

import java.util.HashMap;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.UserException;

public abstract class FunctionFactoryInterface {

	private HashMap<String, FunctionDefinition> config = null;
	private static Object semaphore = new Object();
	private boolean initializing = false;
	
	public abstract void init();
	
	/**
	 * Fetch a functiondefinition. If not found first time, try re-init (maybe new definition), if still not found throw Exception.
	 * 
	 * @param name
	 * @return
	 * @throws UserException
	 */
	private final FunctionDefinition getDef(String name) throws TMLExpressionException {
		
		while ( initializing ) {
			// Wait a bit.
			synchronized (semaphore) {
				try {
					semaphore.wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		FunctionDefinition fd = config.get(name);
		if ( fd != null ) {
			return fd;
		} else {
			// Try with fresh config.
			synchronized (semaphore) {
				setInitializing(true);
				System.err.println("Reread function config, maybe new definition found: " + name);
				init();
				setInitializing(false);
				semaphore.notify();
			}
			fd = config.get(name);
			if ( fd != null ) {
				return fd;
			} else {
				throw new TMLExpressionException("Could not find function definition: " + name);
			}
		}
	}
		
	public final FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException {
		
		try {
			FunctionDefinition fd = getDef(functionName);
			Class myClass = Class.forName(fd.getObject(), true, cl);
			// TODO INSTANTIATE FUNCTION USING FACTORY, SETTING TYPE SIGNATURE IN FACTORY!!!
			FunctionInterface fi = (FunctionInterface) myClass.newInstance();
			if (!fi.isInitialized()) {
				fi.setTypes(fd.getInputParams());
				fi.setReturnType(fd.getResultParam());
			}
			return fi;
		} catch (ClassNotFoundException e) {
			throw new TMLExpressionException("Could find class for function: " + getDef(functionName).getObject());
		} catch (InstantiationException e) {
			throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject());
		} catch (IllegalAccessException e) {
			throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject());
		}
	}

	public HashMap<String, FunctionDefinition> getConfig() {
		return config;
	}

	public void setConfig(HashMap<String, FunctionDefinition> config) {
		this.config = config;
	}

	public boolean isInitializing() {
		return initializing;
	}

	public void setInitializing(boolean initializing) {
		this.initializing = initializing;
	}
	
}
