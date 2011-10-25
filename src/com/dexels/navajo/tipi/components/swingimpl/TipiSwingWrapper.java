package com.dexels.navajo.tipi.components.swingimpl;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiException;

import tipi.MainApplication;
import tipi.SwingTipiApplicationInstance;

public class TipiSwingWrapper {
	public static SwingTipiApplicationInstance runApp(BundleContext bundle, String appInstance) {
		try {
			SwingTipiApplicationInstance applicationInstance = MainApplication.runApp(bundle, appInstance);
			applicationInstance.setBundleContext(bundle);
			applicationInstance.setCurrentContext(applicationInstance.createContext());
			return applicationInstance;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
