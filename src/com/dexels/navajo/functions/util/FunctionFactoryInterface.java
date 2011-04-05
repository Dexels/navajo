package com.dexels.navajo.functions.util;

import java.util.HashMap;
import java.util.Set;

import navajo.ExtensionDefinition;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.UserException;

public abstract class FunctionFactoryInterface {

	private HashMap<String, FunctionDefinition> functionConfig = null;
	protected final HashMap<String, String> adapterConfig = new HashMap<String, String>();
	private static Object semaphore = new Object();
	private boolean initializing = false;
	
	public abstract void init();

	
	public void injectExtension(ExtensionDefinition fd) {
		readDefinitionFile(getConfig(), fd);

	}

	
	public abstract void readDefinitionFile(HashMap<String, FunctionDefinition> fuds, ExtensionDefinition fd) ;

	/**
	 * Fetch a functiondefinition. If not found first time, try re-init (maybe new definition), if still not found throw Exception.
	 * 
	 * @param name
	 * @return
	 * @throws UserException
	 */
	
	public final FunctionDefinition getDef(String name) throws TMLExpressionException {
		
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
		
		FunctionDefinition fd = functionConfig.get(name);
		if ( fd != null ) {
			return fd;
		} else {
			throw new TMLExpressionException("Could not find function definition: " + name);
		}
	}
	
	public final String getAdapterClass(String name)  {
		return adapterConfig.get(name);
	}

	public final Object getAdapterInstance(String name, ClassLoader cl)  {
		try {
			Class c = Class.forName(getAdapterClass(name),true,cl);
			return c.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	public Set<String> getFunctionNames() {
		return functionConfig.keySet();
	}
	public void clearFunctionNames() {
		functionConfig.clear();
	}

	public Set<String> getAdapterNames() {
		return adapterConfig.keySet();
	}
	public void clearAdapterNames() {
		adapterConfig.clear();
	}

	public FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException {
		try {
			FunctionDefinition fd = getDef(functionName);
			Class myClass = Class.forName(fd.getObject(), true, cl);
			FunctionInterface fi =(FunctionInterface) myClass.newInstance();
			if (!fi.isInitialized()) {
				fi.setTypes(fd.getInputParams(), fd.getResultParam());
			}
			return fi;
		} catch (Exception e) {
			// Try legacy mode.
			try {
				Class myClass = Class.forName("com.dexels.navajo.functions."+functionName, true, cl);
				FunctionInterface fi = (FunctionInterface) myClass.newInstance();
				if (!fi.isInitialized()) {
					fi.setTypes(null, null);
				}
				return fi;
			} catch (ClassNotFoundException e1) {
				throw new TMLExpressionException("Could find class for function: " + getDef(functionName).getObject(),e1);
			} catch (IllegalAccessException e2) {
				throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject(),e2);
			} catch (InstantiationException e3) {
				throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject(),e3);
			}
//		} catch (InstantiationException e) {
//			throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject());
//		} catch (IllegalAccessException e) {
//			throw new TMLExpressionException("Could not instantiate class: " + getDef(functionName).getObject());
		}
	}

	public HashMap<String, FunctionDefinition> getConfig() {
		return functionConfig;
	}

	public void setConfig(HashMap<String, FunctionDefinition> config) {
		this.functionConfig = config;
	}

	public boolean isInitializing() {
		return initializing;
	}

	public void setInitializing(boolean initializing) {
		this.initializing = initializing;
	}
	public FunctionInterface instantiateFunctionClass(FunctionDefinition fd, ClassLoader classLoader) {
		try {
			Class<? extends FunctionInterface> clz = (Class<? extends FunctionInterface>) Class.forName(fd.getObject(),true,classLoader);
			return clz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//	
	
}
