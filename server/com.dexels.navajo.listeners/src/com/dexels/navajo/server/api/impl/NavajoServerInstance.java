package com.dexels.navajo.server.api.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoServerInstance implements NavajoServerContext {
    private static final Logger logger = LoggerFactory.getLogger(NavajoServerInstance.class);

	private final String installationPath;
//	private final DispatcherInterface dispatcher;

	public NavajoServerInstance(String installationPath,
			DispatcherInterface dispatcher) {
		this.installationPath = installationPath;
//		this.dispatcher = dispatcher;
	}


	@Override
	public String getInstallationPath() {
		return installationPath;
	}

	public File getConfigRoot() {
		return new File(new File(getInstallationPath()), "config/");
	}

	public Map<String, String> getClientSettingMap() throws IOException {
		Map<String, String> res = new HashMap<String, String>();
		ResourceBundle rs = getClientSettingsBundle();
		res.put("user", rs.getString("username"));
		res.put("password", rs.getString("password"));

		return res;

	}

	public ResourceBundle getClientSettingsBundle() throws IOException {
		File props = new File(getConfigRoot(), "client.properties");
		if (!props.exists()) {
			logger.warn("Not found.");
			return null;
		}
		FileReader fr = null;
		try {
			fr = new FileReader(props);
			PropertyResourceBundle b = new PropertyResourceBundle(fr);
			return b;
		} finally {
			if (fr != null) {
				fr.close();
			}
		}
	}


	@Override
	public String getOutputPath() {
		return installationPath;
	}


	@Override
	public String getTempPath() {
		return installationPath;
	}


    @Override
    public String getDeployment() {
        logger.warn("getDeployment not implemented in OSGi implementation");
        return null;
    }

}
