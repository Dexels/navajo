package com.dexels.navajo.server;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.loader.NavajoClassLoader;


public class NavajoConfig {

    protected String adapterPath;
    protected String scriptPath;
    protected HashMap properties;
    protected String configPath;
    protected NavajoClassLoader classloader;
    protected com.dexels.navajo.server.Repository repository;
    protected Navajo configuration;
    protected String rootPath;
    protected String scriptVersion = "";

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

}
