package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TSLElement extends CaseSensitiveXMLElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6997480396735629539L;

	public TSLElement(XMLElement source, String name) {
		super();
		setName(name);
		setAttribute("linenr", source.getStartLineNr());
		setAttribute("startoffset", source.getStartOffset());
		setAttribute("endoffset", source.getOffset());
	}
}
