package com.dexels.navajo.tipi.vaadin.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipipackage.TipiManualExtensionRegistry;
import tipivaadin.TipiVaadinExtension;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actionmanager.OSGiActionManager;
import com.dexels.navajo.tipi.classdef.OSGiClassManager;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.eval.EvalHandler;
import com.dexels.navajo.tipi.vaadin.components.io.URLInputStreamSource;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TipiVaadinTouchApplication extends Application implements TipiApplicationInstance, HttpServletRequestListener,
		Serializable {

	private static final long serialVersionUID = -5962249453869298788L;
	private VaadinTipiContext myContext;
	private transient ServletContext servletContext;
	private transient HttpServletRequest request;
	private transient HttpServletResponse response;
	
	// TODO: Refactor to URL
	private File installationFolder;
	private String applicationProfile;
	private String applicationDeploy;

	private final TipiManualExtensionRegistry extensionRegistry = new TipiManualExtensionRegistry();
//	private transient Timer shutdownTimer = null;
	private WindowCloseManager windowCloseManager;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinTouchApplication.class);

	@Override
	public void init() {
		ApplicationUtils.detectGae();
		actualInit();
	}



	protected void actualInit() {
		final WebApplicationContext context = ((WebApplicationContext) getContext());
		
		ApplicationUtils.setupContext(context);
		
		try {

			VerticalLayout componentContainer = new VerticalLayout();
			componentContainer.setSizeFull();
			final Window mainWindow = new Window("Tipi Vaadin", componentContainer);
			setMainWindow(mainWindow);
			EvalHandler eval = new EvalHandler(this);
			mainWindow.addParameterHandler(eval);
			mainWindow.addURIHandler(eval);
			if (ApplicationUtils.isRunningInGae()) {
				TipiCoreExtension tce = new TipiCoreExtension();
				TipiVaadinExtension tve = new TipiVaadinExtension();
				extensionRegistry.registerTipiExtension(tce);
				extensionRegistry.registerTipiExtension(tve);
			}

			try {
				setCurrentContext(createContext());

			} catch (Exception e) {
				e.printStackTrace();
			}
			windowCloseManager = new WindowCloseManager(this, getCurrentContext());

		} catch (Throwable t) {
			t.printStackTrace();
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

		try {
			logger.info("Entering file-based mode");
			setupInstallationFolder();
		} catch (TipiException e1) {
			throw new IOException("Error resolving tipi installation directory.", e1);
		}
		TipiVaadinExtension instance = TipiVaadinExtension.getInstance();
		if (!ApplicationUtils.isRunningInGae()) {
			ApplicationUtils.checkForExtensions(getInstallationFolder());
			instance.getTipiExtensionRegistry().debugExtensions();
		}

		VaadinTipiContext va;
		System.err.println("Extensionlist: "+extensionRegistry);
		try {
			va = new VaadinTipiContext(this, extensionRegistry.getExtensionList());
		} catch (Throwable e2) {
			e2.printStackTrace();
			return null;
		}
		va.setClassManager(new OSGiClassManager(TipiVaadinExtension.getInstance().getBundleContext(), va));
		va.setActionManager(new OSGiActionManager(TipiVaadinExtension.getInstance().getBundleContext()));
		logger.debug("VaadinTipiContext created. Cloudmode: "+ApplicationUtils.isRunningInGae());
		if (ApplicationUtils.isRunningInGae()) {
			extensionRegistry.loadExtensions(va);
		}
		try {
			BaseTipiApplicationInstance.processSettings(applicationDeploy, applicationProfile, installationFolder, va);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Coulnd not process settings. No worries");
		}

		String theme = va.getSystemProperty("tipi.vaadin.theme");
		logger.debug("Theme resolved to: "+theme);
		setTheme(theme);

		va.setMainWindow(getMainWindow());
		va.setContextName(this.servletContext.getContextPath());

		if (!ApplicationUtils.isRunningInGae()) {
			instance.getTipiExtensionRegistry().loadExtensions(va);
		} else {
			extensionRegistry.loadExtensions(va);
		}

		((BrowserCookieManager) va.getCookieManager()).setRequest(request);
		((BrowserCookieManager) va.getCookieManager()).setResponse(response);
		

		if(ApplicationUtils.isRunningInGae()) {
			logger.warn("Disabling compression due to NavajoClient/Listener bug, but forcing GZIP compression");
			va.getClient().setAllowCompression(false);
			va.getClient().setForceGzip(true);
		}
		try {
			loadTipi(va, "start.xml", instance);
		} catch (TipiException e) {
			e.printStackTrace();
		}
		return va;
	}

	


	public void loadTipi(TipiContext newContext, String fileName, ExtensionDefinition ed) throws IOException,
			TipiException {
		InputStream in = newContext.getTipiResourceStream(fileName);

		if (in != null) {
			newContext.parseStream(in, ed);
			newContext.switchToDefinition("startup");
			in.close();

		} else {
			throw new TipiException("Error loading tipi: " + fileName);
		}
	}

	@Override
	public void dispose(TipiContext t) {

	}
	
	public void setEvalUrl(URL context, String relativeUri) {
		((VaadinTipiContext)getCurrentContext()).setEvalUrl(context, relativeUri);
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

	private void setupInstallationFolder() throws TipiException {

		if (ApplicationUtils.isRunningInGae()) {
			this.applicationProfile = "knvb";
			this.applicationDeploy = "test";
			this.installationFolder = new File(servletContext.getRealPath("/application"));
			logger.info("Application dir resolved to: " + installationFolder.getAbsolutePath());
		} else {
			List<String> installationSettings = VaadinInstallationPathResolver.getInstallationPath(this.servletContext);
			String installationPath = installationSettings.get(0);
			if (installationSettings.size() > 1) {
				this.applicationDeploy = installationSettings.get(1);
			}
			if (installationSettings.size() > 2) {
				this.applicationProfile = installationSettings.get(2);
			}
			this.installationFolder = new File(installationPath);
		}
	}



	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		if(getCurrentContext()!=null) {
			if(getCurrentContext().getCookieManager()!=null) {
				((BrowserCookieManager) getCurrentContext().getCookieManager()).setRequest(request);
				((BrowserCookieManager) getCurrentContext().getCookieManager()).setResponse(response);
			}
		}

		if(!ApplicationUtils.isRunningInGae()) {
			windowCloseManager.cancelShutdownTimer();
		}
	}





	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		// clean up request refs? Also in CookieManager?
	}


	
	public  StreamResource getResource(Object u) {
		if (u == null) {
			return null;
		}
		String lastMimeType = null;
		StreamSource is = null;
		if (u instanceof URL) {
			if (ApplicationUtils.isRunningInGae()) {
				try {
					is = resolve((URL) u);
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} else {
					is = new URLInputStreamSource((URL) u);
			}
		}
		if (u instanceof Binary) {
			lastMimeType = ((Binary)u).guessContentType();
		}
		if (is == null) {
			return null;
		}
		
		StreamResource sr = new StreamResource(is, ""+u,this);
		sr.setMIMEType(lastMimeType);
		return sr;
	}

	private StreamSource resolve(URL u) throws IOException {
		return new URLInputStreamSource(u);
	}
	
}
