package com.dexels.navajo.functions.util;

import navajo.ExtensionDefinition;

public final class FunctionDefinition {

	// = fully qualified class name, actually
	private final String object;
	private final String description;
	private final String [][] inputParams;
	private final String [] resultParam;
	
	public FunctionDefinition(final String object, final String description, final String inputParams, final String resultParam, ExtensionDefinition fd) {
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

	public String toString() {
		return description;
	}
	
	public static void main(String [] args) throws Exception {
		
	}
}
