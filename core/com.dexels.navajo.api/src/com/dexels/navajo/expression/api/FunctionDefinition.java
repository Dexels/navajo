/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.expression.api;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public final class FunctionDefinition implements Serializable {

	
	private static final Logger logger = LoggerFactory
			.getLogger(FunctionDefinition.class);
	private static final long serialVersionUID = 8105107847721249814L;
	// = fully qualified class name, actually
	private final String object;
	private final String description;
	private final String [][] inputParams;
	private final String [] resultParam;
	private XMLElement xmlElement;
	private Class<? extends FunctionInterface> functionClass;
	
	public FunctionDefinition(final String object, final String description, final String inputParams, final String resultParam) {
		this.object = object;
		this.description = description;
		if ( inputParams != null && !inputParams.equals("") ) {
			String [] params = inputParams.split(",");
			this.inputParams = new String[params.length][];
			for (int i = 0; i < params.length; i++) {
				this.inputParams[i] = params[i].split("\\|");
			}
		} else {
			this.inputParams =  null;
		}
		if ( resultParam != null && !resultParam.equals("") ) {
			this.resultParam = resultParam.split("\\|");
		} else {
			this.resultParam = null;
		}
	}

	public final String getObject() {
		return object;
	}

	public final String getDescription() {
		return description;
	}

	public final String [][] getInputParams() {
		return inputParams;
	}

	public final String [] getResultParam() {
		return resultParam;
	}

	@Override
	public String toString() {
		return description;
	}
	
	public static void main(String [] args) throws Exception {
		
	}

	public void setXmlElement(XMLElement f) {
		this.xmlElement = f;
		
	}

	public XMLElement getXmlElement() {
		return xmlElement;
	}

	public Class<? extends FunctionInterface> getFunctionClass() {
		return functionClass;
	}

	public void setFunctionClass(Class<? extends FunctionInterface> clz) {
		this.functionClass = clz;
	}
	
	public FunctionInterface getFunctionInstance() {
		try {
			final Class<? extends FunctionInterface> fc = getFunctionClass();
			if(fc==null) {
				logger.warn("No function class found for function with name: {}", getDescription());
				return null;
			}
			FunctionInterface osgiResolution = fc.getDeclaredConstructor().newInstance();
			if (!osgiResolution.isInitialized()) {
				osgiResolution.setTypes(getInputParams(), getResultParam());
			}
			return osgiResolution;
		} catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException|NoSuchMethodException|SecurityException e) {
			logger.warn("Function instantiation issue.",e);
		return null;
		}
	}
}
