package com.dexels.navajo.server.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.loader.NavajoBasicClassLoader;
import com.dexels.navajo.mapping.AsyncStore;
import com.dexels.navajo.script.api.NavajoClassSupplier;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.sharedstore.SharedStoreInterface;

public class NavajoConfigComponent implements NavajoConfigInterface {

	private NavajoIOConfig navajoIOConfig = null;
	protected NavajoClassSupplier adapterClassloader;
	private Map<String, Object> properties;
	private BundleContext bundleContext;
	private final Map<Class<?>,ServiceReference<?>> serviceReferences = new HashMap<>();
	private final Map<String, DescriptionProviderInterface> desciptionProviders = new HashMap<>();
	private ConfigurationAdmin myConfigurationAdmin;
	private AsyncStore asyncStore;
	private WorkerInterface integrityWorker;
	private SharedStoreInterface sharedStore;
	private TribeManagerInterface tribeManager;
	private static final Logger logger = LoggerFactory
			.getLogger(NavajoConfigComponent.class);
	
	public NavajoConfigComponent() {
	}
	
	
	public void setSharedStore(SharedStoreInterface sharedStore) {
		this.sharedStore = sharedStore;
	}
	
	public void clearSharedStore(SharedStoreInterface sharedShore) {
		this.sharedStore = null;
	}
	
	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	/**
	 * @param config the navajoioconfig to clear
	 */
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
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
	
	public void activate(Map<String,Object> props, BundleContext bundleContext) throws InstantiationException {
			this.properties = props;
			this.bundleContext = bundleContext;
	}

	public void deactivate() {
		logger.info(">>>>>> deactivating navajo config");
	}
	
	@Override
	public File getContextRoot() {
		return navajoIOConfig.getContextRoot();
	}

	@Override
	public InputStream getScript(String name) throws IOException {
		return navajoIOConfig.getScript(name);
	}

	@Override
	public InputStream getConfig(String name) throws IOException {
		return navajoIOConfig.getConfig(name);
	}

	@Override
	public InputStream getResourceBundle(String name) throws IOException {
		return navajoIOConfig.getResourceBundle(name);
	}

	@Override
	public Writer getOutputWriter(String outputPath, String scriptPackage,
			String scriptName, String extension) throws IOException {
		return navajoIOConfig.getOutputWriter(outputPath, scriptPackage, scriptName, extension);
	}

	@Override
	public Reader getOutputReader(String outputPath, String scriptPackage,
			String scriptName, String extension) throws IOException {
		return navajoIOConfig.getOutputReader(outputPath, scriptPackage, scriptName, extension);
	}

	@Override
	public String getConfigPath() {
		return navajoIOConfig.getConfigPath();
	}

	@Override
	public String getRootPath() {
		return navajoIOConfig.getRootPath();
	}

	@Override
	public String getScriptPath() {
		return navajoIOConfig.getScriptPath();
	}

	@Override
	public String getCompiledScriptPath() {
		return navajoIOConfig.getCompiledScriptPath();
	}

	@Override
	public String getAdapterPath() {
		return navajoIOConfig.getAdapterPath();
	}

	@Override
	public Navajo readConfig(String s) throws IOException {
		return navajoIOConfig.readConfig(s);
	}

	@Override
	public void writeConfig(String name, Navajo conf) throws IOException {
		navajoIOConfig.writeConfig(name, conf);
	}

	@Override
	public void writeOutput(String scriptName, String suffix, InputStream is)
			throws IOException {
		navajoIOConfig.writeOutput(scriptName, suffix, is);
	}

	@Override
	public String getInstanceName() {
		return (String) properties.get("instanceName");
	}

	@Override
	public String getInstanceGroup() {
		return (String) properties.get("instanceGroup");
	}

	private Object getService(Class<?> clz) {
		ServiceReference<?> cached = serviceReferences.get(clz);
		if(cached!=null) {
			logger.debug("using cached service reference");
			return bundleContext.getService(cached);
		}
		ServiceReference<?> ref = bundleContext.getServiceReference(clz);
		this.serviceReferences.put(clz,ref);
		try {
			return bundleContext.getService(ref);
		} catch (Throwable t) {
			logger.warn("Could not find service: " + clz);
			return null;
		}
	}
	
	

	@Override
	public StatisticsRunnerInterface getStatisticsRunner() {
		return (StatisticsRunnerInterface) getService(StatisticsRunnerInterface.class);
	}

	@Override
	public ClassLoader getClassloader() {
		if ( WebserviceListenerFactory.getInstance() != null ) {
			return new NavajoBasicClassLoader(WebserviceListenerFactory.getInstance().getClass().getClassLoader());
		}
		return getClass().getClassLoader();
	}

	public void setAsyncStore(AsyncStore as) {
		this.asyncStore = as;
	}
	
	public void clearAsyncStore(AsyncStore as) {
		this.asyncStore = null;
	}
	
	@Override
	public AsyncStore getAsyncStore() {
		return this.asyncStore;
	}

	public void addDescriptionProvider(DescriptionProviderInterface dpi) {
		desciptionProviders.put(dpi.getClass().getName(), dpi);
	}

	public void removeDescriptionProvider(DescriptionProviderInterface dpi) {
		desciptionProviders.remove(dpi.getClass().getName());
	}

	public void setTribeManager(TribeManagerInterface tmi) {
		tribeManager = tmi;
	}
	
	public void clearTribeManager(TribeManagerInterface tmi) {
		tribeManager = null;
	}
	
	public void clearIntegrityWorker(WorkerInterface dpi) {
		this.integrityWorker = null;
	}

	public void setIntegrityWorker(WorkerInterface dpi) {
		this.integrityWorker = dpi;
	}
	
	@Override
	public DescriptionProviderInterface getDescriptionProvider() {
		return desciptionProviders.get(properties.get("descriptionProviderClass"));
	}

	@Override
	public WorkerInterface getIntegrityWorker() {
		return integrityWorker;
	}

	@Override
	public double getCurrentCPUload() {
		return -1;
	}

	@Override
	public void setStatisticsRunnerEnabled(boolean b) {
		StatisticsRunnerInterface sm = (StatisticsRunnerInterface)getService(StatisticsRunnerInterface.class);
		if(sm!=null) {
			sm.setEnabled( b);
			return;
		}
		logger.warn("Warning: setStatisticsRunnerEnabled failed, no ServiceMonitor found.");
	}

	@Override
	public String getResourcePath() {
		return (String) properties.get("resource");
	}

	@Override
	public int getMaxAccessSetSize() {
		Integer f =(Integer) properties.get("maxAccessSetSize");
		if(f!=null) {
			return f;
		}
		return MAX_ACCESS_SET_SIZE;
	}

	@Override
	public float getAsyncTimeout() {
		Float f =(Float) getExternalConfigurationValue("navajo.server.async","asyncTimeout");
		if(f!=null) {
			return f;
		}
		return -1;
	}

	private Object getExternalConfigurationValue(String pid, String key) {
		try {
			Configuration c = myConfigurationAdmin.getConfiguration(pid);
			if(c!=null) {
				Dictionary<String,Object> props = c.getProperties();
				if(props!=null) {
					return props.get(key);
				}
				return null;
			}
			return null;
		} catch (IOException e) {
			logger.error("Error retrieving external configuration: ");
			return null;
		}
	}

	@Override
	public void doClearCache() {
		logger.info("Ignoring doClearCache");
	}

	@Override
	public void doClearScriptCache() {
		logger.info("Ignoring doClearScriptCache");
	}


	@Override
	public boolean isLockManagerEnabled() {
		Boolean b =(Boolean) getExternalConfigurationValue("navajo.server.lockmanager","enabled");
		if(b!=null) {
			return b;
		}
		return false;
	}

	@Override
	public boolean isEnableStatisticsRunner() {
		Boolean b =(Boolean) getExternalConfigurationValue("navajo.server.statistics","enabled");
		if(b!=null) {
			return b;
		}
		return false;
	}

	/**
	 * This one asks the statisticsrunner
	 * @return
	 */
	@Override
	public boolean isStatisticsRunnerEnabled() {
		StatisticsRunnerInterface s = getStatisticsRunner();
		if(s==null) {
			return false;
		}
		return s.isEnabled();
	}

	@Override
	public boolean isCompileScripts() {
		final Boolean result = (Boolean)properties.get("compileScripts");
		if(result==null) {
			logger.error("isCompiledScript NULL in configuration, this is wrong on many levels. Assuming true");
			return true;
		}
		return result;
	}

	@Override
	public Object getParameter(String string) {
		return properties.get(string);
	}

	@Override
	public SharedStoreInterface getSharedStore() {
		return sharedStore;
	}

	@Override
	public boolean hasTenantScriptFile(String rpcName, String tenant,String scriptPath) {
		return navajoIOConfig.hasTenantScriptFile(rpcName, tenant,scriptPath);
	}

	@Override
	public InputStream getScript(String name, String tenant,String extension) throws IOException {
		return navajoIOConfig.getScript(name, tenant,extension);
	}

    @Override 
    public boolean useLegacyDateMode() {
        Object value = getParameter("isLegacyMode"); 
        if (value != null) {
            if(!(value instanceof Boolean)) {
                logger.error("Error: isLegacy mode is set to: "+value+" this should be boolean type, this will fail");
                // allow failure
            }
            return (Boolean)value;
        }
        return true;
    }


    @Override
    public String getDeployment() {
        return navajoIOConfig.getDeployment();
    }

}
