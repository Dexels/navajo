package com.dexels.navajo.server;

import com.dexels.navajo.document.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import java.io.*;
import com.dexels.navajo.persistence.*;
import com.dexels.navajo.logger.*;
import java.net.URL;
import java.sql.*;

public class NavajoConfig {

    public String adapterPath;
    public String compiledScriptPath;
    public String hibernatePath;
    public String scriptPath;
    public String dbPath;
    public boolean compileScripts = false;
    protected HashMap properties = new HashMap();
    private String configPath;
    protected NavajoClassLoader classloader;
    protected NavajoClassLoader betaClassloader;
    protected com.dexels.navajo.server.Repository repository;
    protected Navajo configuration;
    protected com.dexels.navajo.mapping.AsyncStore asyncStore;
    protected com.dexels.navajo.server.statistics.StatisticsRunner statisticsRunner;
    public String rootPath;
    private String scriptVersion = "";
    private PersistenceManager persistenceManager;
    private String betaUser;
    private InputStreamReader inputStreamReader = null;
    private String classPath = "";
    private boolean enableAsync = true;
    private boolean hotCompile = true;
    private static boolean useLog4j = false;
    protected NavajoLogger navajoLogger = null;

//    private static NavajoClassLoader loader = null;
//    private static NavajoClassLoader betaLoader = null;

    public NavajoConfig(InputStream in, InputStreamReader inputStreamReader)  throws SystemException {

      this.inputStreamReader = inputStreamReader;
      classPath = System.getProperty("java.class.path");
      loadConfig(in);

    }

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
      //System.err.println("Starting loadconfig... Is inputstream null? "+in==null);
      configuration = NavajoFactory.getInstance().createNavajo(in);
      //System.err.println("Navajo configuration created. Is it null?  "+configuration==null);

      Message body = configuration.getMessage("server-configuration");
      if (body == null)
          throw new SystemException(-1, "Could not read configuration file server.xml");

      try {
        rootPath = properDir(body.getProperty("paths/root").getValue());
        configPath = properDir(rootPath +
                               body.getProperty("paths/configuration").getValue());
        adapterPath = properDir(rootPath +
                                body.getProperty("paths/adapters").getValue());
        scriptPath = properDir(rootPath +
                               body.getProperty("paths/scripts").getValue());
        compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ?
                              properDir(rootPath +
                                        body.getProperty("paths/compiled-scripts").
                                        getValue()) : "");
        this.dbPath = (body.getProperty("paths/navajoDB") != null ?
                              rootPath +
                                        body.getProperty("paths/navajostore").
                                        getValue() : null);

        if (this.dbPath != null) {
          statisticsRunner = com.dexels.navajo.server.statistics.StatisticsRunner.getInstance();
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

        classloader = new NavajoClassLoader(adapterPath, compiledScriptPath);
        betaClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, true);

        String repositoryClass = body.getProperty("repository/class").getValue();
        repository = RepositoryFactory.getRepository(repositoryClass, this);

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
            Dispatcher.matchCN = matchCn.getValue().equals("true");
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

        hotCompile = (body.getProperty("parameters/hot_compile") == null ||
                      body.getProperty("parameters/hot_compile").getValue().
                      equals("true"));

        useLog4j = (body.getProperty("parameters/use_log4j") != null &&
                    body.
                    getProperty("parameters/use_log4j").getValue().equals("true"));

        try {
          betaUser = body.getProperty("special-users/beta").getValue();
        }
        catch (Exception e) {
          //System.out.println("No beta user specified");
        }

        s = body.getProperty("parameters/compile_scripts");
        if (s != null) {
          //System.out.println("s.getValue() = " + s.getValue());
          compileScripts = (s.getValue().equals("true"));
        }
        else {
          compileScripts = false;
      }
      } catch (Throwable t) {
        t.printStackTrace(System.err);
        throw new SystemException(-1, "Error reading server.xml configuration", t);
      }
       //System.out.println("COMPILE SCRIPTS: " + compileScripts);
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

    public final HashMap getProperties() {
        return properties;
    }

    public final String getConfigPath() {
        return configPath;
    }

    public final NavajoClassLoader getBetaClassLoader() {
      return betaClassloader;
    }

    public final NavajoClassLoader getClassloader() {
        return classloader;
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

    public final com.dexels.navajo.mapping.AsyncStore getAsyncStore() {
      return this.asyncStore;
    }

    public final com.dexels.navajo.server.statistics.StatisticsRunner getStatisticsRunner() {
     return this.statisticsRunner;
   }

    public final InputStream getScript(String name) throws IOException {
      return getScript(name,false);
    }

    public final Navajo getConditions(String rpcName) throws IOException {
      InputStream input = inputStreamReader.getResource(getRootPath() + "conditions/" + rpcName + ".val");
      if (input == null)
        return null;
      return NavajoFactory.getInstance().createNavajo(input);
    }

    public final InputStream getScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        //try {
          //input = getNavajoStream(getScriptPath() + name + ".xsl_beta");
          //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl_beta"));
          input = inputStreamReader.getResource(getScriptPath() + name + ".xml_beta");
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
          input = inputStreamReader.getResource(getScriptPath() +  name + ".tml_beta");
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
      if (is == null) {
        Thread.currentThread().dumpStack();
//        throw new Exception(is);
      }
      return NavajoFactory.getInstance().createNavajo(is);
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

        if (classloader != null)
          classloader.clearCache();
        if (betaClassloader != null)
          betaClassloader.clearCache();

        classloader = new NavajoClassLoader(adapterPath, compiledScriptPath);
        betaClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, true);
//        classloader = classloader;

        //System.out.println("Cleared cache");
    }

}
