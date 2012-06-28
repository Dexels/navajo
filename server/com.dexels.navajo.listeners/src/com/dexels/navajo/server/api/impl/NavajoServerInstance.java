package com.dexels.navajo.server.api.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;


public class NavajoServerInstance implements NavajoServerContext {

	private final String installationPath;
	private final DispatcherInterface dispatcher;

	public NavajoServerInstance(String installationPath,DispatcherInterface dispatcher) {
		this.installationPath = installationPath;
		this.dispatcher = dispatcher;
	}
	@Override
	public DispatcherInterface getDispatcher() {
		return dispatcher;
	}

	@Override
	public String getInstallationPath() {
		return installationPath;
	}
	public File getConfigRoot() throws IOException {
		return new File(new File(getInstallationPath()),"config/");
	}
	
	public Map<String,String> getClientSettingMap() throws IOException {
		Map<String,String> res = new HashMap<String, String>();
		ResourceBundle rs = getClientSettingsBundle();
		res.put("username",rs.getString("username"));
		res.put("password",rs.getString("password"));

		return res;
		
	}
	
	public ResourceBundle getClientSettingsBundle() throws IOException {
		File props = new File(getConfigRoot(),"client.properties");
		if(!props.exists()) {
			System.err.println("Not found.");
			return null;
		}
		FileReader fr = null;
		try {
		fr = new FileReader(props);
			PropertyResourceBundle b = new PropertyResourceBundle(fr);
			return b;
		} finally {
			fr.close();
		}
	}

}
