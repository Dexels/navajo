package com.dexels.navajo.server;

import com.dexels.navajo.document.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.*;
import com.dexels.navajo.loader.NavajoClassLoader;
import java.io.*;

public class NavajoConfig {

    public String adapterPath;
    public String scriptPath;
    protected HashMap properties;
    public String configPath;
    protected NavajoClassLoader classloader;
    protected com.dexels.navajo.server.Repository repository;
    protected Navajo configuration;
    protected com.dexels.navajo.mapping.AsyncStore asyncStore;
    public String rootPath;
    public String scriptVersion = "";

    public Navajo getConfiguration() {
        return configuration;
    }

    public String getScriptVersion() {
      return scriptVersion;
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

    public NavajoClassLoader getClassloader() {
        return classloader;
    }

    public void setRepository(com.dexels.navajo.server.Repository newRepository) {
        repository = newRepository;
    }

    public com.dexels.navajo.server.Repository getRepository() {
        return repository;
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

    public InputStream getScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        try {
          input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl_beta"));
        }
        catch (FileNotFoundException ex) {
          ex.printStackTrace();
          return getScript(name,false);
        }
        return input;
      } else {
        input = new FileInputStream(new File(getScriptPath() + "/" + name + ".xsl"));
        return input;
      }
    }

    public InputStream getTmlScript(String name) throws IOException {
      return getTmlScript(name,false);
    }

    public InputStream getTmlScript(String name, boolean useBeta) throws IOException {
      InputStream input;
      if (useBeta) {
        try {
          input = new FileInputStream(new File(getScriptPath() + "/" + name + ".tml_beta"));
        }
        catch (FileNotFoundException ex) {
          ex.printStackTrace();
          return getTmlScript(name,false);
        }
        return input;
      } else {
        input = new FileInputStream(new File(getScriptPath() + "/" + name + ".tml"));
        return input;
      }
    }

    public InputStream getTemplate(String name) throws IOException {
      InputStream input = new FileInputStream(new File(getScriptPath() + "/" + name + ".tmpl"));
      return input;
    }

    public InputStream getConfig(String name) throws IOException {
      InputStream input = new FileInputStream(new File(getConfigPath() + "/" + name));
      return input;
    }

    public void writeConfig(String name, Navajo conf) throws IOException {
      Writer output = new FileWriter(new File(getConfigPath() + "/" + name));
      try {
        conf.write(output);
      }
      catch (NavajoException ex) {
        throw new IOException(ex.getMessage());
      }
      output.close();
    }

    public Navajo readConfig(String name) throws IOException {
      return NavajoFactory.getInstance().createNavajo(new FileInputStream(new File(getConfigPath() + "/" + name)));
    }
//    public InputStream getConfigScript() {
//
//    }
}
