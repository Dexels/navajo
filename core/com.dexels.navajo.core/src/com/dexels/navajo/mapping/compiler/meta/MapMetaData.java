package com.dexels.navajo.mapping.compiler.meta;

import java.io.InputStream;
import java.io.Writer;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.expression.meta.MapMetaDataListener;

public interface MapMetaData extends MapMetaDataListener{

	public Set<String> getMapDefinitions();

	public MapDefinition getMapDefinition(String name)
			throws Exception;

	public String parse(String fileName) throws Exception;

	/**
	 * Parses this script from the screen, the script name is still necessary. Does not close the stream!
	 * @param scriptName
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public String parse(String scriptName, InputStream is)
			throws Exception;

	public void parse(XMLElement in, String scriptName, Writer sw)
			throws Exception;
	
	public boolean isMetaScript(String script, String scriptPath, String packagePath);

		
}