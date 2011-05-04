package com.dexels.navajo.dsl.expression.proposals;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.dsl.expression.proposals.INavajoResourceFinder;
import com.google.inject.Inject;

public class NavajoResourceFinder implements INavajoResourceFinder{
	
	@Inject
	public NavajoResourceFinder() {
		
	}
	
	public Navajo getInputNavajo() throws CoreException {
		if(getCurrentProject()==null) {
			return null;
		}
		IFile dd = getCurrentProject().getFile("navajoconfig/input.tml");
		if(dd.exists()) {
			Navajo n = NavajoFactory.getInstance().createNavajo( dd.getContents());
			return n;
		}
		return null;
	}

	public XMLElement getAdapters() throws XMLParseException, IOException {
		return getXmlElement("navajoconfig/adapters.xml");

	}

	public XMLElement getFunctions() throws XMLParseException, IOException {
		return getXmlElement("navajoconfig/functions.xml");
	}

	private XMLElement getXmlElement(String localPath) throws XMLParseException, IOException {
		Reader r = null;
		XMLElement xe;
		try {
			r = getReader(localPath);
			if(r==null) {
				return null;
			}
			xe = new CaseSensitiveXMLElement();
			xe.parseFromReader(r);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(r!=null) {
				r.close();				
			}
		}
		return xe;		
	}
	
	private Reader getReader(String localPath) throws CoreException {
		if(getCurrentProject()==null) {
			System.err.println("No current project");
			return null;
		}
		IFile f = getCurrentProject().getFile(localPath);
		System.err.println("Using file: "+f);
		if(!f.exists()) {
			return null;
		}
		InputStreamReader is = new InputStreamReader(f.getContents());
		return is;
	}

	private IProject getCurrentProject(){

		IProject ip = ResourcesPlugin.getWorkspace().getRoot().getProject("Navajo");
		return ip;
		}

	
}
