package com.dexels.navajo.tipi.vaadin.application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metadata.FormatIdentification;
import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipipackage.TipiExtensionRegistry;
import tipivaadin.TipiVaadinExtension;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.VaadinTipiContext;
import com.dexels.navajo.tipi.vaadin.application.eval.EvalHandler;
import com.dexels.navajo.tipi.vaadin.components.io.BufferedInputStreamSource;
import com.dexels.navajo.tipi.vaadin.components.io.URLInputStreamSource;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;

public class TipiVaadinApplication extends Application implements TipiApplicationInstance, HttpServletRequestListener,
		Serializable {

	private static final long serialVersionUID = -5962249453869298788L;
	private VaadinTipiContext myContext;
	private transient ServletContext servletContext;
	private transient HttpServletRequest request;
	private transient HttpServletResponse response;
	private File installationFolder;
	private String applicationProfile;
	private String applicationDeploy;
	private String lastMimeType;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinApplication.class);

	private final TipiExtensionRegistry extensionRegistry = new TipiExtensionRegistry();

	private boolean isRunningInGae = false;
	private transient Timer shutdownTimer = null;
	

	@Override
	public void init() {
		detectGae();
		actualInit();
	}



	protected void actualInit() {
		try {

			VerticalLayout componentContainer = new VerticalLayout();
			componentContainer.setSizeFull();
			final Window mainWindow = new Window("Tipi Vaadin", componentContainer);
			setMainWindow(mainWindow);
			EvalHandler eval = new EvalHandler(this);
			mainWindow.addParameterHandler(eval);
			mainWindow.addURIHandler(eval);
			if (isRunningInGae()) {
				// extensionRegistry = new TipiExtensionRegistry();
				TipiCoreExtension tce = new TipiCoreExtension();
				TipiVaadinExtension tve = new TipiVaadinExtension();
//				tce.loadDescriptor();
//				tve.loadDescriptor();
				// special case for TipiCoreExtension, as it is not the bundle
				// activator
				// TODO Maybe refactor
				extensionRegistry.registerTipiExtension(tce);
				extensionRegistry.registerTipiExtension(tve);
			}

			try {
				setCurrentContext(createContext());
//				testSerializability(getCurrentContext());

			} catch (Exception e) {
				e.printStackTrace();
			}
			mainWindow.addListener(new Window.CloseListener() {

				private static final long serialVersionUID = 1604878118381797590L;

				@Override
				public void windowClose(CloseEvent e) {
					System.err.println("WINDOW CLOSE EVENT DETECTED!");
					handleWindowClose();
					
				}
			});

			// checkForExtensions();
//			testSerializability(this);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	protected void handleWindowClose() {
		if(!isRunningInGae()) {
			 shutdownTimer  = new Timer("DisconnectTimer",true);
			 shutdownTimer.schedule(new TimerTask(){

				@Override
				public void run() {
					System.err.println("Shutdown firing!");
					getCurrentContext().shutdown();
					close();
					logger.info("Shutting down instance. Goodbye");
				}}, 10000);
		}
	}

	private void cancelShutdownTimer() {
		if(shutdownTimer!=null) {
			logger.info("Assuming reload. Cancelling shutdown.");
			shutdownTimer.cancel();
			shutdownTimer = null;
		}
	}


	protected void testSerializability(Object element) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(element);
		} catch (Throwable e1) {

			e1.printStackTrace();
		}
	}

	private void checkForExtensions() throws IOException {
		File installationFolder = getInstallationFolder();
		logger.info("Loading extensions in: ", installationFolder.getAbsolutePath());
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
			logger.info("Entering file-based mode");
			setupInstallationFolder();
		} catch (ServletException e1) {
			throw new IOException("Error resolving tipi installation directory.", e1);
		}
		TipiVaadinExtension instance = TipiVaadinExtension.getInstance();
		if (!isRunningInGae) {
			checkForExtensions();
			instance.getTipiExtensionRegistry().debugExtensions();
		}

		VaadinTipiContext va;
		try {
			va = new VaadinTipiContext(this, extensionRegistry.getExtensionList());
		} catch (Throwable e2) {
			e2.printStackTrace();
			return null;
		}
		logger.debug("VaadinTipiContext created. Cloudmode: "+isRunningInGae);
		if (isRunningInGae) {
			extensionRegistry.loadExtensions(va);
		}
		try {
			BaseTipiApplicationInstance.processSettings(applicationDeploy, applicationProfile, installationFolder, va);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.err.println("Coulnd not process settings. No worries");
		}

		String theme = va.getSystemProperty("tipi.vaadin.theme");
		// if(theme==null) {
		// theme="oao";
		// }
		logger.debug("Theme resolved to: "+theme);
		setTheme(theme);

		va.setMainWindow(getMainWindow());
		va.setContextName(this.servletContext.getContextPath());

		if (!isRunningInGae) {
			instance.getTipiExtensionRegistry().loadExtensions(va);
		} else {
			extensionRegistry.loadExtensions(va);
		}

		((BrowserCookieManager) va.getCookieManager()).setRequest(request);
		((BrowserCookieManager) va.getCookieManager()).setResponse(response);
		

		if(isRunningInGae()) {
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

	
	public boolean isRunningInGae() {
		return isRunningInGae;
	}

	public void setRunningInGae(boolean isRunningInGae) {
		this.isRunningInGae = isRunningInGae;
	}

	/**
	 * Detect if we're in the App Engine
	 */
	private void detectGae() {
		try {
			Class.forName("com.google.appengine.api.LifecycleManager");
			this.isRunningInGae = true;
		} catch (ClassNotFoundException e) {
			this.isRunningInGae = false;
		}
		
	}

	public void loadTipi(TipiContext newContext, String fileName, ExtensionDefinition ed) throws IOException,
			TipiException {
		// System.err.println("Context: " + newContext + " filename: " +
		// fileName);
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

	private void setupInstallationFolder() throws ServletException {

		if (isRunningInGae) {
			this.applicationProfile = "knvb";
			this.applicationDeploy = "test";
			this.installationFolder = new File(servletContext.getRealPath("/application"));
			logger.info("Application dir resolved to: " + installationFolder.getAbsolutePath());
		} else {
			List<String> installationSettings = InstallationPathResolver.getInstallationPath(this.servletContext);

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

	//
	// public String setupInstallationFolder(String contextPath) throws
	// ServletException, IOException {
	// String installationPath = InstallationPathResolver
	// .getInstallationFromPath(contextPath);
	// this.installationFolder = new File(
	// installationPath);
	// }

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

		if(!isRunningInGae()) {
			cancelShutdownTimer();
		}
	}





	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		// clean up request refs? Also in CookieManager?
	}


	
	public StreamResource getResource(Object u) {
		if (u == null) {
			return null;
		}
		StreamSource is = null;
		if (u instanceof URL) {
			if (isRunningInGae()) {
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
		
		
//		getVaadinApplication().getMainWindow().
		sr.setMIMEType(lastMimeType);
		System.err.println("Stream resource created: " + u.toString()+" mime: "+lastMimeType);

		return sr;
	}

	private StreamSource resolve(URL u) throws IOException {
		return new URLInputStreamSource(u);
	}
	
	@SuppressWarnings("unused")
	private StreamSource resolveAndBuffer(URL u) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = u.openStream();
		copyResource(baos, is);
		byte[] byteArray = baos.toByteArray();
		this.lastMimeType = FormatIdentification.identify(byteArray).getMimeType();
		return new BufferedInputStreamSource(byteArray);
	}
	
	protected final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
			}
			if (read <= -1) {
				ready = true;
			}
		}
		bin.close();
		bout.flush();
		bout.close();
	}

	
	
	
}
