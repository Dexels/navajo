package com.dexels.navajo.tipi.vaadin.application;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

import navajo.ExtensionDefinition;
import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipi.TipiExtension;
import tipi.TipiVaadinExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.LoginDialog;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class TipiVaadinApplication extends Application implements TipiApplicationInstance {

	private VaadinTipiContext myContext;
	private ServletContext servletContext;
	
	@Override
	public void init() {
		
		setTheme("runo");
		final Window mainWindow = new Window("Tipi Vaadin");
		setMainWindow(mainWindow);
		try {
			setCurrentContext(createContext());
	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public TipiContext getCurrentContext() {
		return myContext;
	}

	@Override
	public void setCurrentContext(TipiContext currentContext) {
		myContext = (VaadinTipiContext) currentContext;
	}

	@Override
	public void startup() throws IOException, TipiException {
		init();
	}

	@Override
	public TipiContext createContext() throws IOException {
		// TODO Rewrite to OSGi resolution
		List<TipiExtension> preload = new LinkedList<TipiExtension>();
		preload.add(new TipiCoreExtension());
		TipiVaadinExtension tipiVaadinExtension = new TipiVaadinExtension();
		preload.add(tipiVaadinExtension);
		VaadinTipiContext va = new VaadinTipiContext(this, preload);
		va.setMainWindow(getMainWindow());
		
		va.setContextName(this.servletContext.getContextPath());
		try {
			loadTipi(va, "init.xml", tipiVaadinExtension);
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return va;
	}

	public void loadTipi(TipiContext newContext, String fileName, ExtensionDefinition ed) throws IOException, TipiException {
		System.err.println("Context: "+newContext+" filename: "+fileName);
		InputStream in = newContext.getTipiResourceStream(fileName);

		if(in!=null) {
			newContext.parseStream(in, "startup", false,ed);
			newContext.switchToDefinition("startup");
			in.close();
			 
		} else {
			throw new TipiException("Error loading tipi: "+fileName);
		}
	}
	
	@Override
	public void dispose(TipiContext t) {
		
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reboot() throws IOException, TipiException {
		// TODO Auto-generated method stub
		
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		
	}

	public File getInstallationFolder() {
		return new File(InstallationPathResolver.getInstallationPath(this.servletContext));
	}
}
