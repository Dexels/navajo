package com.dexels.navajo.tipi.application;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingWrapper;

import tipi.TipiApplicationInstance;
import tipi.TipiCoreExtension;
import tipi.TipiExtension;
import tipi.TipiMainExtension;


public class ApplicationComponent {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationComponent.class);
	private TipiApplicationInstance instance;

	private ComponentContext componentContext;
	private final List<TipiExtension> extensionList = new ArrayList<TipiExtension>();
	private TipiMainExtension mainExtension;
	private TipiCoreExtension coreExtension;
	public void activate(ComponentContext c) {
		this.componentContext = c;
		logger.info("Tipi Application Active");
		bootApplication();
		instance.close();
	}

	public void deactivate() {
		logger.info("Deactivating tipi Application");
	}

	public void addTipiExtension(TipiExtension te) {
		logger.info("Adding extension: "+te.getId()+" current size: "+extensionList.size());
		extensionList.add(te);
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
	
	private void bootApplication() {
		final String context = System.getProperty("tipi.context");
		logger.info("====================\nStarting application\n====================\n context: "+context);
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
