/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownParameterException extends MetaCompileException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8641803615058174162L;
	
	public UnknownParameterException(String method, String parameter, XMLElement offendingTag, String filename) {
		super(filename,offendingTag);
		super.message = "Unknown parameter [" + parameter + "] for method <" + method + "/>";
	}

}
