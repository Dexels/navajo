package com.dexels.navajo.server;

import com.dexels.navajo.document.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

import java.util.*;

import com.dexels.navajo.server.enterprise.descriptionprovider.DescriptionProviderInterface;
import com.dexels.navajo.server.enterprise.integrity.WorkerFactory;
import com.dexels.navajo.server.enterprise.integrity.WorkerInterface;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerFactory;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerInterface;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.lockguard.LockManager;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.persistence.*;
import com.dexels.navajo.util.AuditLog;
import com.dexels.navajo.logger.*;

public final class NavajoConfig {

	private static final int MAX_ACCESS_SET_SIZE = 50;
	
	public String adapterPath;
	public String compiledScriptPath;
	public String hibernatePath;
	public String scriptPath;
	public String dbPath;
	public String store;
	private String resourcePath;
	public int dbPort = -1;
	public boolean compileScripts = false;
	protected HashMap properties = new HashMap();
	private String configPath;
	protected NavajoClassLoader betaClassloader;
	protected NavajoClassSupplier adapterClassloader;
	protected com.dexels.navajo.server.Repository repository;
	protected Navajo configuration;
    public int maxAccessSetSize = MAX_ACCESS_SET_SIZE;
    
    /**
     * Several supporting threads.
     */
    protected com.dexels.navajo.mapping.AsyncStore asyncStore = null;
    protected StatisticsRunnerInterface statisticsRunner = null;
    //protected TaskRunnerInterface taskRunner = null;
    protected LockManager lockManager = null;
    protected DescriptionProviderInterface myDescriptionProvider = null;
        
    public String rootPath;
    private String scriptVersion = "";
    private PersistenceManager persistenceManager;
    private String betaUser;
    private InputStreamReader inputStreamReader = null;
    private String classPath = "";
    private boolean enableAsync = true;
    private boolean hotCompile = true;
    private boolean enableIntegrityWorker = true;
    private boolean enableLockManager = true;
    private boolean enableStatisticsRunner = true;
    private static boolean useLog4j = false;
    protected NavajoLogger navajoLogger = null;

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
    
	private static final synchronized void resetInstance() {
		instance = null;
	}
	
    public NavajoConfig(InputStreamReader inputStreamReader, NavajoClassSupplier ncs)  throws SystemException {

      this.inputStreamReader = inputStreamReader;
      classPath = System.getProperty("java.class.path");
      adapterClassloader = ncs;
      instance = this;
      myOs = ManagementFactory.getOperatingSystemMXBean();
      //loadConfig(in);
     }
    
	public static NavajoConfig getInstance() {
    	 return instance;
     }
     
//    public NavajoConfig(InputStream in, InputStreamReader inputStreamReader)  throws SystemException {
//
//    	this.inputStreamReader = inputStreamReader;
//    	classPath = System.getProperty("java.class.path");
//    	loadConfig(in);
//
//    }
 
    public final boolean isLogged() {
      return useLog4j;
    }

    public final static NavajoLogger getNavajoLogger(Class c) {
      NavajoLogger nl = null;
      if (useLog4j) {
         nl = NavajoLoggerFactory.getNavajoLogger("com.dexels.navajo.logger.log4j.NavajoLoggerImpl");
      } else {
         nl = NavajoLoggerFactory.getNavajoLogger("com.dexels.navajo.logger.nullimpl.NavajoLoggerImpl");
      }
      nl.setClass(c);
      return nl;
    }

    public void loadConfig(InputStream in)  throws SystemException{
    	loadConfig(in, null);
    }
    
    public void loadConfig(InputStream in, String externalRootPath)  throws SystemException{
    	
    	configuration = NavajoFactory.getInstance().createNavajo(in);
    	
    	Message body = configuration.getMessage("server-configuration");
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
    			System.err.println("New skool configuration (rootPath found), path:"+rootPath);
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
			} else {
				System.err.println("Warning: No resources defined.");
			}

    		compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ?
    				properDir(rootPath +
    						body.getProperty("paths/compiled-scripts").
    						getValue()) : "");
    		// Reading deprecated navajostore definition.
    		this.dbPath = (body.getProperty("paths/navajostore") != null ?
    				rootPath +
    				body.getProperty("paths/navajostore").
    				getValue() : null);
    		if (dbPath != null) {
    			System.err.println("WARNING: Using DEPRECATED navajostore configuration, use message based configuration instead.");
    		}
    		
    		this.hibernatePath = (body.getProperty("paths/hibernate-mappings") != null ?
    				properDir(rootPath +
    						body.getProperty("paths/hibernate-mappings").
    						getValue()) : "");
    		
    		
    		if (body.getProperty("parameters/script_version") != null)
    			scriptVersion = body.getProperty("parameters/script_version").getValue();
    		String persistenceClass = body.getProperty("persistence-manager/class").
    		getValue();
    		persistenceManager = PersistenceManagerFactory.getInstance(persistenceClass, getConfigPath());
    		
    		if(adapterClassloader == null) {
    			adapterClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, getClass().getClassLoader());
    		}
//    		if(classloader==null) {
//    			classloader = new NavajoClassLoader(null, compiledScriptPath, adapterClassloader);
//    		}
    		if(betaClassloader==null) {
    			betaClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, true, getClass().getClassLoader());
    		}
    		
    		// Read monitoring configuration options
    		Message monitoringMessage = body.getMessage("monitoring-agent");
    		Property monitoringAgentClass = body.getProperty("monitoring-agent/class");
    		Property monitoringAgentProperties = body.getProperty("monitoring-agent/properties");
    		// Set properties.
    		if  ( monitoringAgentProperties != null ) {
    			String [] properties = monitoringAgentProperties.getValue().split(";");
    			for ( int i = 0; i < properties.length; i++ ) {
    				String [] keyValue = properties[i].split("=");
    				String key = ( keyValue.length > 1) ? keyValue[0] : "";
    				String value = ( keyValue.length > 1) ? keyValue[1] : "";
    				AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Setting system property, key = " + key + ", value = " + value);
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
					Class cc = Class.forName(descriptionProviderClass);
					System.err.println("Descriptionprovider is: " + descriptionProviderClass);
					if (cc!=null) {
//						System.err.println("Setting description provider. config hash: "+hashCode());
						if (myDescriptionProvider==null) {
							myDescriptionProvider = (DescriptionProviderInterface)cc.newInstance();
							myDescriptionProvider.setDescriptionConfigMessage(descriptionMessage);
						} else {
							System.err.println("Warning: Resetting description provider.");
						}
					}
					} catch (Throwable e) {
						System.err.println("WARNING: DescriptionProvider not available");
					}
				}
			} 
			

    		
    		
    		String repositoryClass = body.getProperty("repository/class").getValue();
    		repository = RepositoryFactory.getRepository(repositoryClass, this);
    		
    		// Read navajostore parameters.
    		Message navajostore = body.getMessage("navajostore");
    		if (navajostore != null) {
    			dbPath = (navajostore.getProperty("dbpath") != null ? rootPath + navajostore.getProperty("dbpath").getValue() : null);
    			String p = (navajostore.getProperty("dbport") != null ? navajostore.getProperty("dbport").getValue() : null);
    			store = (navajostore.getProperty("store") != null ? navajostore.getProperty("store").getValue() : null);
    			if (p != null) {
    				dbPort = Integer.parseInt(p);
    				System.err.println("SETTING DBPORT TO " + dbPort);
    			}
    		}
    		
    		enableStatisticsRunner = (body.getProperty("parameters/enable_statistics") == null ||
    				body.getProperty("parameters/enable_statistics").getValue().equals("true"));
    		

    		HashMap p = new HashMap();
    		if (dbPort != -1) {
    			System.err.println("PUTTING PORT = " + dbPort + " IN MAP");
    			p.put("port", new Integer(dbPort));
    		}
    		
    		statisticsRunner = StatisticsRunnerFactory.getInstance(dbPath, p, store);
    		statisticsRunner.setEnabled(enableStatisticsRunner);
    		
    		//System.err.println("USing repository = " + repository);
    		Message maintenance = body.getMessage("maintenance-services");
    		ArrayList propertyList = maintenance.getAllProperties();
    		for (int i = 0; i < propertyList.size(); i++) {
    			Property prop = (Property) propertyList.get(i);
    			properties.put(prop.getName(), scriptPath + prop.getValue());
    		}
    		
    		Message security = body.getMessage("security");
    		if (security != null) {
    			Property matchCn = security.getProperty("match_cn");
    			if (matchCn != null)
    				Dispatcher.getInstance().matchCN = matchCn.getValue().equals("true");
    		}
    		
    		Property s = body.getProperty("parameters/async_timeout");
    		float asyncTimeout = 3600 * 1000; // default 1 hour.
    		if (s != null) {
    			asyncTimeout = Float.parseFloat(s.getValue()) * 1000;
    			System.out.println("SETTING ASYNC TIMEOUT: " + asyncTimeout);
    		}
    		
    		enableAsync = (body.getProperty("parameters/enable_async") == null ||
    				body.getProperty("parameters/enable_async").getValue().
    				equals("true"));
    		if (enableAsync) {
    			asyncStore = com.dexels.navajo.mapping.AsyncStore.getInstance(asyncTimeout);
    		}
    		
    		enableIntegrityWorker = (body.getProperty("parameters/enable_integrity") == null ||
    				body.getProperty("parameters/enable_integrity").getValue().equals("true"));
    		
    		enableLockManager = (body.getProperty("parameters/enable_locks") == null ||
    				body.getProperty("parameters/enable_locks").getValue().equals("true"));
    		
    		
    		if (body.getProperty("paths/jar_folder") != null) {
				Property fold = body.getProperty("paths/jar_folder");
				if (fold!=null) {
					String ss = fold.getValue();
					System.err.println("Supplied value: "+ss);
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
				System.err.println("No jar path found");
	    		jarFolder = null;				
			}
//    		classPath = (body.getProperty("parameters/classpathdir") == null ? System.getProperty("java.class.path"):
//    				body.getProperty("parameters/classpath").getValue());
//    		
    		
    			
    		hotCompile = (body.getProperty("parameters/hot_compile") == null ||
    				body.getProperty("parameters/hot_compile").getValue().
    				equals("true"));
    		
    		useLog4j = (body.getProperty("parameters/use_log4j") != null &&
    				body.
    				getProperty("parameters/use_log4j").getValue().equals("true"));
    		
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
    		System.err.println("Betauser suffix is: " + betaUser);
    		
    		s = body.getProperty("parameters/compile_scripts");
    		if (s != null) {
    			//System.out.println("s.getValue() = " + s.getValue());
    			compileScripts = (s.getValue().equals("true"));
    		}
    		else {
    			compileScripts = false;
    		}
    		// Get document class implementation.
    		String documentClass = ( body.getProperty("documentClass") != null ? 
    				body.getProperty("documentClass").getValue() : null );
    		
    		if ( documentClass != null ) {
    			System.setProperty("com.dexels.navajo.DocumentImplementation", documentClass);
    			NavajoFactory.resetImplementation();
    			NavajoFactory.getInstance();
    			NavajoFactory.getInstance().setExpressionEvaluator(new DefaultExpressionEvaluator());
    			AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Documentclass is now: " + documentClass);
    		} 

    		
    	} catch (Throwable t) {
    		t.printStackTrace(System.err);
    		throw new SystemException(-1, "Error reading server.xml configuration", t);
    	}
    	//System.out.println("COMPILE SCRIPTS: " + compileScripts);
    }

    public TaskRunnerInterface getTaskRunner() {
    	// Startup task scheduler
    	try {
			return TaskRunnerFactory.getInstance();
		} catch (RuntimeException e) {
			System.err.println("No taskrunner found.");
			return null;
		}  
    }
    
    public boolean isIntegrityWorkerEnabled() {
    	return enableIntegrityWorker;
    }
    
    public boolean isLockManagerEnabled() {
    	return enableLockManager;
    }
    
    /**
     * Function to enable/disable statisticsrunner on the fly.
     * 
     * @param b
     */
    public synchronized void setStatisticsRunnerEnabled(boolean b) {
    	
    	if ( statisticsRunner != null ) {
    		statisticsRunner.setEnabled(b);
    	}
 
    }
    
    public boolean isStatisticsRunnerEnabled() {
    	if ( statisticsRunner != null ) {
    		return statisticsRunner.isEnabled();
    	} else {
    		return enableStatisticsRunner;
    	}
    }
    
    public WorkerInterface getIntegrityWorker() {
    	
    	if ( !enableIntegrityWorker ) {
    		
    		return null;
    	}
    	
    	return WorkerFactory.getInstance();
    	
    }
    
    public LockManager getLockManager() {
    	
    	if ( !enableLockManager ) {
    		return null;
    	}
    	
    	if ( lockManager == null ) {
    		lockManager = LockManager.getInstance(this);
    	}
    	return lockManager;
    }
    
    public final boolean isHotCompileEnabled() {
      return hotCompile;
    }

    public final boolean isAsyncEnabled() {
      return enableAsync;
    }

    public final String getClassPath() {
      return this.classPath;
    }

    public final Navajo getConfiguration() {
        return configuration;
    }

    public final String getScriptVersion() {
      return scriptVersion;
    }

    public final String getCompiledScriptPath() {
        return compiledScriptPath;
    }

    public final String getHibernatePath() {
      return ( this.hibernatePath );
    }


    public final String getAdapterPath() {
        return adapterPath;
    }

    public final String getScriptPath() {
        return scriptPath;
    }

    public final String getResourcePath() {
        return resourcePath;
    }
    
    public final HashMap getProperties() {
        return properties;
    }

    public final String getConfigPath() {
        return configPath;
    }

    public final NavajoClassLoader getBetaClassLoader() {
    	return betaClassloader;
    }

    // Added a cast, because I changed the type of classloader to generic class loader, so I can just use the system class loader as well...
    public final NavajoClassSupplier getClassloader() {
//    	if (classloader instanceof NavajoClassSupplier) {
        	return (NavajoClassSupplier) adapterClassloader;
//		}
  //  	return null;
    }

    public final String getBetaUser() {
    	return betaUser;
    }

    public final void setRepository(com.dexels.navajo.server.Repository newRepository) {
        repository = newRepository;
    }

    public final com.dexels.navajo.server.Repository getRepository() {
        return repository;
    }

    public final PersistenceManager getPersistenceManager() {
      return persistenceManager;
    }
    public final String getRootPath() {
        return this.rootPath;
    }
    
    private final File getRootDirectory() {
    	File f = new File(getRootPath());
    	if (f.exists()) {
			return f;
		}
    	File workingDir = new File(System.getProperty("user.dir"));
    	File rootDir = new File(workingDir,getRootPath());
    	return rootDir;
    }
    
    /**
	 * Gets a resource from the 'navajo-tester' dir. For example authorization/authorization.xml
	 * Its preferrable to the other filthy accessors, like:
	 * new FileInputStream(new File(configPath + "/authorization/InputData.xml") )
	 */
    public final InputStream getResource(String path) {
    	try {
			return new FileInputStream(getResourceFile(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /** Same as the getResource(path), only it returns a file. It would be cleaner not to use this function,
     * to make the navajo-config pure inputstream-based. */
    public final File getResourceFile(String path) {
    	return new File(getRootDirectory(),path);
    }
    	
    public final com.dexels.navajo.mapping.AsyncStore getAsyncStore() {
      return this.asyncStore;
    }

    public final StatisticsRunnerInterface getStatisticsRunner() {
     return this.statisticsRunner;
   }

    public final InputStream getScript(String name) throws IOException {
      return getScript(name,false);
    }

//    public final Navajo getConditions(String rpcName) throws IOException {
//      InputStream input = inputStreamReader.getResource(getRootPath() + "conditions/" + rpcName + ".val");
//      if (input == null)
//        return null;
//      return NavajoFactory.getInstance().createNavajo(input);
//    }

    public final InputStream getScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        //try {
          //input = getNavajoStream(getScriptPath() + name + ".xsl_beta");
          //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl_beta"));
          input = inputStreamReader.getResource(getScriptPath() + name + "_beta.xml");
        //}
        //catch (IOException ex) {
        //  ex.printStackTrace();
        //  return getScript(name,false);
        //}
        return input;
      } else {
        ////System.err.println("\n\nLooking for script: "+name+" resolved to: "+getScriptPath() + name + ".xsl");
        ////System.err.println("Resourceurl would be: "+getClass().getClassLoader().getResource(getScriptPath() + name + ".xsl"));
        //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl"));
        //input = getNavajoStream(getScriptPath() + name + ".xsl");
        input = inputStreamReader.getResource(getScriptPath() + name + ".xml");
        return input;
      }
    }

    public final InputStream getTmlScript(String name) throws IOException {
      return getTmlScript(name,false);
    }

    public final InputStream getTmlScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        //try {
          //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".tml_beta"));
          //input = getNavajoStream(getScriptPath() +  name + ".tml_beta");
          input = inputStreamReader.getResource(getScriptPath() +  name + "_beta.tml");
          if (input == null)
            return getTmlScript(name, false);
        //}
        //catch (FileNotFoundException ex) {
        //  ex.printStackTrace();
         // return getTmlScript(name,false);
        //}
        return input;
      } else {
        //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".tml"));
        //input = getNavajoStream(getScriptPath() + name + ".tml");
        input = inputStreamReader.getResource(getScriptPath() + name + ".tml");
        return input;
      }
    }

    public final InputStream getTemplate(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getScriptPath() + "/" + name + ".tmpl");
      //InputStream input = getNavajoStream(getScriptPath() + name + ".tmpl");
      return input;
    }

    public final InputStream getConfig(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getConfigPath() + "/" + name);
      //InputStream input = getNavajoStream(getScriptPath() + name);
      return input;
    }

    public final void writeConfig(String name, Navajo conf) throws IOException {
      Writer output = new FileWriter(new File(getConfigPath() + name));
      try {
        conf.write(output);
      }
      catch (NavajoException ex) {
        throw new IOException(ex.getMessage());
      }
      output.close();
    }

    public final Navajo readConfig(String name) throws IOException {
    	//System.err.println("inputStreamReader = " + inputStreamReader);
    	//System.err.println("inputStreamReader.getResource(getConfigPath() + name) = " + inputStreamReader.getResource(getConfigPath() + name));
    	InputStream is = inputStreamReader.getResource(getConfigPath() + name);
    	try {
    		if (is == null) {
    			//Thread.dumpStack();
    			return null;
//  			throw new Exception(is);
    		}
    		return NavajoFactory.getInstance().createNavajo(is);
    	} finally {
    		if ( is != null ) {
    			try {
    				is.close();
    			} catch (Exception e) {
    				// NOT INTERESTED.
    			}
    		}
    	}
    	//return NavajoFactory.getInstance().createNavajo(getNavajoStream (getConfigPath() + name));
    }
//    public InputStream getConfigScript() {
//
//    }

    private final String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");

        //System.out.println(result);
        return result;
    }

    public final synchronized void doClearCache() {
    	
        adapterClassloader = new NavajoClassLoader(adapterPath, null, getClass().getClassLoader());
//        classloader = new NavajoClassLoader(null, compiledScriptPath, adapterClassloader);
       betaClassloader = new NavajoClassLoader(adapterPath, null, true, getClass().getClassLoader());
        GenericHandler.doClearCache();
        
    }
    
    public final synchronized void doClearScriptCache() {
       
//        classloader = new NavajoClassLoader(null, compiledScriptPath, adapterClassloader);
//        betaClassloader = new NavajoClassLoader(null, compiledScriptPath, true, adapterClassloader);
        GenericHandler.doClearCache();
        
    }

    public final synchronized void setNoScriptCaching() {
        if (adapterClassloader instanceof NavajoClassLoader) {
            if (adapterClassloader != null)
                ((NavajoClassLoader) adapterClassloader).setNoCaching();
        }
  
    }
    
    /**
     *
     * BELOW WILL FOLLOW LOGIC FOR MONITORING WEBSERVICES.
     *
     */

    /**
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

    /**
     * Determine if access object needs full access log.
     *
     * @param a the full access log candidate
     * @return whether full access log is required for access object.
     */
    public final boolean needsFullAccessLog(Access a) {
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
    public final boolean isMonitorOn() {
      return monitorOn;
    }

    /**
     * Set enabled/disable full access log monitor.
     *
     * @param monitorOn
     */
    public final void setMonitorOn(boolean monitorOn) {
      this.monitorOn = monitorOn;
    }

    /**
     * Get r.e. for user monitor filter. If null is returned all users should be logged.
     *
     * @return the current filter
     */
    public final String getMonitorUsers() {
      return monitorUsers;
    }

    /**
     * Set r.e. for user monitor filter. Null or empty string means no filter.
     *
     * @param monitorUsers
     */
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

    /**
      * Set r.e. for webservice monitor filter. Null or empty string means no filter.
      *
      * @param monitorWebservices
      */

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

    /**
     * Get r.e. for webservice monitor filter. If null is returned all users should be logged.
     *
     * @return the current filter
     */
    public final String getMonitorWebservices() {
      return monitorWebservices;
    }

    /**
     * Get the time in millis over which an access needs to be fully logged.
     *
     * @return
     */
    public final int getMonitorExceedTotaltime() {
      return monitorExceedTotaltime;
    }

    /**
     * Set the time in millis over which an access needs to be fully logged.
     *
     * @param monitorExceedTotaltime
     */
    public final void setMonitorExceedTotaltime(int monitorExceedTotaltime) {
      this.monitorExceedTotaltime = monitorExceedTotaltime;
    }

	/**
	 * @param classloader The classloader to set.
	 */
	public void setClassloader(NavajoClassSupplier classloader) {
		this.adapterClassloader = classloader;
	}
	
	public File getJarFolder() {
		return jarFolder;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public DescriptionProviderInterface getDescriptionProvider() {
//		System.err.println("Getting description provider. Config hash: "+hashCode());
		return myDescriptionProvider;
	}
	
	public double getCurrentCPUload() {
		if ( myOs != null ) {
			try {
			return myOs.getSystemLoadAverage();
			} catch (Throwable t) {
				System.err.println("Upgrade to Java 1.6 for supporting cpu load statistics");
				return -1.0;
			}
		} else {
			return -1.0;
		}
	}

	public String getInstanceGroup() {
		return instanceGroup;
	}
	
}