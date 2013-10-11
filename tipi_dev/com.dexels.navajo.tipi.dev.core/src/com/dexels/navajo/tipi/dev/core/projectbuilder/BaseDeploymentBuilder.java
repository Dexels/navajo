package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDeploymentBuilder {
//	protected final VersionResolver myVersionResolver = new VersionResolver();
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseDeploymentBuilder.class);
	
//	public abstract String build(String repository, String developmentRepository, String extensions, Map<String,String> tipiProperties, String deployment, File baseDir, String codebase, List<String> profiles, boolean useVersioning) throws IOException;
	public abstract void buildFromMaven(ResourceBundle settings,List<Dependency> dependencyList, File appFolder,
			List<String> profiles,String resourceBase);

	


	protected Map<String, String> parseArguments(File baseDir, String profile, String deployment) throws IOException {

		File path = null;
		Map<String, String> params = new HashMap<String, String>();
		File basePath = new File(baseDir, "settings/arguments.properties");
		FileInputStream fr = new FileInputStream(basePath);

		PropertyResourceBundle p = new PropertyResourceBundle(fr);
		fr.close();
		Enumeration<String> eb = p.getKeys();
		while (eb.hasMoreElements()) {
			String string = eb.nextElement();
			params.put(string, p.getString(string));
		}

		if (profile != null) {
			path = new File(baseDir, "settings/profiles/" + profile + ".properties");
			if(path.exists()) {
				fr = new FileInputStream(path);
				p = new PropertyResourceBundle(fr);
				fr.close();
				eb = p.getKeys();
				while (eb.hasMoreElements()) {
					String string = eb.nextElement();
					params.put(string, p.getString(string));
				}
			}
		}
		if(deployment==null) {
			logger.info("No deployment supplied, so not postprocessing");
			return params;
		}
		logger.debug("Processing: "+params);
		logger.debug("Ok, now gonna post process the params!");
		Map<String,String> result = new HashMap<String, String>();
		for (Map.Entry<String, String> element : params.entrySet()) {
			if(element.getKey().indexOf("/")==-1) {
				result.put(element.getKey(), element.getValue());
				continue;
			}
			String[] elts = element.getKey().split("/");
			if(elts.length!=2) {
				throw new IllegalArgumentException("Strange key in args: "+element.getKey()+" with value: "+element.getValue());
			}
			if(elts[0].equals(deployment)) {
				logger.debug("Adding: "+elts[1]+" "+ element.getValue());
				result.put(elts[1], element.getValue());
			} else {
				// ignore this setting, it's for another deployment
			}
		}
		logger.debug("Result: "+result);
		return result;
	}
}
