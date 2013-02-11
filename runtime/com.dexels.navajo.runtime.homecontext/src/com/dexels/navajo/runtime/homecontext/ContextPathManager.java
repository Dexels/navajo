package com.dexels.navajo.runtime.homecontext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextPathManager {

	private ConfigurationAdmin configurationAdmin = null;
	private final Set<Configuration> configurations = new HashSet<Configuration>();

	private final static Logger logger = LoggerFactory
			.getLogger(ContextPathManager.class);

	public void activate() throws IOException {
		// TODO skip pull config for now
		injectAll(new String[] { "com.dexels.navajo.runtime.provisioning.push" });

		
		// injectAll(new
		// String[]{"com.dexels.navajo.runtime.provisioning.push","com.dexels.navajo.runtime.provisioning.pull"});

	}

	private void injectAll(String[] resourceTypes) throws IOException {
		for (String resourceType : resourceTypes) {
			Map<String, String> systemContexts = loadSystemContexts(resourceType);
			inject(systemContexts, resourceType);
//			navajo.context
	
			// bit odd to do it here, we need some refactoring.
			String forcedNavajoPath = System.getProperty("navajo.context");
			if(forcedNavajoPath!=null) {
				String resolved = systemContexts.get(forcedNavajoPath);
				if(resolved!=null) {
					logger.info("Injecting default context: "+forcedNavajoPath+" resolved to: "+resolved);
					createDefaultContext(forcedNavajoPath,resolved);
				}
			}
		}
	}

	// private Set<String> getContexts() {
	// Set<String> result = new HashSet<String>();
	// for (ContextIdentifier ci : contextIdentifiers) {
	// String path = ci.getContextPath();
	// if(path!=null) {
	// result.add(path);
	// }
	// }
	// return result;
	// }

	private void createDefaultContext(String forcedNavajoPath, String resolved) throws IOException {
		Configuration cc = configurationAdmin.getConfiguration("navajo.server.http.osgi");
		Dictionary<String, Object> settings = new Hashtable<String, Object>();
		settings.put("contextPath", forcedNavajoPath);
		settings.put("installationPath", resolved);
		cc.update(settings);
	}

	// TODO do this in a more thread safe manner?
	public void deactivate() {
		logger.info("Deactivating: " + configurations.size()
				+ " configurations.");
		for (Configuration c : configurations) {
			try {
				c.delete();
			} catch (IOException e) {
				logger.error("Problem deleting navajo configuration: ", e);
			}
		}
		configurations.clear();
	}

	private void inject(Map<String, String> systemContexts,
			String provisioningType) throws IOException {
		logger.info("Activating contextpathmanager.");
		for (String cxt : systemContexts.keySet()) {
			String context = systemContexts.get(cxt);
			if (context == null) {
				logger.error("Skipping context: " + context
						+ " as it is null. And THAT is weird.");
			} else {
				Configuration c = configurationAdmin
						.createFactoryConfiguration(provisioningType, null);
				Dictionary<String, Object> d = new Hashtable<String, Object>();
				List<String> contextPath = parseContext(context);
				d.put("contextName", cxt);
				d.put("contextPath", contextPath.get(0));
				if (contextPath.size() > 1) {
					d.put("deployment", contextPath.get(1));
				}
				if (contextPath.size() > 2) {
					d.put("profile", contextPath.get(2));
				}
				d.put("provisioningType", provisioningType);
				configurations.add(c);
				c.update(d);

			}
		}
		// if(d.get("contextPath")==null) {
		// logger.warn("No system context found for provisioningType: "+provisioningType+" skipping injection");
		// return;
		// }
	}

	public void setConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = ca;
	}

	/**
	 * @param ca the configadmin to remove 
	 */
	public void removeConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = null;
	}

	private Map<String, String> loadSystemContexts(String provisioningType)
			throws IOException {
		File home = new File(System.getProperty("user.home"));

		File navajo = new File(home, getPropertyNameForType(provisioningType)
				+ ".properties");
		Map<String, String> systemContexts = new HashMap<String, String>();
		if (!navajo.exists()) {
			return systemContexts;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));
		ResourceBundle rb = new PropertyResourceBundle(br);
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			systemContexts.put(key, value);
		}
		br.close();
		return systemContexts;
	}

	/**
	 * Not beautiful.
	 * 
	 * @param provisioningType
	 * @return
	 */
	private String getPropertyNameForType(String provisioningType) {
		if (provisioningType.endsWith("push")) {
			return "navajo";
		}
		return "tipi";
	}

	private List<String> parseContext(String context) {
		StringTokenizer st = new StringTokenizer(context, "|");
		List<String> result = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			String part = st.nextToken();
			result.add(part);

		}
		return result;
	}
}
