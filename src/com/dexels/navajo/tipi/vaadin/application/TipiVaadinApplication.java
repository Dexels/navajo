package com.dexels.navajo.tipi.vaadin.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajo.ExtensionDefinition;
import tipi.TipiApplicationInstance;
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
	private File installationFolder;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinApplication.class);
	
	@Override
	public void init() {

		logger.debug("Theme hardcoded to oao. TODO fix");
		setTheme("oao");
		final Window mainWindow = new Window("Tipi Vaadin");
		setMainWindow(mainWindow);
		try {
			setCurrentContext(createContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		checkForExtensions();
	}



	private void checkForExtensions() throws IOException {
		File installationFolder = getInstallationFolder();
		logger.info("Loading extensions in: ",installationFolder.getAbsolutePath());
		TipiVaadinExtension.getInstance().initialializeExtension(installationFolder);
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
		try {
			setupInstallationFolder();
		} catch (ServletException e1) {
			throw new IOException("Error resolving tipi installation directory.",e1);
		}

		checkForExtensions();
		TipiVaadinExtension.getInstance().getTipiExtensionRegistry().debugExtensions();

		VaadinTipiContext va = new VaadinTipiContext(this);
		va.setMainWindow(getMainWindow());
		va.setContextName(this.servletContext.getContextPath());

		TipiVaadinExtension.getInstance().getTipiExtensionRegistry().loadExtensions(va);
		
		
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
		return this.installationFolder;
	}
	
	private void setupInstallationFolder() throws ServletException {
		String installationPath = InstallationPathResolver
				.getInstallationPath(this.servletContext);
		this.installationFolder =  new File(
				installationPath);
	}
//
//	public String setupInstallationFolder(String contextPath) throws ServletException, IOException {
//		String installationPath = InstallationPathResolver
//				.getInstallationFromPath(contextPath);
//		this.installationFolder =  new File(
//				installationPath);
//	}
	
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
