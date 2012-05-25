package com.dexels.navajo.tipi.classdef;

import java.util.Map;
import java.util.Set;


import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.version.ExtensionDefinition;

public interface IClassManager {

	public abstract XMLElement getClassDef(String name) throws ClassNotFoundException;

	// to do: make sure it resolves layout components too
	public abstract XMLElement getAssembledClassDef(String name)
			throws TipiException;

	
	public abstract Map<String, XMLElement> getClassMap();

	public abstract Map<String, FunctionDefinition> getFunctionDefMap();

	public abstract void clearClassMap();

	public abstract void addTipiClassDefinition(XMLElement xe,
			ExtensionDefinition ed) throws ClassNotFoundException, InstantiationException, IllegalAccessException;

	public abstract Class<?> getTipiClass(XMLElement xe);

	public abstract Set<String> getClassNameSet();

	public ExtensionDefinition getExtension(String extensionName);
	
	public abstract void addFunctionDefinition(String name,
			FunctionDefinition fd);

	public TipiTypeParser parseParser(XMLElement xe, ExtensionDefinition ed);
	public boolean isValidType(String name);
	public Object parse(TipiComponent source, String name, String expression,
			TipiEvent te);


}