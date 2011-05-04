package com.dexels.navajo.functions.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import navajo.ExtensionDefinition;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.UserException;

public abstract class FunctionFactoryInterface {

	private Map<String, FunctionDefinition> defaultConfig = null;
//	protected final HashMap<String, String> defaultAdapterConfig = new HashMap<String, String>();

	protected final Map<ExtensionDefinition,Map<String, String>> adapterConfig = new HashMap<ExtensionDefinition,Map<String, String>>();
	
	protected final Map<ExtensionDefinition,Map<String,FunctionDefinition>> functionConfig = new HashMap<ExtensionDefinition,Map<String,FunctionDefinition>>();

	private static Object semaphore = new Object();
	private boolean initializing = false;
	
	public abstract void init();

	
	public void injectExtension(ExtensionDefinition fd) {
		readDefinitionFile(getConfig(fd), fd);

	}

	
	public abstract void readDefinitionFile(Map<String, FunctionDefinition> fuds, ExtensionDefinition fd) ;

	
	public final FunctionDefinition getDef(String name) throws TMLExpressionException {
		if(defaultConfig!=null) {
			FunctionDefinition fd = defaultConfig.get(name);
			if(fd!=null) {
				return fd;
			}
		}
		for (Map<String, FunctionDefinition> elt : functionConfig.values()) {
			FunctionDefinition fd = elt.get(name);
			if(fd!=null) {
				return fd;
			}
		}
		return null;
	}
	/**
	 * Fetch a functiondefinition. If not found first time, try re-init (maybe new definition), if still not found throw Exception.
	 * 
	 * @param name
	 * @return
	 * @throws UserException
	 */
	
	public final FunctionDefinition getDef(ExtensionDefinition ed, String name) throws TMLExpressionException {
		
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
		
		FunctionDefinition fd = functionConfig.get(ed).get(name);
		if ( fd != null ) {
			return fd;
		} else {
			throw new TMLExpressionException("Could not find function definition: " + name);
		}
	}
	
	public  String getAdapterClass(String name, ExtensionDefinition ed)  {
		return getAdapterConfig(ed).get(name);
	}

	public final Object getAdapterInstance(String name, ClassLoader cl)  {
		try {
			// Old skool, adapter should have been supplied by an OSGi service
			Class<?> c = getAdapterClass(name, cl);
			if(c==null) {
				// No adapter found, going older skool:
				c = Class.forName(name, true, cl);
			}
			return c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// old skool class fail, unreachable in injected OSGi mode
			e.printStackTrace();
		}
		return null;
	}

	public  Class<?> getAdapterClass(String name, ClassLoader cl) throws ClassNotFoundException {
		for (ExtensionDefinition elt : adapterConfig.keySet()) {
			String ss = getAdapterClass(name, elt);
			if(ss!=null) {
				try {
					Class<?> c = Class.forName(getAdapterClass(name,elt),true,cl);
					return c;
				} catch (ClassNotFoundException e) {
					// not found in this extensiondefinition.
				}				
			}
		}
		// no class found, throw.
		throw new ClassNotFoundException("Adapter named: "+name+" not found.");
		
		}

	public  Class<?> getAdapterClass(String name, ClassLoader cl, ExtensionDefinition ed) {
		try {
			Class<?> c = Class.forName(getAdapterClass(name,ed),true,cl);
			return c;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Set<String> getFunctionNames(ExtensionDefinition ed) {
		return functionConfig.get(ed).keySet();
	}
	public void clearFunctionNames() {
		functionConfig.clear();
	}

	public Set<String> getAdapterNames(ExtensionDefinition ed) {
		return getAdapterConfig(ed).keySet();
	}
	public void clearAdapterNames() {
		adapterConfig.clear();
	}

	public FunctionInterface getInstance(final ClassLoader cl, final String functionName) throws TMLExpressionException {
		// This method is only used for non osgi resolution
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

	public Map<String, FunctionDefinition> getConfig(ExtensionDefinition ed) {
		Map<String, FunctionDefinition> map = functionConfig.get(ed);
		if(map!=null) {
			return map;
		}
		map = new HashMap<String, FunctionDefinition>();
		functionConfig.put(ed,map);
		return map;
	}
	
	public Map<String, String> getAdapterConfig(ExtensionDefinition ed) {
		Map<String, String> map = adapterConfig.get(ed);
		if(map!=null) {
			return map;
		}
		map = new HashMap<String, String>();
		adapterConfig.put(ed,map);
		return map;
	}
	

	public void setAdapterConfig(ExtensionDefinition ed, Map<String, String> config) {
		this.adapterConfig.put(ed, config);
	}

	public void setConfig(ExtensionDefinition ed, Map<String, FunctionDefinition> config) {
		this.functionConfig.put(ed, config);
	}

	public Map<String, FunctionDefinition> getDefaultConfig() {
		return defaultConfig;
	}

	public void setDefaultConfig(Map<String, FunctionDefinition> config) {
		this.defaultConfig = config;
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
	public List<XMLElement> getAllFunctionElements(String interfaceClass, String propertyKey)  {
		throw new UnsupportedOperationException("getAllFunctionElements only implemented in OSGi");
	}
}
