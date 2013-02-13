package com.dexels.navajo.tipi.vaadin.instance.impl;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;

public class TipiVaadinInstanceImpl {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiVaadinInstanceImpl.class);
	private ServiceRegistration<Servlet> tipiServlet;
	
	public void activate(final Map<String,Object> settings, BundleContext bundleContext) {
		logger.info("Activating Tipi Instance: {}",settings);
		final String profile = (String) settings.get("tipi.instance.profile");
		final String deployment= (String) settings.get("tipi.instance.deployment");
		TipiVaadinServlet tvs = new TipiVaadinServlet();
		
		ContextInstance ci = new ContextInstance() {
			
			@Override
			public String getProfile() {
				return profile;
			}
			
			@Override
			public String getPath() {
				return (String) settings.get("tipi.instance.path");
			}
			
			@Override
			public String getDeployment() {
				return deployment;
			}
			@Override
			public String getContext() {
				return null;
			}
		};
		tvs.setContextInstance(ci);
		tipiServlet = registerServlet(bundleContext, "/"+deployment+"/"+profile, tvs);
	}

	protected ServiceRegistration<Servlet> registerServlet(BundleContext bundleContext,
			final String alias, Servlet s) {
		Dictionary<String, Object> vaadinRegistrationSettings = new Hashtable<String, Object>();
		 vaadinRegistrationSettings.put("alias", alias);
			return bundleContext.registerService(Servlet.class, s, vaadinRegistrationSettings);
	}

	public void deactivate() {
		tipiServlet.unregister();
	}
}
