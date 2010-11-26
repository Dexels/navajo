package com.dexels.navajo.dsl.expression.ui.contentassist.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.dsl.expression.ui.contentassist.INavajoResourceFinder;

public class NavajoResourceFinder implements INavajoResourceFinder{
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

	public XMLElement getAdapters() throws XMLParseException, IOException, CoreException {
		return getXmlElement("navajoconfig/adapters.xml");

	}

	public XMLElement getFunctions() throws CoreException, XMLParseException, IOException {
		return getXmlElement("navajoconfig/functions.xml");
	}

	private XMLElement getXmlElement(String localPath) throws CoreException, XMLParseException, IOException {
		Reader r = null;
		XMLElement xe;
		try {
			r = getReader(localPath);
			if(r==null) {
				return null;
			}
			xe = new CaseSensitiveXMLElement();
			xe.parseFromReader(r);
		} finally {
			if(r!=null) {
				r.close();				
			}
		}
		return xe;		
	}
	private Reader getReader(String localPath) throws CoreException {
		if(getCurrentProject()==null) {
			return null;
		}
		IFile f = getCurrentProject().getFile(localPath);
		InputStreamReader is = new InputStreamReader(f.getContents());
		return is;
	}

	private IProject getCurrentProject(){
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		if(activePage==null) {
			return null;
		}
		IEditorPart editor = activePage.getActiveEditor();
		if(editor==null) {
			return null;
		}
		IEditorInput input =  editor.getEditorInput();
		  IFile file = null;
		  if(input instanceof IFileEditorInput){
		   file = ((IFileEditorInput)input).getFile();
		  }
		  if(file==null) {
			  return null;
		  }

		    IProject project = file.getProject();
		  return project;

		 }

	
}
