package com.dexels.navajo.tipi.application;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipi.TipiExtension;
import tipi.TipiMainExtension;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingWrapper;

@SuppressWarnings({"rawtypes","unused"})

public class ApplicationComponent {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationComponent.class);
	private TipiApplicationInstance instance;

	private ComponentContext componentContext;
	private final Map<String,TipiExtension> extensionList = new HashMap<String,TipiExtension>();
	private TipiMainExtension mainExtension;
	private TipiCoreExtension coreExtension;

	private String[] requireOptional = new String[]{
			"tipi.TipiSwingMigExtension",
			"tipi.TipiJabberExtension",
			"tipi.TipiMailExtension",
			"tipi.TipiRichExtension",
			"tipi.TipiCssExtension",
			"tipi.TipiSubstanceExtension"
			};
	
	
	private boolean isRunning = false;
	private boolean isActive = false;
	private Dictionary properties;
	
	public void activate(ComponentContext c) {
		this.componentContext = c;
		logger.info("Tipi Application Active");
		properties = c.getProperties();
		Enumeration en = properties.keys();
		while (en.hasMoreElements()) {
			Object key = (Object) en.nextElement();
			logger.info("Element: "+key+" : "+properties.get(key));
		}
		if(instance!=null) {
			instance.close();
		}
		isActive = true;
		if(verifyOptionalDeps()) {
			bootApplication((String) properties.get("tipi.context"));
		}

	}

	private boolean verifyOptionalDeps() {
		if(isRunning) {
			// already running
			logger.warn("Already running, not booting.");
			return false;
		}
		for(String te: requireOptional) {
			if(! isPresent(te)) {
				logger.warn("Aborting boot: missing extension: "+te);
				return false;
			}
		}
		return true;
	}

	private boolean isPresent(String te) {
		return extensionList.get(te)!=null;
	}

	public void deactivate() {
		logger.info("Deactivating tipi Application");
		isActive = false;
	}

	public void addTipiExtension(TipiExtension te) {
		logger.info("Adding extension: "+te.getId()+" current size: "+extensionList.size());
		extensionList.put(te.getClass().getCanonicalName(),te);
		if(verifyOptionalDeps()) {
			bootApplication((String) properties.get("tipi.context"));
		}

	}

	public void removeTipiExtension(TipiExtension te) {
		logger.info("R extension: "+te.getId()+" class: "+te.getClass()+" current size: "+extensionList.size());
		extensionList.remove(te);
	}

	public void setTipiMainExtension(TipiMainExtension te) {
		logger.info("setting main extension: "+te.getId()+" current size: "+extensionList.size());
		this.mainExtension = te;
	}
	public void clearTipiMainExtension(TipiMainExtension te) {
		logger.info("clearing main extension: "+te.getId()+" current size: "+extensionList.size());
		this.mainExtension = null;
	}
	public void setTipiCoreExtension(TipiCoreExtension te) {
		logger.info("setting main extension: "+te.getId()+" current size: "+extensionList.size());
		this.coreExtension = te;
	}
	public void clearTipiCoreExtension(TipiCoreExtension te) {
		logger.info("clearing main extension: "+te.getId()+" current size: "+extensionList.size());
		this.coreExtension = null;
	}
	
	private void bootApplication(final String context) {
		logger.info("====================\nStarting application\n====================\n context: "+context);
		this.isRunning = true;
		Thread t = new Thread() {


			@Override
			public void run() {
				try {
					System.err.println("Extensionlist at boot time: "+extensionList);
					instance = TipiSwingWrapper.runApp(componentContext.getBundleContext(),context);
					instance.getCurrentContext().switchToDefinition(instance.getDefinition());
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}



}
