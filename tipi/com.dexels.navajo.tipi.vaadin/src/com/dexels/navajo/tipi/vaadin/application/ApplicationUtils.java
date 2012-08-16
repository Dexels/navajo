package com.dexels.navajo.tipi.vaadin.application;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiVaadinExtension;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.terminal.gwt.server.WebBrowser;

public class ApplicationUtils {
	
	private static boolean isRunningInGae = false;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);

	public static boolean isRunningInGae() {
		return isRunningInGae;
	}

	public static void setRunningInGae(boolean isRunningInGae) {
		ApplicationUtils.isRunningInGae = isRunningInGae;
	}

	/**
	 * Detect if we're in the App Engine
	 */
	public static void detectGae() {
		try {
			Class.forName("com.google.appengine.api.LifecycleManager");
			ApplicationUtils.isRunningInGae = true;
		} catch (ClassNotFoundException e) {
			ApplicationUtils.isRunningInGae = false;
		}
		
	}


	public static void setupContext(final WebApplicationContext context) {
		SessionTokenFactory.setSessionTokenProvider(new SessionTokenProvider() {
			@Override
			public String getSessionToken() {
				return context.getHttpSession().getId();
			}
		});

		final WebBrowser wb = context.getBrowser(); //.getBrowserApplication();
		SystemInfoFactory.setSystemInfoProvider(new SystemInfoProvider() {
			
			@Override
			public void init() {
				
			}
			
			@Override
			public String getOsVersion() {
				
				return null;
			}
			
			@Override
			public String getOsArch() {
				return null;
			}
			
			@Override
			public String getOs() {
				return null;
			}
			
			@Override
			public long getMaxMem() {
				return 0;
			}
			
			@Override
			public String getJavaVersion() {
				return null;
			}
			
			@Override
			public int getCpuCount() {
				return -1;
			}
			
			public String toString() {
				return wb.getBrowserApplication();
				
			}
		});
	}

	// I don't think this one is still necessary
	public static void checkForExtensions(File installationFolder) {
		logger.info("Loading extensions in: ", installationFolder.getAbsolutePath());
		TipiVaadinExtension.getInstance().initialializeExtension(installationFolder);
	}

}
