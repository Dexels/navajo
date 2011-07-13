package com.dexels.navajo.tipi.vaadin.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiApplicationInstance;
import tipi.TipiVaadinExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

@SuppressWarnings("serial")
public class TipiVaadinApplication extends Application implements
		TipiApplicationInstance,HttpServletRequestListener {

	private VaadinTipiContext myContext;
	private ServletContext servletContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private File installationFolder;
	private String applicationProfile;
	private String applicationDeploy;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinApplication.class);
	
	@Override
	public void init() {


		VerticalLayout componentContainer = new VerticalLayout();
		componentContainer.setSizeFull();
		final Window mainWindow = new Window("Tipi Vaadin",componentContainer);
		setMainWindow(mainWindow);
		try {
			setCurrentContext(createContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainWindow.addListener(new Window.CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				System.err.println("Window close detected: TODO: Handle + close session");
			}
		});
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
		try {
			BaseTipiApplicationInstance.processSettings(applicationDeploy,applicationProfile,installationFolder,va);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Coulnd not process settings. No worries");
		}
		
		String theme = va.getSystemProperty("tipi.vaadin.theme");
//		if(theme==null) {
//			theme="oao";
//		}
		setTheme(theme);
		
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

//	private Map<String, String> processSettings(String deploy, String profile,  File installationFolder) throws IOException {
//
////		Map<String, String> bundleValues = getBundleMap("tipi.properties");
////		String deploy = bundleValues.get("deploy");
//		File settings = new File(installationFolder,"settings");
//
//		Map<String, String> bundleValues = getBundleMap("arguments.properties");
//		File profileProperties = new File(settings,"profiles/"+profile+".properties");
//		if(profileProperties.exists()) {
//			Map<String, String> profileValues = getBundleMap("profiles/"+profile+".properties");
//			bundleValues.putAll(profileValues);
//		} else {
//			System.err.println("No profile bundles present.");
//		}
//		System.err.println("Settings: "+bundleValues);
//		Map<String,String> resolvedValues = new HashMap<String, String>();
//		for (Entry<String,String> entry : bundleValues.entrySet()) {
//			if(entry.getKey().indexOf("/")<0) {
//				resolvedValues.put(entry.getKey(), entry.getValue());
//			} else {
//				String[] elts = entry.getKey().split("/");
//				if(elts[0].equals(deploy)) {
//					resolvedValues.put(elts[1], entry.getValue());
//				}
//			}
//			
//		}
//		System.err.println("RESOLVED TO: "+resolvedValues);
//		return resolvedValues;
//	}


//
//	private Map<String, String> getBundleMap(String path) throws FileNotFoundException, IOException {
//		File settings = new File(installationFolder,"settings");
//		FileReader argReader = new FileReader(new File(settings,path));
//		Map<String,String> bundleValues = new HashMap<String, String>();
//		PropertyResourceBundle prb = new PropertyResourceBundle(argReader);
//		for (String key : prb.keySet()) {
//			bundleValues.put(key, prb.getString(key));
//		}
//		return bundleValues;
//	}



	public void loadTipi(TipiContext newContext, String fileName,
			ExtensionDefinition ed) throws IOException, TipiException {
//		System.err.println("Context: " + newContext + " filename: " + fileName);
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
		List<String> installationSettings = InstallationPathResolver
				.getInstallationPath(this.servletContext);
		
		String installationPath = installationSettings.get(0);
		if(installationSettings.size()>1) {
			this.applicationDeploy = installationSettings.get(1);
		}
		if(installationSettings.size()>2) {
			this.applicationProfile = installationSettings.get(2);
		}
		this.installationFolder =  new File(
				installationPath);
//		if()
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
