package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import tipi.MainApplication;
import tipi.SwingTipiApplicationInstance;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

public class TipiSwingWrapper {
	public static SwingTipiApplicationInstance runApp(BundleContext bundle, String appInstance) throws TipiException {
		try {
			SwingTipiApplicationInstance applicationInstance = MainApplication.runApp(bundle, appInstance);
			applicationInstance.setBundleContext(bundle);
			TipiContext createContext = applicationInstance.createContext();
			applicationInstance.setCurrentContext(createContext);
			return applicationInstance;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
