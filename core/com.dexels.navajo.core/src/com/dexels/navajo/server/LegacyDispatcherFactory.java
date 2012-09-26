package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;

public class LegacyDispatcherFactory {

	private static final int MAX_ACCESS_SET_SIZE = 50;
	private final static Logger logger = LoggerFactory
			.getLogger(LegacyDispatcherFactory.class);

	private NavajoServerContext navajoContext;
//	private NavajoServerInstance wrapped = null;
	private ConfigurationAdmin myConfigurationAdmin = null;
	private final Set<Configuration> registeredConfigurations = new HashSet<Configuration>();
	
	public void setContext(NavajoServerContext nsc) {
		logger.info("LocalClient linked to context");
		this.navajoContext = nsc;
	}

	
	/**
	 * @param nsc the context to remove 
	 */
	public void removeContext(NavajoServerContext nsc) {
		this.navajoContext = null;
	}
		

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = null;
	}

	
	
	public void activate() {
		logger.info("Activating HTTP server component");
		try {
			String rootPath = this.navajoContext.getInstallationPath();
			File rp = new File(rootPath);
			File serverXml = new File(rp,"config/server.xml");
			URL srv = serverXml.toURI().toURL();
			final String absolutePath = properDir(rp.getAbsolutePath());
			createNavajoConfigConfiguration(srv, absolutePath);
		} catch (MalformedURLException e) {
			logger.error("Parse error: ",e);
		} catch (IOException e) {
			logger.error("IO error: ",e);
		} catch (SystemException e) {
			logger.error("System error: ",e);
		} catch(Throwable e) {
			logger.error("Unexpected error activating dispatcher configuration factory: ",e);
		}
	}

	public void deactivate() {
		for (Configuration c : registeredConfigurations) {
			try {
				c.delete();
			} catch (IOException e) {
				logger.error("Problem deleting configuraiton: "+c.getPid(),e);
			}
		}
		registeredConfigurations.clear();
	}	
	
	private void createNavajoConfigConfiguration(URL configurationUrl,
			String rootPath) throws IOException, SystemException {
		InputStream is = configurationUrl.openStream();
		logger.info("Setting up configuration for rootpath: "+rootPath);
		Dictionary<String, Object> data = new Hashtable<String, Object>();
		Navajo configuration = NavajoFactory.getInstance().createNavajo(is);

		Message body = configuration.getMessage("server-configuration");
		if (body == null) {
			throw new SystemException(-1,
					"Could not read configuration file server.xml");
		}

		// Get the instance name.
		String instanceName = (body.getProperty("instance_name") != null ? body
				.getProperty("instance_name").getValue() : null);
		if (instanceName == null) {
			throw new IllegalArgumentException(
					"instance_name in server.xml not found.");
		}
		data.put("instanceName", instanceName);
		// Get the instance group.
		String instanceGroup = (body.getProperty("instance_group") != null ? body
				.getProperty("instance_group").getValue() : null);
		if (instanceGroup == null) {
			throw new IllegalArgumentException(
					"instance_group in server.xml not found.");
		}
		data.put("instanceGroup", instanceGroup);

//		File rootFile = new File(rootPath);

		String configPath = properDir(rootPath
				+ body.getProperty("paths/configuration").getValue());
		String adapterPath = properDir(rootPath
				+ body.getProperty("paths/adapters").getValue());
		String scriptPath = properDir(rootPath
				+ body.getProperty("paths/scripts").getValue());
		data.put("configPath", configPath);
		data.put("adapterPath", adapterPath);
		data.put("scriptPath", scriptPath);
		// changed to more defensive behaviour
		Property resourceProperty = body.getProperty("paths/resource");
		if (resourceProperty != null) {
			String resourcePath = properDir(rootPath
					+ resourceProperty.getValue());
			data.put("resourcePath", resourcePath);

		}

		String compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ? properDir(rootPath
				+ body.getProperty("paths/compiled-scripts").getValue())
				: "");
		data.put("compiledScriptPath", compiledScriptPath);

		// Read monitoring configuration options
		Property monitoringAgentClass = body
				.getProperty("monitoring-agent/class");
		Property monitoringAgentProperties = body
				.getProperty("monitoring-agent/properties");
		// Set properties.
		if (monitoringAgentProperties != null) {
			String[] properties = monitoringAgentProperties.getValue().split(
					";");
			for (int i = 0; i < properties.length; i++) {
				String[] keyValue = properties[i].split("=");
				String key = (keyValue.length > 1) ? keyValue[0] : "";
				String value = (keyValue.length > 1) ? keyValue[1] : "";
				System.setProperty(key, value);
			}
		}
		if (monitoringAgentClass != null) {
			AgentFactory.getInstance(monitoringAgentClass.getValue()).start();
		}

//		Message descriptionMessage = body.getMessage("description-provider");
		Property descriptionProviderProperty = body
				.getProperty("description-provider/class");
		String descriptionProviderClass = null;
		if (descriptionProviderProperty != null) {
			descriptionProviderClass = descriptionProviderProperty.getValue();
			if (descriptionProviderClass != null) {
				data.put("descriptionProviderClass", descriptionProviderClass);
			}
		}

		if (body.getProperty("repository/class") != null) {
			String repositoryClass = body.getProperty("repository/class")
					.getValue();
			data.put("repositoryClass", repositoryClass);
		}

		// Read navajostore parameters.

		Message navajostore = body.getMessage("navajostore");
		if (navajostore != null) {
			String store = (navajostore.getProperty("store") != null ? navajostore
					.getProperty("store").getValue() : null);
			String auditLevel = (navajostore.getProperty("auditlevel") != null ? navajostore
					.getProperty("auditlevel").getValue() : Level.WARNING
					.getName());
			Dictionary<String, Object> d = new Hashtable<String, Object>();
			d.put("type", store);
			d.put("name", "navajostore");
			d.put("level", auditLevel);
			injectConfiguration("navajo.server.store", d);
		}

		boolean enableStatisticsRunner = (body
				.getProperty("parameters/enable_statistics") == null || body
				.getProperty("parameters/enable_statistics").getValue()
				.equals("true"));

		Dictionary<String, Object> d = new Hashtable<String, Object>();
		d.put("enable", enableStatisticsRunner);
		injectConfiguration("navajo.server.statistics", d);

		Property s = body.getProperty("parameters/async_timeout");
		double asyncTimeout = 3600 * 1000; // default 1 hour.
		if (s != null) {
			asyncTimeout = Float.parseFloat(s.getValue()) * 1000;
		}

		boolean enableAsync = (body.getProperty("parameters/enable_async") == null || body
				.getProperty("parameters/enable_async").getValue()
				.equals("true"));
		d = new Hashtable<String, Object>();
		d.put("enable", enableAsync);
		d.put("asyncTimeout", asyncTimeout);
		injectConfiguration("navajo.server.async", d);

		boolean enableIntegrityWorker = (body
				.getProperty("parameters/enable_integrity") == null || body
				.getProperty("parameters/enable_integrity").getValue()
				.equals("true"));

		d = new Hashtable<String, Object>();
		d.put("enable", enableIntegrityWorker);
		injectConfiguration( "navajo.server.integrity", d);

		boolean enableLockManager = (body
				.getProperty("parameters/enable_locks") == null || body
				.getProperty("parameters/enable_locks").getValue()
				.equals("true"));

		d = new Hashtable<String, Object>();
		d.put("enable", enableLockManager);
		injectConfiguration("navajo.server.lockmanager", d);

		int maxAccessSetSize = (body.getProperty("parameters/max_webservices") == null ? MAX_ACCESS_SET_SIZE
				: Integer.parseInt(body.getProperty(
						"parameters/max_webservices").getValue()));
		data.put("maxAccessSetSize", maxAccessSetSize);

		s = body.getProperty("parameters/compile_scripts");
		boolean compileScripts;
		if (s != null) {
			// System.out.println("s.getValue() = " + s.getValue());
			compileScripts = (s.getValue().equals("true"));
		} else {
			compileScripts = false;
		}
		data.put("compileScripts", compileScripts);

		// Get compilation class.
		// TODO refactor into intelligent discovery
		String compilationLanguage = (body
				.getProperty("parameters/compilation_language") != null ? body
				.getProperty("parameters/compilation_language").getValue()
				: null);
		if(compilationLanguage!=null) {
			data.put("compilationLanguage", compilationLanguage);
		}

		// Get document class implementation.
		String documentClass = (body.getProperty("documentClass") != null ? body
				.getProperty("documentClass").getValue() : null);

		data.put("documentClass", documentClass);

		if (documentClass != null) {
			System.setProperty("com.dexels.navajo.DocumentImplementation",
					documentClass);
			NavajoFactory.resetImplementation();
			NavajoFactory.getInstance();
			NavajoFactory.getInstance().setExpressionEvaluator(
					new DefaultExpressionEvaluator());
		}
		
		addAllProperties(body.getMessage("parameters"),data);
		injectConfiguration("navajo.server.config", data);
	}
/**
 * Dump all parameter properties into the fray:
 * @param message
 * @param data
 */
	private void addAllProperties(Message message,
			Dictionary<String, Object> data) {
		ArrayList<Property> all = message.getAllProperties();
		for (Property property : all) {
			String name = property.getName();
			final Object old = data.get(name);
			final Object newValue = property.getTypedValue();
			if(old!=null) {
				logger.warn("Will not append property: "+ name+" (with value: "+newValue+") to configuration, as it will overwrite the present value of: "+old);
			} else {
				data.put(name, newValue);
			}
		}
	}


	private void injectConfiguration( String pid,
			Dictionary<String, Object> d) throws IOException {
		Configuration c = myConfigurationAdmin.getConfiguration(pid,null);
		registeredConfigurations.add(c);
		c.update(d);
		
	}

	private final static String properDir(String in) {
		String result = in + (in.endsWith("/") ? "" : "/");
		return result;
	}
}
