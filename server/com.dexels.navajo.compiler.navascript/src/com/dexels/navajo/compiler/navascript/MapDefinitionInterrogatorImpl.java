package com.dexels.navajo.compiler.navascript;

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
}
