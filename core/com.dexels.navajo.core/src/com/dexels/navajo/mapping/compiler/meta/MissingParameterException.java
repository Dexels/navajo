/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import java.util.Iterator;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MissingParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2455963968481759993L;

	public MissingParameterException(Set<String> missing, String method, XMLElement offendingTag, String filename) {
		super(filename, offendingTag);
		StringBuilder sb = new StringBuilder();
		Iterator<String> i = missing.iterator();
		while ( i.hasNext() ) {
			sb.append(i.next() + ",");
		}
		super.message = "Missing required parameters: [" + sb.toString().substring(0, sb.length() - 1) + "], for map/method: <" + method + "/>";
	}
}
