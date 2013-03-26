package com.dexels.navajo.tipi.vaadin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;
import tipi.TipiExtension;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication;
import com.dexels.navajo.tipi.vaadin.components.io.URLInputStreamSource;
import com.dexels.navajo.tipi.vaadin.cookie.BrowserCookieManager;
import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class VaadinTipiContext extends TipiContext {
 
	private static final long serialVersionUID = -5277282822136332687L;

	private static Logger logger = LoggerFactory.getLogger(VaadinTipiContext.class);

	private File installationFolder = null;
	private Window mainWindow;
//	private URL evalUrl;
	private String contextName = null;
	private final java.util.Random randomizer = new java.util.Random(System.currentTimeMillis());

	
	public VaadinTipiContext(TipiApplicationInstance myApplication, File install, List<TipiExtension> extensionList) {
		super(myApplication, extensionList, null);
		
		
//		File install = getInstallationFolder();
		setTipiInstallationFolder(install);
		setInstallationFolder(install);
		setCookieManager(new BrowserCookieManager());
		TipiScreen ts = new TipiScreen(this);
		setDefaultTopLevel(ts);
	}




	/**
	 * Maybe we can loosen up this constraint, at some point, but for now: GAE doesn't like it.
	 */
	@Override
	public int getPoolSize() {
		return 0;
	}

	@Override
	public void doExit() {
		super.doExit();
// TODO FIX
		getVaadinApplication().close();
	}



	@Override
	public void shutdown() {
		super.shutdown();
	}



	public String getContextName() {
		return contextName;
	}


	public void setContextName(String contextName) {
		this.contextName = contextName;
	}


	@Override
	public void runSyncInEventThread(Runnable r) {
		r.run();
	}

	@Override
	public void runAsyncInEventThread(Runnable r) {
		r.run();

	}

	@Override
	public void setSplash(Object s) {
		logger.info("Splash {}",s);
	}

	@Override
	public void clearTopScreen() {

	}

	@Override
	public void setSplashVisible(boolean b) {

	}

	@Override
	public void setSplashInfo(String s) {
		logger.info("Splash {}",s);

	}
	
	@Override
	public void showWarning(String text, String title)
	{
		Notification not = new Notification(text, title, Notification.TYPE_WARNING_MESSAGE);
		mainWindow.showNotification(not);
	}

	
	@Override
	public void showError(String text, String title)
	{
		Notification not = new Notification(text, title, Notification.TYPE_ERROR_MESSAGE);
		mainWindow.showNotification(not);
	}

	@Override
	public void showInfo(String text, String title) {
		Notification not = new Notification(text, title);
		mainWindow.showNotification(not);
	}

	@Override
	public void showQuestion(String text, String title, String[] options)
			throws TipiBreakException {

	}

	public void setMainWindow(Window mainWindow) {
		this.mainWindow = mainWindow;
	}

	public TipiApplicationInstance getVaadinApplication() {
		return this.getApplicationInstance();
	}

	public Application getApplication() {
		return (Application) this.getApplicationInstance();
	}


	public URL getEvalUrl(String expression, String mime) {
		try {
			String encoded = URLEncoder.encode(expression,"UTF-8");
			TipiVaadinApplication tva = (TipiVaadinApplication) getVaadinApplication();
			String referer = tva.getReferer();
			if(referer!=null) {
				URL contextUrl = getVaadinApplication().getContextUrl();
				String path = contextUrl.getPath()+"?evaluate="+encoded;
				if(mime!=null) {
					path = path+"&mime="+mime;
				}
				URL prot = new URL(contextUrl.getProtocol(),referer,path);
				return  prot;
			} else {
				URL contextUrl = getVaadinApplication().getContextUrl();
				String s = contextUrl+"?rdm="+randomizer.nextLong()+"&evaluate="+encoded;
				if(mime!=null) {
					s = s+"&mime="+mime;
				}
				return new URL(s);
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("I just don't know",e);
		} catch (MalformedURLException e) {
			logger.error("I just don't know",e);
		}
		return null;
	}

//	public void setEvalUrl(URL context, String relativeUri) {
//	}	
	
	public String createExpressionUrl(String expression, String mime) {
		return getEvalUrl(expression,mime).toString();
	}




	public File getInstallationFolder() {
		return installationFolder;
	}

	public void setInstallationFolder(File installationFolder) {
		this.installationFolder = installationFolder;
	}
	
	
	public  StreamResource getResource(Object u) {
		if (u == null) {
			return null;
		}
		String lastMimeType = null;
		StreamSource is = null;
		if (u instanceof URL) {
			is = new URLInputStreamSource((URL) u);
		}
		if (u instanceof Binary) {
			lastMimeType = ((Binary)u).guessContentType();
		}
		if (is == null) {
			return null;
		}
		StreamResource sr = new StreamResource(is, ""+u,getApplication());
		sr.setMIMEType(lastMimeType);
		return sr;
	}
	
}
