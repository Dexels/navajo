package com.dexels.navajo.compiler.scala;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.jar.Manifest;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;

public class ScalaCompilerWhiteListConf {

    private ConfigurationAdmin configAdmin;
    private Configuration configuration;
    private RepositoryInstance repository;

    private final static Logger logger = LoggerFactory.getLogger(ScalaCompilerWhiteListConf.class);

    public void deactivate() {
        try {
            if (configuration != null) {
                configuration.delete();
            }
        } catch (IOException e) {
            logger.error("Error: ", e);
        }
    }

    private void injectConfig() throws IOException {
        Configuration c = createOrReuse("navajo.enterprise.compiler.scala");
        Dictionary<String, Object> properties = new Hashtable<String, Object>();

        String importsString = "";
        File manifestFile = new File(repository.getRepositoryFolder(), "META-INF" + File.separator + "MANIFEST.MF");
        if (manifestFile.exists()) {

            InputStream inputStream = null;
            try {

                inputStream = new FileInputStream(manifestFile);
                Manifest mf = new Manifest(inputStream);
                importsString = mf.getMainAttributes().getValue("Import-Package");
                properties.put("whitelist", importsString);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        c.update(properties);

    }

    private Configuration createOrReuse(String pid) throws IOException {
        configuration = configAdmin.getConfiguration(pid);

        if (configuration == null) {
            configuration = configAdmin.createFactoryConfiguration(pid, null);
        }
        return configuration;
    }

    public void setConfigAdmin(ConfigurationAdmin configAdmin) {
        this.configAdmin = configAdmin;
    }

    public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
        this.configAdmin = null;
    }

    public void setRepositoryInstance(RepositoryInstance repo) {
        this.repository = repo;
        try {
            injectConfig();
        } catch (IOException e) {
            logger.error("Exception on injecting ScalaCompiler config: {}", e);
        }

    }

    public void clearRepositoryInstance(RepositoryInstance repo) {
        this.repository = null;
    }

}
