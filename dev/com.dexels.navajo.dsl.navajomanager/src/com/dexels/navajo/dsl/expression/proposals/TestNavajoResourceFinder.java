package com.dexels.navajo.dsl.expression.proposals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.dsl.expression.proposals.INavajoResourceFinder;

public class TestNavajoResourceFinder implements INavajoResourceFinder {

	@Override
	public Navajo getInputNavajo() throws CoreException, FileNotFoundException {
		Reader r = getReader("testartifacts/input.tml");
		return NavajoFactory.getInstance().createNavajo(r);
	}

	@Override
	public XMLElement getAdapters() throws XMLParseException, IOException {

		XMLElement adapterList = new CaseSensitiveXMLElement();
		adapterList.parseFromReader(getReader("testartifacts/adapters.xml"));
		return adapterList;
	}
	
	private Reader getReader(String localPath) throws FileNotFoundException {
		return new InputStreamReader(getStream(localPath));
	}
	private InputStream getStream(String localPath) throws FileNotFoundException {
		File f = new File(localPath);
		System.err.println("Opening file: "+f.getAbsolutePath());
		return new FileInputStream(f);
	}

	@Override
	public XMLElement getFunctions() throws XMLParseException,
			IOException {
		XMLElement functionList = new CaseSensitiveXMLElement();
		functionList.parseFromReader(getReader("testartifacts/functions.xml"));
		return functionList;
	}

	@Override
	public IProject getCurrentProject() {
		return null;
	}

	@Override
	public void setCurrentProject(IProject currentProject) {
		
	}

}
