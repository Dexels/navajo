package com.dexels.navajo.tipi.projectbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

public abstract class BaseDeploymentBuilder {
	protected final VersionResolver myVersionResolver = new VersionResolver();

	public abstract void build(String repository, String developmentRepository, String extensions, File baseDir, String codebase, String fileName, String profile) throws IOException;

	
	public Map<String, String> parseParams(File baseDir) throws IOException {
		File path = new File(baseDir, "settings/tipi.properties");
		Map<String, String> params = new HashMap<String, String>();
		FileInputStream fr = new FileInputStream(path);

		PropertyResourceBundle p = new PropertyResourceBundle(fr);
		fr.close();
		Enumeration<String> eb = p.getKeys();
		while (eb.hasMoreElements()) {
			String string = (String) eb.nextElement();
			params.put(string, p.getString(string));
		}
		System.err.println("params: " + params);
		return params;
	}

	public Map<String, String> parseArguments(File baseDir, String profile) throws IOException {

		File path = null;
		Map<String, String> params = new HashMap<String, String>();
		File basePath = new File(baseDir, "settings/arguments.properties");
		FileInputStream fr = new FileInputStream(basePath);

		PropertyResourceBundle p = new PropertyResourceBundle(fr);
		fr.close();
		Enumeration<String> eb = p.getKeys();
		while (eb.hasMoreElements()) {
			String string = (String) eb.nextElement();
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
					String string = (String) eb.nextElement();
					params.put(string, p.getString(string));
				}
			}
		}
		System.err.println("params: " + params);
		return params;
	}
}
