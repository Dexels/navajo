package com.dexels.navajo.compiler.tsl.custom;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJavaFileFolder {
	private final List<JavaFileObject> elements = new ArrayList<JavaFileObject>();
	private final BundleContext context;
	private String packageName;
	private final Map<String,JavaFileObject> contentMap = new HashMap<String, JavaFileObject>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(CustomJavaFileFolder.class);
	
	public CustomJavaFileFolder(BundleContext context, String packageName) throws IOException, URISyntaxException {
		this.context = context;
		this.packageName = packageName;
		elements.addAll(findAll(packageName));
	}

	public Iterable<JavaFileObject> getEntries() {
		return Collections.unmodifiableList(elements);
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	private List<JavaFileObject> findAll(String packageName) throws IOException, URISyntaxException {
		
		List<JavaFileObject> result;
		packageName = packageName.replaceAll("\\.", "/");

			result = new ArrayList<JavaFileObject>();
			Bundle[] b = context.getBundles();
			for (Bundle bundle : b) {
				enumerateWiring(packageName, result, bundle);
			}
			return result;
	}
	
	public JavaFileObject getFile(String localName) {
		return contentMap.get(localName);
	}
	
	private void enumerateWiring(String packageName, List<JavaFileObject> result, Bundle b) throws IOException {
//		List<CustomJavaFileObject> resultList = new ArrayList<CustomJavaFileObject>();
		BundleWiring bw =  b.adapt(BundleWiring.class);
		if(bw==null) {
			logger.warn("Can not retrieve entries for bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" as it doesn't seem to be resolved.");
			return;
		}
		Collection<String> cc = bw.listResources(packageName, null, BundleWiring.LISTRESOURCES_LOCAL);
		for (String resource : cc) {
			URL u = b.getResource(resource);
			if(u!=null) {
//					InputStream openStream = null;
					URI uri = null;
					try {
						uri = u.toURI();
						try {
//							openStream = u.openStream();
							final CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(resource, uri,u,Kind.CLASS);
							result.add(customJavaFileObject);
							contentMap.put(resource, customJavaFileObject);
						} catch (FileNotFoundException e) {
							final CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(resource, uri,(URL)null,Kind.CLASS);
							result.add(customJavaFileObject);
						}
					} catch (Exception e1) {
						logger.warn("URI failed for URL: "+u+" ignoring.");
					}
			}
		}
	}
}
