/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class KeywordException extends MetaCompileException {

	public KeywordException(String fileName, XMLElement offendingTag, String message) {
		super(fileName, offendingTag);
		super.message = message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2326978089816572024L;

}
