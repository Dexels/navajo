package com.dexels.navajo.tipi.vaadin.instance.impl;

import java.io.File;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.application.servlet.VaadinFileServlet;

public class TipiVaadinGlobalInstance {
	
	private ServiceRegistration<Servlet> fileRegistration;
	private BundleContext bundleContext = null;

	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinGlobalInstance.class);
	
	public void activate(final Map<String,Object> settings, BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		String root = (String) settings.get("rootPath");
		logger.info("========>  Activating");
		File rootFolder = new File(root);

		registerFileServlet(rootFolder.getAbsolutePath(), bundleContext);
	}
	
	private void registerFileServlet(final String rootPath, BundleContext bundleContext) {
		VaadinFileServlet vfs = new VaadinFileServlet();
		ContextInstance ci = new ContextInstance() {
			
			@Override
			public String getProfile() {
				throw new UnsupportedOperationException("The Tipi File servlet is not profile dependent.");
			}
			
			@Override
			public String getPath() {
				return rootPath;
			}
			
			@Override
			public String getDeployment() {
				throw new UnsupportedOperationException("The Tipi File servlet is not deployment dependent.");
			}
			@Override
			public String getContext() {
				return "unknown context";
			}
		};
		vfs.setContextInstance(ci);
		fileRegistration = registerServlet(bundleContext, "/VAADIN", vfs);
	}

	protected ServiceRegistration<Servlet> registerServlet(BundleContext bundleContext,
			final String alias, Servlet s) {
		Dictionary<String, Object> vaadinRegistrationSettings = new Hashtable<String, Object>();
		 vaadinRegistrationSettings.put("alias", alias);
			return bundleContext.registerService(Servlet.class, s, vaadinRegistrationSettings);
	}
	public void deactivate() {
		logger.info(">>>>>> deactivating tipi global provider");
		if(fileRegistration!=null) {
			fileRegistration.unregister();
		}
		
	}
}
