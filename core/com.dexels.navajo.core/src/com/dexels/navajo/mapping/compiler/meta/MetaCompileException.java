/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class MetaCompileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193610832873773439L;

	protected XMLElement offendingTag;
	protected String message;
	protected String fileName;
	
	public  MetaCompileException(String fileName, XMLElement offendingTag) {
		this.fileName = fileName;
		this.offendingTag = offendingTag;
	}
	
	public  MetaCompileException(String fileName, XMLElement offendingTag, String message) {
		this.fileName = fileName;
		this.offendingTag = offendingTag;
		this.message = message;
	}
	
	public XMLElement getOffendingTag() {
		return this.offendingTag;
	}
	
	public void setOffendingTag(XMLElement offendingTag) {
		this.offendingTag = offendingTag;
	}
	
	@Override
	public String getMessage() {
		return message + " (" + fileName + ":" + (offendingTag.getStartLineNr()+1) + ")";
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
