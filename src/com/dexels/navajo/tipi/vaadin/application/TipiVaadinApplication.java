package com.dexels.navajo.tipi.vaadin.application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import navajo.ExtensionDefinition;
import tipi.TipiApplicationInstance;
import tipi.TipiExtension;
import tipi.TipiVaadinExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class TipiVaadinApplication extends Application implements
		TipiApplicationInstance,HttpServletRequestListener {

	private VaadinTipiContext myContext;
	private ServletContext servletContext;
	private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	public void init() {

		setTheme("runo");
		final Window mainWindow = new Window("Tipi Vaadin");
		setMainWindow(mainWindow);
		try {
			setCurrentContext(createContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		checkForExtensions();
	}

	private void checkForExtensions() {
		File extensions = new File(getInstallationFolder(),"extensions");
		File[] list =extensions.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		System.err.println("FIles #"+list.length);
		for (File file : list) {
			TipiVaadinExtension.getInstance().installExtension(file);
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
//		preload.add(new TipiCoreExtension());
//		TipiVaadinExtension tipiVaadinExtension = new TipiVaadinExtension();
//		preload.add(tipiVaadinExtension);

		// preload with empty list is ok
		VaadinTipiContext va = new VaadinTipiContext(this, preload);
		va.setMainWindow(getMainWindow());

		va.setContextName(this.servletContext.getContextPath());

		checkForExtensions();
		System.err.println("Vaadin context:"+va.hashCode());
		TipiVaadinExtension.getInstance().getTipiExtensionRegistry().debugExtensions();
		TipiVaadinExtension.getInstance().getTipiExtensionRegistry().loadExtensions(va);
		
		// FIX multi parsing the core classdefs!
		
		((BrowserCookieManager)va.getCookieManager()).setRequest(request);
		((BrowserCookieManager)va.getCookieManager()).setResponse(response);

		
		try {
			loadTipi(va, "start.xml", TipiVaadinExtension.getInstance());
		} catch (TipiException e) {
			e.printStackTrace();
		}
		return va;
	}

	public void loadTipi(TipiContext newContext, String fileName,
			ExtensionDefinition ed) throws IOException, TipiException {
		System.err.println("Context: " + newContext + " filename: " + fileName);
		InputStream in = newContext.getTipiResourceStream(fileName);

		if (in != null) {
			newContext.parseStream(in, "startup", false, ed);
			newContext.switchToDefinition("startup");
			in.close();

		} else {
			throw new TipiException("Error loading tipi: " + fileName);
		}
	}

	@Override
	public void dispose(TipiContext t) {

	}

	@Override
	public String getDefinition() {
		return null;
	}

	@Override
	public void reboot() throws IOException, TipiException {
		this.close();
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;

	}

	public File getInstallationFolder() {
		return new File(
				InstallationPathResolver
						.getInstallationPath(this.servletContext));
	}

	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
		
	}
}
