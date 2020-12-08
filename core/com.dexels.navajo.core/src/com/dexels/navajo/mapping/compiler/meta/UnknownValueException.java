/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class UnknownValueException extends MetaCompileException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6792620993996882281L;

	public UnknownValueException(String tag, String parameter, XMLElement offendingTag, String filename) {
		super(filename,offendingTag);
		if ( parameter != null ) {
			super.message = "Trying to set unknown parameter [" + parameter + "] in tag <" + tag + "/>";
		} else {
			super.message = "Trying to set empty parameter in tag <" + tag + "/>";
		}
	}

}
