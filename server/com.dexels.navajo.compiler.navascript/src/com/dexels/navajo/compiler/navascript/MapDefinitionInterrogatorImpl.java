package com.dexels.navajo.compiler.navascript;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.navascript.tags.MapDefinitionInterrogator;
import com.dexels.navajo.mapping.compiler.meta.KeywordException;
import com.dexels.navajo.mapping.compiler.meta.MapDefinition;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;

@SuppressWarnings("unused")
public class MapDefinitionInterrogatorImpl implements MapDefinitionInterrogator {

	private static final Logger logger = LoggerFactory.getLogger(MapDefinitionInterrogatorImpl.class);
			
	MapMetaData mapMetaData = null;
	
	public MapDefinitionInterrogatorImpl() throws ClassNotFoundException, KeywordException {
		
		mapMetaData = MapMetaData.getInstance();
		
	}

	@Override
	public boolean isMethod(String adapter, String m) {
		try {
			if (  mapMetaData.getMapDefinition(adapter) == null ) {
				logger.warn("Could not find adapter: " + adapter);
				return false;
			}
			return ( mapMetaData.getMapDefinition(adapter).getMethodDefinition(m) != null);
		} catch (ClassNotFoundException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		} catch (KeywordException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		}
	}

	@Override
	public boolean isField(String adapter, String m) {
		
		try {
			if (  mapMetaData.getMapDefinition(adapter) == null ) {
				logger.warn("Could not find adapter: " + adapter);
				return false;
			}
			return ( mapMetaData.getMapDefinition(adapter).getValueDefinition(m) != null);
		} catch (ClassNotFoundException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
		} catch (KeywordException e) {
			logger.error(e.getLocalizedMessage(), e);
			return false;
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
	
	public static void main (String [] args) throws Exception {
		
		MapDefinitionInterrogatorImpl m = new MapDefinitionInterrogatorImpl();
		
		String adapter = "sqlquery";
		String field = "query";
		
		m.describeAdapter(adapter);
		
	}
}
