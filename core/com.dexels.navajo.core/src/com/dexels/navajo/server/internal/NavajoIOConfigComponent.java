package com.dexels.navajo.server.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.FileInputStreamReader;
import com.dexels.navajo.server.InputStreamReader;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoIOConfigComponent implements NavajoIOConfig {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoIOConfigComponent.class);
	
	private NavajoServerContext context = null;
	private final InputStreamReader inputStreamReader= new FileInputStreamReader();
	private File rootPath = null;

	public void activate() {
		logger.info("NavajoIOConfigComponent activated");
	}
	
	public void setServerContext(NavajoServerContext nsc) {
		this.context = nsc;
		rootPath = new File(context.getInstallationPath());
	}
	
	public void clearServerContext(NavajoServerContext nsc) {
		this.context = null;
	}
	
	
	/*
	 * Copied from NavajoConfig
	 * @see com.dexels.navajo.server.NavajoIOConfig#getScript(java.lang.String)
	 */
	@Override
    public final InputStream getScript(String name) throws IOException {
    	InputStream input;
		logger.debug("Not beta");
		String path = getScriptPath() + name + ".xml";
		input = inputStreamReader.getResource(path);
		if(input==null) {
    		path = getScriptPath() + name + ".tsl";
			input = inputStreamReader.getResource(path);
		}
		if(input==null) {
			logger.debug("No resource found");
			File f = new File(rootPath,"scripts/"+name+".xml");
			if(!f.exists()) {
				f = new File(rootPath,"scripts/"+name+".tsl");
			}
			logger.debug("Looking into contextroot: "+f.getAbsolutePath());
			if(f.exists()) {
				System.err.println("Retrieving script from servlet context: "+path);
				return new FileInputStream(f);
			}
		}
		return input;
    }

    @Override
    public final InputStream getConfig(String name) throws IOException {
      InputStream input = inputStreamReader.getResource(getConfigPath() + "/" + name);
      return input;
    }
	@Override
	public String getConfigPath() {
		return new File(rootPath,"config").getAbsolutePath();
	}

	@Override
	public String getClassPath() {
		return null;
	}

	@Override
	public String getAdapterPath() {
		return new File(rootPath,"config").getAbsolutePath();
	}

	@Override
	@Deprecated
	public File getJarFolder() {
		return null;
	}

	@Override
	public String getScriptPath() {
		return new File(rootPath,"scripts").getAbsolutePath();
	}

	@Override
	public String getCompiledScriptPath() {
		return new File(rootPath,"classes").getAbsolutePath();
	}

	
	@Override
	public String getRootPath() {
		return context.getInstallationPath();
	}



	/**
	 * Copied from NavajoConfig Don't forget to close the stream
	 */
    public InputStream getResourceBundle(String name) throws IOException {
   	File adPath = new File(getAdapterPath());
		File bundleFile = new File(adPath,name+".properties");
		if(!bundleFile.exists()) {
			System.err.println("Bundle: "+name+" not found. Resolved to non-existing file: "+bundleFile.getAbsolutePath());
			return null;
		}
		FileInputStream fix = new FileInputStream(bundleFile);
		return fix;

    }


}
