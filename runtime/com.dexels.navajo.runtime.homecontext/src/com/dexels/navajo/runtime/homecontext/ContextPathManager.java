package com.dexels.navajo.runtime.homecontext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContextPathManager {
	
	private ConfigurationAdmin configurationAdmin = null;
	private final Set<ContextIdentifier> contextIdentifiers = new HashSet<ContextIdentifier>();
	private final Set<Configuration> configurations = new HashSet<Configuration>();

	private final static Logger logger = LoggerFactory
			.getLogger(ContextPathManager.class);
	
	public void activate() throws IOException {
		logger.info("Activating. My context is: "+System.getProperty("navajo.context"));
		injectAll(new String[]{"navajo"});

	}


	private void injectAll(String[] resourceTypes) throws IOException {
		for (String resourceType : resourceTypes) {
			Map<String, String> systemContexts = loadSystemContexts(resourceType);
			inject(systemContexts,resourceType);
		}
	}

	private Set<String> getContexts() {
		Set<String> result = new HashSet<String>();
		for (ContextIdentifier ci : contextIdentifiers) {
			String path = ci.getContextPath();
			if(path!=null) {
				result.add(path);
			}
		}
		return result;
	}
	
	// TODO do this in a more thread safe manner?
	public void deactivate() {
		logger.info("Deactivating: "+configurations.size()+" configurations.");
		for (Configuration c: configurations) {
			try {
				c.delete();
			} catch (IOException e) {
				logger.error("Problem deleting navajo configuration: ", e);
			}
		}
		configurations.clear();
	}
	

	
	private void inject(Map<String, String> systemContexts, String resourceType) throws IOException {
		Configuration c = configurationAdmin.getConfiguration( "com.dexels.navajo.runtime.obr",null);
		Dictionary<String, Object> d = new Hashtable<String,Object>();
		Set<String> myContexts = getContexts();
		for (String cxt : myContexts) {
			String context = systemContexts.get(cxt);
			if(context==null) {
				logger.warn("Skipping context: "+cxt+" as it is null.");
			} else {
				List<String> contextPath = parseContext(context);
				d.put("contextPath", contextPath.get(0));
				if(contextPath.size()>1) {
					d.put("deployment", contextPath.get(1));
				}
				if(contextPath.size()>2) {
					d.put("profile", contextPath.get(2));
				}
			}
		}
		if(d.get("contextPath")==null) {
			logger.warn("No system context found for resourceType: "+resourceType+" skipping injection");
			return;
		}
		d.put("resourceType", resourceType);
		configurations.add(c);
		c.update(d);
	}


	
	public void setConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = ca;
	}

	public void removeConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = null;
	}

	// TODO, refine this to support multiple context identifiers
	public void removeContextIdentifier(ContextIdentifier ci) {
		contextIdentifiers.remove(ci);
	}

	public void addContextIdentifier(ContextIdentifier ci) {
		contextIdentifiers.add(ci);
	}

	
	private Map<String, String> loadSystemContexts(String resourceType) throws IOException {
		File home = new File(System.getProperty( "user.home"));
		File navajo = new File(home, resourceType+".properties");
		Map<String, String> systemContexts = new HashMap<String, String>();
		if (!navajo.exists()) {
			return systemContexts;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));

		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			String[] r = line.split("=");
			systemContexts.put(r[0], r[1]);
		}

		br.close();
		return systemContexts;
	}
	
	private static List<String> parseContext(String context) {
		StringTokenizer st = new StringTokenizer(context, "|");
		List<String> result = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			String part =st.nextToken();
			result.add(part);

		}
		return result;
	}
}
