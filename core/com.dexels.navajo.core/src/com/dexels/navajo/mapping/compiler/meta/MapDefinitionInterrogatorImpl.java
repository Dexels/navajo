package com.dexels.navajo.mapping.compiler.meta;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.navascript.tags.MapDefinitionInterrogator;
import navajo.ExtensionDefinition;

@SuppressWarnings("unused")
public class MapDefinitionInterrogatorImpl implements MapDefinitionInterrogator {

	private static final Logger logger = LoggerFactory.getLogger(MapDefinitionInterrogatorImpl.class);

	MapMetaData mapMetaData = null;

	public MapDefinitionInterrogatorImpl() throws Exception {
		mapMetaData = MapMetaData.getInstance();
	}

	public void addExtentionDefinition(String extension) throws Exception {

		Class<ExtensionDefinition> c = (Class<ExtensionDefinition>) Class.forName(extension);
		ExtensionDefinition ed = c.getDeclaredConstructor().newInstance();
		mapMetaData.readExtentionDefinition(ed);

	}

	@Override
	public boolean isMethod(String adapter, String m) throws Exception {
		try {
			if (  mapMetaData.getMapDefinition(adapter) == null ) {
				throw new Exception("Could not find adapter: " + adapter);
			}
			return ( mapMetaData.getMapDefinition(adapter).getMethodDefinition(m) != null);
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		} catch (KeywordException e) {
			throw new Exception(e);
		}
	}

	private boolean isRegularField(String adapter, String m) throws Exception {
		if (  mapMetaData.getMapDefinition(adapter) == null ) {
			throw new Exception("Could not find adapter: " + adapter + " for field " + m);
		} 
		String className = mapMetaData.getMapDefinition(adapter).getObjectName();
		Class c = Class.forName(className);
		return c.getDeclaredField(m) != null;
	}

	public boolean hasDeclaredGetter(String className, String m)  {

		String getter = "get" + (""+m.charAt(0)).toUpperCase() + m.substring(1);

		try {
			Class c = Class.forName(className);
			return c.getDeclaredMethod(getter, null) != null;
		} catch (Exception e) {
			return false;
		}
	}


	@Override
	public boolean isDeclaredField(String className, String m)  {
		try {
			Class c = Class.forName(className);
			return c.getDeclaredField(m) != null;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isField(String adapter, String m) throws Exception {

		try {
			if (  mapMetaData.getMapDefinition(adapter) == null ) {
				// Try regular field
				if ( isRegularField(adapter, m) ) {
					return true;
				}
				throw new Exception("Could not find adapter: " + adapter);
			} 
			String objectName = mapMetaData.getMapDefinition(adapter).getObjectName();
			boolean isDefinedField = ( mapMetaData.getMapDefinition(adapter).getValueDefinition(m) != null);
			boolean isDeclaredField = isDeclaredField(objectName, m);
			boolean hasGetter = hasDeclaredGetter(objectName, m);
			return isDefinedField || isDeclaredField || hasGetter;
		} catch (ClassNotFoundException e) {
			throw new Exception(e);
		} catch (KeywordException e) {
			throw new Exception(e);
		}
	}

	public MapDefinition getAdapter(String adapter) throws Exception{

		return mapMetaData.getMapDefinition(adapter);

	}

	public void describeAdapter(String adapter) throws Exception {

		MapDefinition md = mapMetaData.getMapDefinition(adapter);

		Set<String> methods = md.getMethodDefinitions();		

		System.err.println("Adapter " + adapter + " methods:");

		for ( String s : methods ) {
			System.err.println("\t" + s);
		}

		Set<String> values = md.getValueDefinitions();

		System.err.println("Adapter " + adapter + " fields:");

		for ( String s : values ) {
			System.err.println("\t" + s);
		}

	}

	@Override
	public boolean isValidClass(String className) {
		try {
			Class c = Class.forName(className);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isValidAdapter(String adapter)  {
		try {
			return ( mapMetaData.getMapDefinition(adapter) != null ) ;
		} catch (Exception e) {
			return false;
		}
	}
}
