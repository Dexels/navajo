package com.dexels.navajo.tipi.vaadin.touch.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiApplicationInstance;
import tipi.TipiExtension;
import tipivaadin.TipiVaadinExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actionmanager.OSGiActionManager;
import com.dexels.navajo.tipi.classdef.OSGiClassManager;
import com.dexels.navajo.tipi.connectors.TipiConnector;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.locale.LocaleListener;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.VaadinInstallationPathResolver;
import com.dexels.navajo.tipi.vaadin.application.WindowCloseManager;
import com.dexels.navajo.tipi.vaadin.application.eval.EvalHandler;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.dexels.navajo.tipi.vaadin.touch.servlet.TipiVaadinTouchServlet;
import com.vaadin.addon.touchkit.ui.TouchKitApplication;
import com.vaadin.addon.touchkit.ui.TouchKitWindow;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;

public class TipiVaadinTouchApplication extends TouchKitApplication implements TipiApplicationInstance, HttpServletRequestListener,
		Serializable {

	private static final long serialVersionUID = -5962249453869298788L;
	private VaadinTipiContext myContext;
	private transient ServletContext servletContext;
	private transient HttpServletRequest request;
	private transient HttpServletResponse response;
	
	private URL urlContext;
	
	// TODO: Refactor to URL
	private File installationFolder;
	private String applicationProfile;
	private String applicationDeploy;

	private WindowCloseManager windowCloseManager;
	private String referer;
	private ContextInstance contextInstance;
	private TipiVaadinTouchServlet servlet;
	private final Set<TipiContextListener> tipiContextListeners = new HashSet<TipiContextListener>();
	private final Set<LocaleListener> localeListeners = new HashSet<LocaleListener>();

	private TipiConnector defaultConnector;
	private String region;
	private String language;
	private final List<TipiExtension> tipiExtensions = new ArrayList<TipiExtension>();

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinTouchApplication.class);

	@Override
	public void init() {
		actualInit();
	}


	@Override
	public void addTipiContextListener(TipiContextListener t) {
		tipiContextListeners.add(t);
	}
	
	
	protected void actualInit() {
		try {
			final TouchKitWindow mainWindow = new TouchKitWindow();
			configureMainWindow(mainWindow);
	        setMainWindow(mainWindow);
			windowCloseManager = new WindowCloseManager(this, getCurrentContext());
			EvalHandler eval = new EvalHandler(this);
			getMainWindow().addParameterHandler(eval);
			getMainWindow().addURIHandler(eval);
			setTheme("default");

		} catch (Throwable t) {
			logger.error("Error: ", t);
		}
	}

	private void configureMainWindow(TouchKitWindow mainWindow) {
		// These configurations modify how the app behaves as "ios webapp".
		logger.debug("Applicationurl: " + getURL());
		URL u = getURL();
		if (u == null) {
			return;
		}
		try {
			URL p = new URL(u.getProtocol(), u.getHost(), u.getPort(), "");
			logger.debug("Context Applicationurl: " + p);
			mainWindow
					.addApplicationIcon(p + "/VAADIN/themes/default/icon.png");
			mainWindow
					.setStartupImage(p + "/VAADIN/themes/default/startup.png");
			mainWindow.setWebAppCapable(true);
		} catch (MalformedURLException e) {
			logger.error("Error: ", e);
		}
		mainWindow.setOfflineTimeout(50000);
		// mainWindow.setPersistentSessionCookie(true);

	}
	
	@Override
	public void onBrowserDetailsReady() {
		try {
			setCurrentContext(createContext());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	private void setupView(NavigationView navigationView) {
//		navigationView.setCaption("aaaap");
//	}
//


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

		VaadinTipiContext va;
		try {
			va = new VaadinTipiContext(this, installationFolder, tipiExtensions,new HashMap<String, String>());
		} catch (Throwable e2) {
			logger.error("Error: ",e2);
			return null;
		}
		va.setClassManager(new OSGiClassManager(TipiVaadinExtension.getInstance().getBundleContext(), va));
		va.setActionManager(new OSGiActionManager(TipiVaadinExtension.getInstance().getBundleContext()));
		for (TipiContextListener t : tipiContextListeners) {
			t.setContext(va);
		}
		va.setDefaultConnector(defaultConnector);
		BaseTipiApplicationInstance.processSettings(applicationDeploy, applicationProfile, installationFolder, va);

		String theme = va.getSystemProperty("tipi.vaadin.theme");
		logger.info("Theme resolved to: "+theme);
		setTheme(theme);

		va.setMainWindow(getMainWindow());
		va.setContextName(this.servletContext.getContextPath());

		((BrowserCookieManager) va.getCookieManager()).setRequest(request);
		((BrowserCookieManager) va.getCookieManager()).setResponse(response);

		try {
			loadTipi(va, "start.xml", instance);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
		return va;
	}

	


	public void loadTipi(TipiContext newContext, String fileName, ExtensionDefinition ed) throws IOException,
			TipiException {
		InputStream in = newContext.getTipiResourceStream(fileName);

		if (in != null) {
			newContext.parseStream(in, ed);
			newContext.switchToDefinition("init");
			in.close();

		} else {
			throw new TipiException("Error loading tipi: " + fileName);
		}
	}

	@Override
	public void dispose(TipiContext t) {

	}
	
	@Override
	public void setEvalUrl(URL context, String relativeUri) {
		VaadinTipiContext vaadinTipiContext = (VaadinTipiContext)getCurrentContext();
		if(vaadinTipiContext!=null) {
//			vaadinTipiContext.setEvalUrl(context, relativeUri);
		}
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
		if (contextInstance != null) {
			this.installationFolder = new File(contextInstance.getPath());
			this.applicationDeploy = contextInstance.getDeployment();
			this.applicationProfile = contextInstance.getProfile();
		} else {
			List<String> installationSettings = VaadinInstallationPathResolver
					.getInstallationPath(this.servletContext);
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

		if(windowCloseManager!=null) {
			windowCloseManager.cancelShutdownTimer();
		}
		super.onRequestStart(request, response);
	}





	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		super.onRequestEnd(request, response);
	}



	@Override
	public void setContextUrl(URL contextUrl) {
		urlContext = contextUrl;		
	}



	@Override
	public URL getContextUrl() {
		return urlContext;
	}



	public void setReferer(String referer) {
		this.referer = referer;
	}



	public String getReferer() {
		return referer;
	}


	public void setContextInstance(ContextInstance ci) {
		this.contextInstance = ci;
	}
	
	public void clearContextInstance(ContextInstance ci) {
		this.contextInstance = null;
	}


	@Override
	public void close() {
		if(this.servlet!=null) {
			this.servlet.applicationClosed(this);
		}
		super.close();
	}



	public void setServlet(TipiVaadinTouchServlet tipiVaadinTouchServlet) {
		this.servlet = tipiVaadinTouchServlet;
	}


	@Override
	public void setDefaultConnector(TipiConnector tipiDefaultConnector) {
		this.defaultConnector = tipiDefaultConnector;
	}


	
	@Override
	public void setLocaleCode(String locale) {
		this.language = locale;
		for (LocaleListener l : localeListeners) {
			l.localeChanged(getCurrentContext(), language, region);
		}
	}
	@Override
	public String getLocaleCode() {
		return language;
	}
	@Override
	public void setSubLocaleCode(String region) {
		this.region = region;
		for (LocaleListener l : localeListeners) {
			l.localeChanged(getCurrentContext(), language, region);
		}
	}
	@Override
	public String getSubLocaleCode() {
		return region;
	}

	@Override
	public void addLocaleListener(LocaleListener l) {
		localeListeners.add(l);
	}

	@Override
	public void removeLocaleListener(LocaleListener l) {
		localeListeners.remove(l);
	}


	public void addExtension(TipiExtension tipiExtension) {
		this.tipiExtensions .add(tipiExtension);
	}

}
