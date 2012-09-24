package com.dexels.navajo.server;

import java.io.File;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.loader.NavajoLegacyClassLoader;
import com.dexels.navajo.lockguard.LockManager;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.persistence.PersistenceManagerFactory;
import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerFactory;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;

/*
 * The default NavajoConfig class.
 * 
 */
public final class NavajoConfig extends FileNavajoConfig implements NavajoConfigInterface {

	private static final int MAX_ACCESS_SET_SIZE = 50;
	
	public String adapterPath;
	public String compiledScriptPath;
	public String hibernatePath;
	public String scriptPath;
	
	private String repositoryClass = "com.dexels.navajo.server.SimpleRepository";
	private String auditLevel;
	private HashMap<String,Object> dbProperties = new HashMap<String,Object>();
	public String store;
	
	private String resourcePath;
	public int dbPort = -1;
	public boolean compileScripts = false;
	protected HashMap<String,String> properties = new HashMap<String,String>();
	private String configPath;
	protected NavajoClassLoader betaClassloader;
	protected NavajoClassSupplier adapterClassloader;
	protected volatile com.dexels.navajo.server.Repository repository = null;
	protected Navajo configuration;
    public int maxAccessSetSize = MAX_ACCESS_SET_SIZE;
    
    private Message body;
    private boolean statisticsRunnerStarted = false;
    
    /**
     * Several supporting threads.
     */
    protected DescriptionProviderInterface myDescriptionProvider = null;
        
    public String rootPath;
    private PersistenceManager persistenceManager;
    private String betaUser;
    private String classPath = "";
    private boolean enableAsync = true;
    private boolean enableIntegrityWorker = true;
    private boolean enableLockManager = true;
    private boolean enableStatisticsRunner = true;
    private float asyncTimeout;
    
    public boolean monitorOn;
    public String monitorUsers = null;
    public String [] monitorUsersList = null;
    public String monitorWebservices = null;
    public String [] monitorWebservicesList = null;
    public int monitorExceedTotaltime = -1;
	private File rootFile;

    private static volatile NavajoConfig instance = null;
	private File jarFolder;
	private String instanceName;
	private String instanceGroup;
	
	private OperatingSystemMXBean myOs = null;

	private String compilationLanguage;

	private File contextRoot;
	
	

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoConfig.class);


    
	/**
	 * Creates a fresh NavajoConfig object.
	 * 
	 * @param inputStreamReader, the reader object to be used to read the config inputstream is
	 * @param ncs, the classloader to be used for the scripts.
	 * @param in, the inpustream containing the xml configuration.
	 * @param externalRootPath, optionally an external rootpath can be supplied (e.g. from a Servlet Context).
	 * 
	 * @throws SystemException
	 */
	public NavajoConfig(NavajoClassSupplier ncs, InputStream in, String externalRootPath, String servletContextRootPath)  throws SystemException {

		classPath = System.getProperty("java.class.path");
		adapterClassloader = ncs;
		// BIG NOTE: instance SHOULD be set at this point since instance needs to be known by classes
		// called during loadConfig()!!
		instance = this;
		loadConfig(in, externalRootPath,servletContextRootPath);
		myOs = ManagementFactory.getOperatingSystemMXBean();
		Version.registerNavajoConfig(this);
	}

	
	private void loadConfig(InputStream in, String externalRootPath, String servletContextPath)  throws SystemException{
    	
   	if(servletContextPath!=null) {
   		contextRoot = new File(servletContextPath);
   	}

    	configuration = NavajoFactory.getInstance().createNavajo(in);
    	
    	body = configuration.getMessage("server-configuration");
    	if (body == null) {
    		throw new SystemException(-1, "Could not read configuration file server.xml");
    	}
    	
    	try {
    		
    		String r = ( body.getProperty("paths/root") != null ? body.getProperty("paths/root").getValue() : null);
    		// in Old Skool situation, passed rootPath is null.
    		if(externalRootPath==null) {
    			System.err.println("Old skool configuration (null rootPath), get path from serverXml: "+r);
    			if ( r == null ) {
    				throw new SystemException(-1, "Server root was not specified, try defining root in server.xml");
    			}
    			rootPath = properDir(r);
    		} else {
    			rootPath = externalRootPath;
    			
    		}
            
    		// Get the instance name.
    		instanceName = ( body.getProperty("instance_name") != null ? body.getProperty("instance_name").getValue() : null );
    		if ( instanceName == null ) {
    			throw new IllegalArgumentException("instance_name in server.xml not found.");
    		}
    		
    		// Get the instance group.
    		instanceGroup = ( body.getProperty("instance_group") != null ? body.getProperty("instance_group").getValue() : null );
    		if ( instanceGroup == null ) {
    			throw new IllegalArgumentException("instance_group in server.xml not found.");
    		}
    		
    		rootFile = new File(rootPath);
    		if (!rootFile.exists()) {
				throw new IllegalArgumentException("Rootpath in server.xml not found.");
			}
    		
    		configPath = properDir(rootPath +
    				body.getProperty("paths/configuration").getValue());
    		adapterPath = properDir(rootPath +
    				body.getProperty("paths/adapters").getValue());
    		scriptPath = properDir(rootPath +
    				body.getProperty("paths/scripts").getValue());
    		
    		// changed to more defensive behaviour
    		Property resourceProperty = body.getProperty("paths/resource");
    		if (resourceProperty!=null) {
        		resourcePath = properDir(rootPath +
        				resourceProperty.getValue());				
			}

    		compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ?
    				properDir(rootPath +
    						body.getProperty("paths/compiled-scripts").
    						getValue()) : "");
    		
    		persistenceManager = PersistenceManagerFactory.getInstance("com.dexels.navajo.persistence.impl.PersistenceManagerImpl", configPath);
    		
    		if(adapterClassloader == null) {
    			if(!navajocore.Version.osgiActive()) {
        			adapterClassloader = new NavajoLegacyClassLoader(adapterPath, compiledScriptPath, getClass().getClassLoader());
        			logger.warn("Setting non-OSGi legacy adapter classloader: " + adapterClassloader);
    			} else {
    				adapterClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, getClass().getClassLoader());
    			}
    		}

    		if(betaClassloader==null) {
    			if(!navajocore.Version.osgiActive()) {
        			betaClassloader = new NavajoLegacyClassLoader(adapterPath, compiledScriptPath, true, getClass().getClassLoader());
    			} else {
    				adapterClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, getClass().getClassLoader());
    			}
    		}
    		
    		// Read monitoring configuration options
    		Property monitoringAgentClass = body.getProperty("monitoring-agent/class");
    		Property monitoringAgentProperties = body.getProperty("monitoring-agent/properties");
    		// Set properties.
    		if  ( monitoringAgentProperties != null ) {
    			String [] properties = monitoringAgentProperties.getValue().split(";");
    			for ( int i = 0; i < properties.length; i++ ) {
    				String [] keyValue = properties[i].split("=");
    				String key = ( keyValue.length > 1) ? keyValue[0] : "";
    				String value = ( keyValue.length > 1) ? keyValue[1] : "";
    				System.setProperty(key, value);
    			}
    		}
    		if ( monitoringAgentClass != null ) {
    			AgentFactory.getInstance(monitoringAgentClass.getValue()).start();
    		}
    		
    		Message descriptionMessage = body.getMessage("description-provider");
    		Property descriptionProviderProperty = body.getProperty("description-provider/class");
			String descriptionProviderClass = null;
			if (descriptionProviderProperty!=null) {
				descriptionProviderClass = descriptionProviderProperty.getValue();
				if (descriptionProviderClass!=null) {
					try {
					Class<? extends DescriptionProviderInterface> cc = (Class<? extends DescriptionProviderInterface>) Class.forName(descriptionProviderClass);
					System.err.println("Descriptionprovider is: " + descriptionProviderClass);
					if (cc!=null) {
//						System.err.println("Setting description provider. config hash: "+hashCode());
						if (myDescriptionProvider==null) {
							myDescriptionProvider = cc.newInstance();
							myDescriptionProvider.setDescriptionConfigMessage(descriptionMessage);
						} else {
							System.err.println("Warning: Resetting description provider.");
						}
					}
					} catch (Throwable e) {
						logger.warn("DescriptionProvider not available (normal in OSGi)");
					}
				}
			} 
					
			if ( body.getProperty("repository/class") != null ) {
				repositoryClass = body.getProperty("repository/class").getValue();
			}
			
    		// Read navajostore parameters.
    		Message navajostore = body.getMessage("navajostore");
    		if (navajostore != null) {
    			String p = (navajostore.getProperty("dbport") != null ? navajostore.getProperty("dbport").getValue() : null);
    			store = (navajostore.getProperty("store") != null ? navajostore.getProperty("store").getValue() : null);
    			if (p != null) {
    				dbPort = Integer.parseInt(p);
    			}
    			auditLevel = ( navajostore.getProperty("auditlevel") != null ? navajostore.getProperty("auditlevel").getValue() : Level.WARNING.getName() );
    			dbProperties.put("auditlevel", auditLevel);
    		}
    		
    		if (dbPort != -1) {
    			dbProperties.put("port", new Integer(dbPort));
    		}
    		
    		enableStatisticsRunner = (body.getProperty("parameters/enable_statistics") == null ||
    				body.getProperty("parameters/enable_statistics").getValue().equals("true"));
    		
	
    		//System.err.println("USing repository = " + repository);
//    		Message maintenance = body.getMessage("maintenance-services");
//    		
//    		if ( maintenance != null ) {
//    			List<Property> propertyList = maintenance.getAllProperties();
//    			for (int i = 0; i < propertyList.size(); i++) {
//    				Property prop = propertyList.get(i);
//    				properties.put(prop.getName(), scriptPath + prop.getValue());
//    			}
//    		}
    		
//    		Message security = body.getMessage("security");
//    		if (security != null) {
//    			Property matchCn = security.getProperty("match_cn");
//    			if (matchCn != null)
//    				DispatcherFactory.getInstance().matchCN = matchCn.getValue().equals("true");
//    		}
    		
    		Property s = body.getProperty("parameters/async_timeout");
    		asyncTimeout = 3600 * 1000; // default 1 hour.
    		if (s != null) {
    			asyncTimeout = Float.parseFloat(s.getValue()) * 1000;
    		}
    		
    		enableAsync = (body.getProperty("parameters/enable_async") == null ||
    				body.getProperty("parameters/enable_async").getValue().
    				equals("true"));
    		
    		enableIntegrityWorker = (body.getProperty("parameters/enable_integrity") == null ||
    				body.getProperty("parameters/enable_integrity").getValue().equals("true"));
    		
    		enableLockManager = (body.getProperty("parameters/enable_locks") == null ||
    				body.getProperty("parameters/enable_locks").getValue().equals("true"));
    		
    		
    		if (body.getProperty("paths/jar_folder") != null) {
				Property fold = body.getProperty("paths/jar_folder");
				if (fold!=null) {
					String ss = fold.getValue();
					if (ss!=null) {
						if (rootFile!=null) {
							jarFolder = new File(rootFile,ss);
						} else {
							jarFolder = new File(ss);
						}
					} else {
						jarFolder = null;
					}
				}
			} else {
				jarFolder = new File(contextRoot,"WEB-INF/lib/");				
			}	
		    					
    		maxAccessSetSize = (body.getProperty("parameters/max_webservices") == null ? MAX_ACCESS_SET_SIZE :
    			                   Integer.parseInt(body.getProperty("parameters/max_webservices").getValue()) );
    		
    		try {
    			betaUser = body.getProperty("special-users/beta").getValue();
    			if ( betaUser == null || betaUser.equals("") ) {
    				betaUser = "_beta";
    			}
    		}
    		catch (Throwable e) {
    			//System.out.println("No beta user specified");
    			betaUser = "_beta";
    		}
    		//System.err.println("Betauser suffix is: " + betaUser);
    		
    		s = body.getProperty("parameters/compile_scripts");
    		if (s != null) {
    			//System.out.println("s.getValue() = " + s.getValue());
    			compileScripts = (s.getValue().equals("true"));
    		}
    		else {
    			compileScripts = false;
    		}

    		// Get compilation class.
    		// TODO refactor into intelligent discovery
    		compilationLanguage = ( body.getProperty("parameters/compilation_language") != null ? body.getProperty("parameters/compilation_language").getValue() : null );

    		
    		// Get document class implementation.
    		String documentClass = ( body.getProperty("documentClass") != null ? 
    				body.getProperty("documentClass").getValue() : null );
    		
    		if ( documentClass != null ) {
    			System.setProperty("com.dexels.navajo.DocumentImplementation", documentClass);
    			NavajoFactory.resetImplementation();
    			NavajoFactory.getInstance();
    			NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
    		} 

    		
    	} catch (Throwable t) {
    		throw new SystemException(-1, "Error reading server.xml configuration", t);
    	}
    	//System.out.println("COMPILE SCRIPTS: " + compileScripts);
    }

    /*
     * Startup the Navajo TaskRunner instance. If no instance exists, it is started.
     */
    public void startTaskRunner() {
    	// Bootstrap task scheduler
    	try {
			TaskRunnerFactory.getInstance();
		} catch (RuntimeException e) {
			System.err.println("No taskrunner found.");
		}  
    }
    
    /*
     * Check whether the integrity module is operational.
     */
    public boolean isIntegrityWorkerEnabled() {
    	return enableIntegrityWorker;
    }
    
    /*
     * Check whether the service lock module is operational.
     */
    public boolean isLockManagerEnabled() {
    	return enableLockManager;
    }
    
    /*
     * Function to enable/disable statistics runner on the fly.
     * 
     * @param b
     */
    public synchronized void setStatisticsRunnerEnabled(boolean b) {
    	
    	if ( getStatisticsRunner() != null ) {
    		getStatisticsRunner().setEnabled(b);
    	}
 
    }
    
    /*
     * Check whether the statistics runner is enabled.
     */
    public boolean isStatisticsRunnerEnabled() {
    	if ( getStatisticsRunner() != null ) {
    		return getStatisticsRunner().isEnabled();
    	} else {
    		return false;
    	}
    }
    
    /*
     * Gets the Integrity Worker instance (if enabled).
     * If it does not exist, the Integrity Worker is started.
     */
    public WorkerInterface getIntegrityWorker() {
    	
    	if ( !enableIntegrityWorker ) {
    		return null;
    	}
    	return WorkerFactory.getInstance();
   
    }
    
    /*
     * Gets the lock manager instance (if enabled).
     * If it does not exist, the lock manager is started.
     */
    public LockManager getLockManager() {    	
    	if ( !enableLockManager ) {
    		return null;
    	}
    	return LockManager.getInstance();
    }
    
    /*
     * Check whether asynchronous services are enabled.
     */
    @Override
    public final boolean isAsyncEnabled() {
      return enableAsync;
    }

    /*
     * Gets the class path to be used for the compiling scripts.
     */
    @Deprecated
    public final String getClassPath() {
      return this.classPath;
    }

    /*
     * Gets the path where the compiled scripts are stored.
     */
    public final String getCompiledScriptPath() {
        return compiledScriptPath;
    }

    /*
     * Gets the path to the user adapter library.
     */
    public final String getAdapterPath() {
        return adapterPath;
    }

    /*
     * Gets the path where the scripts are located.
     */
    public final String getScriptPath() {
        return scriptPath;
    }

    public final String getResourcePath() {
        return resourcePath;
    }
    
//    public final HashMap<String,String> getProperties() {
//        return properties;
//    }


    /*
     * Gets the configuration path to the Navajo Instance.
     */
    public final String getConfigPath() {
        return configPath;
    }

    public final NavajoClassLoader getBetaClassLoader() {
    	return betaClassloader;
    }

    // Added a cast, because I changed the type of classloader to generic class loader, so I can just use the system class loader as well...
    public final NavajoClassSupplier getClassloader() {
    	return adapterClassloader;
    }

    /*
     * Gets the BETA user post fix for scripts.
     */
    public final String getBetaUser() {
    	return betaUser;
    }

    /*
     * Sets the Authentication/Authorization module that will be used by the Navajo Instance.
     */
    public final void setRepository(com.dexels.navajo.server.Repository newRepository) {
        repository = newRepository;
    }

    /*
     * Gets the Authentication/Authorization module that will be used by the Navajo Instance.
     */
    public final com.dexels.navajo.server.Repository getRepository() {

    	if ( repository != null ) {
    		return repository;
    	}

    	synchronized (instance) {
    		if ( repository == null ) {
    			RepositoryFactory r = RepositoryFactoryImpl.getInstance();
    			if(r==null) {
    				// no instance, means no OSGi, so go legacy:
        			repository = RepositoryFactoryImpl.getRepository(repositoryClass, this);
        			return repository;
    			}
    			this.repository = r.getRepository(repositoryClass);
    		} 
    	}

    	return repository;
    }

    /*
     * Returns the instance of the Persistence Manager.
     */
    public final PersistenceManager getPersistenceManager() {
      return persistenceManager;
    }
    
    /*
     * Get the root path for the Navajo Installation.
     */
    public final String getRootPath() {
        return this.rootPath;
    }
    
    /*
     * Gets the asynchronous service store instance.
     */
    public final com.dexels.navajo.mapping.AsyncStore getAsyncStore() {
    	if ( isEnableAsync() ) {
    		return com.dexels.navajo.mapping.AsyncStore.getInstance(asyncTimeout);
    	} else {
    		return null;
    	}
    }
 
    /*
     * Start statistics runner.
     */
    public void startStatisticsRunner() {
    	if ( !statisticsRunnerStarted ) {
    		StatisticsRunnerInterface statisticsRunner = getStatisticsRunner();
    		if ( statisticsRunner != null ) {
    			statisticsRunner.setEnabled(isEnableStatisticsRunner());
    		}
    		statisticsRunnerStarted = true;
    	}
    }
    
    /*
     * Gets the statistics runnner instance.
     */
    @Override
    public final StatisticsRunnerInterface getStatisticsRunner() {
       return StatisticsRunnerFactory.getInstance(null, dbProperties, store);
   }

    @Override
    public File getContextRoot() {
   	 return contextRoot;
    }




    private final String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");
        return result;
    }

    /*
     * Clears all NavajoClassLoaders instances. Both the general classloader as well as each instantiated script
     * classloader.
     */
    @Override
    public final synchronized void doClearCache() {

    	if(!navajocore.Version.osgiActive()) {
    		adapterClassloader = new NavajoLegacyClassLoader(adapterPath, null, getClass().getClassLoader());
    		betaClassloader = new NavajoLegacyClassLoader(adapterPath, null, true, getClass().getClassLoader());
    		GenericHandler.doClearCache();
    	}

    }
    
    /*
     * Clears all script classloaders.
     */
    @Override
    public final synchronized void doClearScriptCache() {
    	GenericHandler.doClearCache();
    }


    
    /**
     *
     * BELOW WILL FOLLOW LOGIC FOR MONITORING WEBSERVICES.
     *
     */

    /*
     * Determine if a value matches any of the regexps in a list.
     *
     * @param value
     * @param regExplist
     * @return
     */
    private final boolean matchesRegexp(String value, String [] regExplist) {
      if (regExplist == null) {
        return true;
      }

      for (int i = 0; i < regExplist.length; i++) {
        if (value.matches(regExplist[i])) {
          return true;
        }
      }
      return false;
    }

    /*
     * Determine if access object needs full access log.
     *
     * @param a the full access log candidate
     * @return whether full access log is required for access object.
     */
    @Override
    public final boolean needsFullAccessLog(Access a) {
      // Check whether compiledscript has debugAll set or whether access object has debug all set.
      if ( a.isDebugAll() || ( a.getCompiledScript() != null && a.getCompiledScript().isDebugAll() ) ) {
    	  return true;
      }
      
      if (!monitorOn) {
        return false;
      }
      if (
           (monitorUsersList == null || matchesRegexp(a.rpcUser, this.monitorUsersList ) )&&
           (monitorWebservicesList == null || matchesRegexp(a.rpcName, monitorWebservicesList) ) &&
           (monitorExceedTotaltime == -1 || a.getTotaltime() >= monitorExceedTotaltime)
          )
      {
        return true;
      }
      return false;
    }

    /**
     * Check if full access log monitor is enabled.
     *
     * @return
     */
    @Override
    public final boolean isMonitorOn() {
      return monitorOn;
    }

    /**
     * Set enabled/disable full access log monitor.
     *
     * @param monitorOn
     */
    @Override
    public final void setMonitorOn(boolean monitorOn) {
      this.monitorOn = monitorOn;
    }

    /*
     * Get r.e. for user monitor filter. If null is returned all users should be logged.
     *
     * @return the current filter
     */
    @Override
    public final String getMonitorUsers() {
      return monitorUsers;
    }

    /*
     * Set r.e. for user monitor filter. Null or empty string means no filter.
     *
     * @param monitorUsers
     */
    @Override
    public final void setMonitorUsers(String monitorUsers) {
      System.err.println("in setMonitorUsers(" + monitorUsers + ")");
      if (monitorUsers == null || monitorUsers.equals("")) {
        this.monitorUsersList = null;
        this.monitorUsers = null;
        return;
      }
      this.monitorUsers = monitorUsers;
      StringTokenizer list = new StringTokenizer(monitorUsers, ",");
      System.err.println("Found " + list.countTokens() + " regexp elements");
      monitorUsersList = new String[list.countTokens()];
      int i = 0;
      while (list.hasMoreTokens()) {
        monitorUsersList[i++] = list.nextToken();
      }
    }

    /*
      * Set r.e. for webservice monitor filter. Null or empty string means no filter.
      *
      * @param monitorWebservices
      */

    @Override
    public final void setMonitorWebservices(String monitorWebservices) {
      System.err.println("in setMonitorWebservices(" + monitorWebservices + ")");
      if (monitorWebservices == null || monitorWebservices.equals("")) {
        this.monitorWebservicesList = null;
        this.monitorWebservices = null;
        return;
      }
      this.monitorWebservices = monitorWebservices;
      StringTokenizer list = new StringTokenizer(monitorWebservices, ",");
      monitorWebservicesList = new String[list.countTokens()];
      System.err.println("Found " + list.countTokens() + " regexp elements");
      int i = 0;
      while (list.hasMoreTokens()) {
        monitorWebservicesList[i++] = list.nextToken();
      }
    }

    /*
     * Get r.e. for webservice monitor filter. If null is returned all users should be logged.
     *
     * @return the current filter
     */
    @Override
    public final String getMonitorWebservices() {
      return monitorWebservices;
    }
    
    /*
     * Get the time in millis over which an access needs to be fully logged.
     *
     * @return
     */
    @Override
    public final int getMonitorExceedTotaltime() {
      return monitorExceedTotaltime;
    }

    /**
     * Set the time in millis over which an access needs to be fully logged.
     *
     * @param monitorExceedTotaltime
     */
    @Override
    public final void setMonitorExceedTotaltime(int monitorExceedTotaltime) {
      this.monitorExceedTotaltime = monitorExceedTotaltime;
    }

	/**
	 * @param classloader The classloader to set.
	 */
    @Override
	public void setClassloader(NavajoClassSupplier classloader) {
		this.adapterClassloader = classloader;
	}
	
    @Override
	public File getJarFolder() {
		return jarFolder;
	}

    @Override
	public String getInstanceName() {
		return instanceName;
	}

    @Override
	public DescriptionProviderInterface getDescriptionProvider() {
//		System.err.println("Getting description provider. Config hash: "+hashCode());
		return myDescriptionProvider;
	}
	
    @Override
	public double getCurrentCPUload() {
		if ( myOs != null ) {
			return ( myOs.getSystemLoadAverage() / myOs.getAvailableProcessors() );
		}
		else return 1.0;
	}

    @Override
	public String getInstanceGroup() {
		return instanceGroup;
	}
	
    @Override
	public float getAsyncTimeout() {
		return asyncTimeout;
	}

	private boolean isEnableAsync() {
		return enableAsync;
	}

    @Override
	public boolean isEnableStatisticsRunner() {
		return enableStatisticsRunner;
	}

    @Override
	public boolean isCompileScripts() {
		return compileScripts;
	}

    @Override
	public int getMaxAccessSetSize() {
		return maxAccessSetSize;
	}

	public void setMaxAccessSetSize(int maxAccessSetSize) {
		this.maxAccessSetSize = maxAccessSetSize;
	}

    @Override
	public Message getMessage(String msg) {
		if ( body != null ) {
			return body.getMessage(msg);
		} else {
			return null;
		}
	}

    @Override
	public String getCompilationLanguage() {
		return compilationLanguage;
	}

}