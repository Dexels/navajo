package com.dexels.navajo.compiler.navascript;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.StandardAdapterLibrary;
import com.dexels.navajo.document.navascript.tags.MapDefinitionInterrogator;
import com.dexels.navajo.mapping.compiler.meta.KeywordException;
import com.dexels.navajo.mapping.compiler.meta.MapDefinition;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.mongo.adapter.MongoAdapterLibrary;
import com.dexels.sportlink.adapters.SportlinkAdapterDefinitions;

import navajo.ExtensionDefinition;

import com.dexels.navajo.adapter.core.NavajoEnterpriseCoreAdapterLibrary;

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

	@Override
	public boolean isField(String adapter, String m) throws Exception {
		
		
		try {
			if (  mapMetaData.getMapDefinition(adapter) == null ) {
				throw new Exception("Could not find adapter: " + adapter);
			}
			boolean b = ( mapMetaData.getMapDefinition(adapter).getValueDefinition(m) != null);
			return b;
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
	
	public static void main (String [] args) throws Exception {
		
		MapDefinitionInterrogatorImpl m = new MapDefinitionInterrogatorImpl();
		
		String adapter = "sqlquery";
		String field = "doUpdate";
		
		m.describeAdapter(adapter);
		
	}
}
