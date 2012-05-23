package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

public abstract class BaseDeploymentBuilder {
	protected final VersionResolver myVersionResolver = new VersionResolver();

	public abstract String build(String repository, String developmentRepository, String extensions, Map<String,String> tipiProperties, String deployment, File baseDir, String codebase, List<String> profiles, boolean useVersioning) throws IOException;

	


	public Map<String, String> parseArguments(File baseDir, String profile, String deployment) throws IOException {

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
				System.err.println("Appending profile: "+profile);
				fr.close();
				eb = p.getKeys();
				while (eb.hasMoreElements()) {
					String string = eb.nextElement();
					params.put(string, p.getString(string));
				}
			}
		}
		System.err.println("params: " + params);
		if(deployment==null) {
			System.err.println("No deployment supplied, so not postprocessing");
			return params;
		}
		System.err.println("Processing: "+params);
		System.err.println("Ok, now gonna post process the params!");
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
				System.err.println("Adding: "+elts[1]+" "+ element.getValue());
				result.put(elts[1], element.getValue());
			} else {
				// ignore this setting, it's for another deployment
			}
		}
		System.err.println("Result: "+result);
		return result;
	}
}
