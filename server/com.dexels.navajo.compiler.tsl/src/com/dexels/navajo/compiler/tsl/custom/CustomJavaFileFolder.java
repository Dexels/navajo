package com.dexels.navajo.compiler.tsl.custom;

import java.net.URI;
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
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJavaFileFolder {
	private final Map<Bundle,List<JavaFileObject>> elements = new HashMap<Bundle,List<JavaFileObject>>();
	private String packageName;
	private final Map<String, JavaFileObject> contentMap = new HashMap<String, JavaFileObject>();
	private final Map<String, CustomJavaFileFolder> subFolders = new HashMap<String, CustomJavaFileFolder>();

	private final static Logger logger = LoggerFactory
			.getLogger(CustomJavaFileFolder.class);

	public CustomJavaFileFolder(String packageName) {
		this.packageName = packageName;
	}

	public Collection<JavaFileObject> getEntries() {
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		for (List<JavaFileObject> e  : elements.values()) {
			result.addAll(e);
		}
		return Collections.unmodifiableList(result);
	}

	public String getPackageName() {
		return packageName;
	}

	private List<JavaFileObject> findAll(Bundle bundle) {

		List<JavaFileObject> result  = new ArrayList<JavaFileObject>();
		packageName = packageName.replaceAll("\\.", "/");

		enumerateWiring(packageName, result, bundle);
		return result;
	}

	public JavaFileObject getFile(String localName) {
		return contentMap.get(localName);
	}

	private void enumerateWiring(String packageName,
			List<JavaFileObject> result, Bundle b) {
		String packageNameDot = packageName.replaceAll("/", ".");
		if (b.getSymbolicName().startsWith("navajo.script")) {
			// ignore script bundles
			return;
		}
		BundleWiring bw = b.adapt(BundleWiring.class);
		if (bw == null) {
			return ;
		}
		boolean foundExportedPackage = false;
		List<BundleCapability> sss = bw.getCapabilities("osgi.wiring.package");
		for (BundleCapability bundleWire : sss) {
			String exported = (String) bundleWire.getAttributes().get(
					"osgi.wiring.package");
			if (packageNameDot.equals(exported)) {
				foundExportedPackage = true;
			}
		}

		if (!foundExportedPackage) {
			return;
		}
		Collection<String> cc = bw.listResources(packageName, null,
				BundleWiring.LISTRESOURCES_LOCAL);
		for (String resource : cc) {
			URL u = b.getResource(resource);
			if (u != null) {
				// InputStream openStream = null;
				URI uri = null;
				try {
					uri = u.toURI();
					// try {
					final CustomJavaFileObject customJavaFileObject = new CustomJavaFileObject(
							resource, uri, u, Kind.CLASS);
					result.add(customJavaFileObject);
					contentMap.put(resource, customJavaFileObject);

				} catch (Exception e1) {
					logger.warn("URI failed for URL: " + u + " ignoring.");
				}
			}
		}
		return;
	}

	public void addSubFolder(String name, CustomJavaFileFolder cjf) {
		subFolders.put(name, cjf);
	}

	public Iterable<JavaFileObject> getRecursiveEntries() {
		Collection<JavaFileObject> files = new ArrayList<JavaFileObject>();
		appendFiles(files);
		return files;
	}
	
	private void appendFiles(Collection<JavaFileObject> coll) {
		coll.addAll(getEntries());
		for (Map.Entry<String,CustomJavaFileFolder> e : subFolders.entrySet()) {
			e.getValue().appendFiles(coll);
		}
	}

	public CustomJavaFileFolder getSubFolder(String name) {
		return subFolders.get(name);
	}

	public void linkBundle(Bundle bundle) {
		List<JavaFileObject> bundleElements = findAll(bundle);
		elements.put(bundle,bundleElements);

		
	}

	public void unlinkBundle(Bundle bundle) {
		elements.remove(bundle);
	}

}
