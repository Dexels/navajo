package com.dexels.navajo.dsl.expression.ui.contentassist;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;

public interface INavajoResourceFinder {
	public Navajo getInputNavajo() throws Exception;
	public XMLElement getAdapters() throws XMLParseException, IOException, CoreException;
	public XMLElement getFunctions() throws CoreException, XMLParseException, IOException;
			

}
