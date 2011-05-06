package com.dexels.navajo.dsl.expression.proposals;

import java.io.IOException;


import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.google.inject.ImplementedBy;

@ImplementedBy (NavajoResourceFinder.class)
public interface INavajoResourceFinder {
	public Navajo getInputNavajo() throws Exception;
	public XMLElement getAdapters() throws XMLParseException, IOException;
	public XMLElement getFunctions() throws XMLParseException, IOException;

}
