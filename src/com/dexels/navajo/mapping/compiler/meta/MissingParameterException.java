package com.dexels.navajo.mapping.compiler.meta;

import java.util.HashSet;
import java.util.Iterator;

public class MissingParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2455963968481759993L;

	public MissingParameterException(HashSet<String> missing, String method, int lineNr, String filename) {
		super(filename, lineNr);
		StringBuffer sb = new StringBuffer();
		Iterator<String> i = missing.iterator();
		while ( i.hasNext() ) {
			sb.append(i.next() + ",");
		}
		super.message = "Missing required parameters: [" + sb.toString().substring(0, sb.length() - 1) + "], for map/method: <" + method + "/>";
	}
}
