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
import java.net.URL;

public class NavajoConfig {

    public String adapterPath;
    public String compiledScriptPath;
    public String scriptPath;
    public boolean compileScripts = false;
    protected HashMap properties = new HashMap();
    private String configPath;
    protected NavajoClassLoader classloader;
    protected NavajoClassLoader betaClassloader;
    protected com.dexels.navajo.server.Repository repository;
    protected Navajo configuration;
    protected com.dexels.navajo.mapping.AsyncStore asyncStore;
    public String rootPath;
    private String scriptVersion = "";
    private PersistenceManager persistenceManager;
    private String betaUser;
    private InputStreamReader inputStreamReader = null;


//    private static NavajoClassLoader loader = null;
//    private static NavajoClassLoader betaLoader = null;

    public NavajoConfig(InputStream in, InputStreamReader inputStreamReader)  throws SystemException {
      this.inputStreamReader = inputStreamReader;

      loadConfig(in);
    }



    public void loadConfig(InputStream in)  throws SystemException{
      configuration = NavajoFactory.getInstance().createNavajo(in);
      Message body = configuration.getMessage("server-configuration");
      rootPath = properDir(body.getProperty("paths/root").getValue());
      configPath = properDir(rootPath + body.getProperty("paths/configuration").getValue());
      adapterPath = properDir(rootPath + body.getProperty("paths/adapters").getValue());
      scriptPath = properDir(rootPath + body.getProperty("paths/scripts").getValue());
      compiledScriptPath = (body.getProperty("paths/compiled-scripts") != null ?
                                properDir(rootPath + body.getProperty("paths/compiled-scripts").getValue()) : "");
//      String rootPath = body.getProperty("paths/root").getValue();
//      scriptPath = rootPath + body.getProperty("paths/scripts").getValue();
      if (body.getProperty("parameters/script_version") != null)
          scriptVersion = body.getProperty("parameters/script_version").getValue();
      String persistenceClass = body.getProperty("persistence-manager/class").getValue();
      persistenceManager = PersistenceManagerFactory.getInstance(persistenceClass, getConfigPath());

      classloader = new NavajoClassLoader(adapterPath, compiledScriptPath);
//      setClassLoader(loader);

      betaClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, true);
//      setBetaClassLoader(betaLoader);


      String repositoryClass = body.getProperty("repository/class").getValue();
      repository = RepositoryFactory.getRepository(repositoryClass, this);
      Message maintenance = body.getMessage("maintenance-services");
      ArrayList propertyList = maintenance.getAllProperties();
      for (int i = 0; i < propertyList.size(); i++) {
          Property prop = (Property) propertyList.get(i);
          properties.put(prop.getName(), scriptPath + prop.getValue());
      }

      Property s = body.getProperty("parameters/async_timeout");
      float asyncTimeout = 3600 * 1000; // default 1 hour.
      if (s != null) {
        asyncTimeout = Float.parseFloat(s.getValue()) * 1000;
        System.out.println("SETTING ASYNC TIMEOUT: " + asyncTimeout);
      }
      asyncStore = com.dexels.navajo.mapping.AsyncStore.getInstance(asyncTimeout);
      try {
          betaUser = body.getProperty("special-users/beta").getValue();
      } catch (Exception e) {
          System.out.println("No beta user specified");
      }

      s = body.getProperty("parameters/compile_scripts");
      if (s != null) {
        System.out.println("s.getValue() = " + s.getValue());
        compileScripts = (s.getValue().equals("true"));
      }
      else {
        compileScripts = false;
      }

       System.out.println("COMPILE SCRIPTS: " + compileScripts);
    }

    public Navajo getConfiguration() {
        return configuration;
    }

    public String getScriptVersion() {
      return scriptVersion;
    }

    public String getCompiledScriptPath() {
        return compiledScriptPath;
    }

    public String getAdapterPath() {
        return adapterPath;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public HashMap getProperties() {
        return properties;
    }

    public String getConfigPath() {
        return configPath;
    }

//    public void setConfigPath(String configPath) {
//      this.configPath = configPath;
//    }

//    public void setClassLoader(NavajoClassLoader ncl) {
//      classloader = ncl;
//    }
//
//    public void setBetaClassLoader(NavajoClassLoader ncl) {
//      betaClassloader = ncl;
//    }

    public NavajoClassLoader getBetaClassLoader() {
      return betaClassloader;
    }

    public NavajoClassLoader getClassloader() {
        return classloader;
    }

    public String getBetaUser() {
      return betaUser;
    }

    public void setRepository(com.dexels.navajo.server.Repository newRepository) {
        repository = newRepository;
    }

    public com.dexels.navajo.server.Repository getRepository() {
        return repository;
    }

    public PersistenceManager getPersistenceManager() {
      return persistenceManager;
    }
    public String getRootPath() {
        return this.rootPath;
    }

    public com.dexels.navajo.mapping.AsyncStore getAsyncStore() {
      return this.asyncStore;
    }

    public InputStream getScript(String name) throws IOException {
      return getScript(name,false);
    }

    public Navajo getConditions(String rpcName) throws IOException {
      InputStream input = inputStreamReader.getResource(getRootPath() + "conditions/" + rpcName + ".val");
      if (input == null)
        return null;
      return NavajoFactory.getInstance().createNavajo(input);
    }

    public InputStream getScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        //try {
          //input = getNavajoStream(getScriptPath() + name + ".xsl_beta");
          //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl_beta"));
          input = inputStreamReader.getResource(getScriptPath() + name + ".xsl_beta");
        //}
        //catch (IOException ex) {
        //  ex.printStackTrace();
        //  return getScript(name,false);
        //}
        return input;
      } else {
        //System.err.println("\n\nLooking for script: "+name+" resolved to: "+getScriptPath() + name + ".xsl");
        //System.err.println("Resourceurl would be: "+getClass().getClassLoader().getResource(getScriptPath() + name + ".xsl"));
        //input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl"));
        //input = getNavajoStream(getScriptPath() + name + ".xsl");
        input = inputStreamReader.getResource(getScriptPath() + name + ".xsl");
        return input;
      }
    }

    public InputStream getTmlScript(String name) throws IOException {
      return getTmlScript(name,false);
    }

    public InputStream getTmlScript(String name, boolean useBeta) throws IOException {
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

    public InputStream getTemplate(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getScriptPath() + "/" + name + ".tmpl");
      //InputStream input = getNavajoStream(getScriptPath() + name + ".tmpl");
      return input;
    }

    public InputStream getConfig(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getConfigPath() + "/" + name);
      //InputStream input = getNavajoStream(getScriptPath() + name);
      return input;
    }

    public void writeConfig(String name, Navajo conf) throws IOException {
      Writer output = new FileWriter(new File(getConfigPath() + name));
      try {
        conf.write(output);
      }
      catch (NavajoException ex) {
        throw new IOException(ex.getMessage());
      }
      output.close();
    }

    public Navajo readConfig(String name) throws IOException {
      return NavajoFactory.getInstance().createNavajo(inputStreamReader.getResource(getConfigPath() + "/" + name));
      //return NavajoFactory.getInstance().createNavajo(getNavajoStream (getConfigPath() + name));
    }
//    public InputStream getConfigScript() {
//
//    }

    private String properDir(String in) {
        String result = in + (in.endsWith("/") ? "" : "/");

        System.out.println(result);
        return result;
    }

    public synchronized void doClearCache() {

        if (classloader != null)
          classloader.clearCache();
        if (betaClassloader != null)
          betaClassloader.clearCache();

        classloader = new NavajoClassLoader(adapterPath, compiledScriptPath);
        betaClassloader = new NavajoClassLoader(adapterPath, compiledScriptPath, true);
//        classloader = classloader;

        System.runFinalization();
        System.gc();
        System.out.println("Cleared cache");
    }

}
