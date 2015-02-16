package com.dexels.navajo.compiler.tsl.custom;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleWiring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJavaFileFolder {
	private final List<JavaFileObject> elements = new ArrayList<JavaFileObject>();
	private final BundleContext context;
	private String packageName;
	private final Map<String, JavaFileObject> contentMap = new HashMap<String, JavaFileObject>();
	private final Map<String, CustomJavaFileFolder> subFolders = new HashMap<String, CustomJavaFileFolder>();

	private final static Logger logger = LoggerFactory
			.getLogger(CustomJavaFileFolder.class);

	public CustomJavaFileFolder(BundleContext context, String packageName) {
		this.context = context;
		this.packageName = packageName;
		List<Bundle> foundInBundles = new ArrayList<Bundle>();
		elements.addAll(findAll(packageName, foundInBundles));
//		System.err.println("Package: "+packageName+" has been found in: "+foundInBundles);
		if (foundInBundles.size() > 1) {
			logger.warn("Split package detected: " + packageName);
			for (Bundle bundle : foundInBundles) {
				logger.warn("Found in bundle: " + bundle.getSymbolicName()
						+ " version: " + bundle.getVersion() + " location: "
						+ bundle.getLocation() + " modified at: "
						+ new Date(bundle.getLastModified()));
			}
		}
	}

	public Collection<JavaFileObject> getEntries() {
		return Collections.unmodifiableList(elements);
	}

	public String getPackageName() {
		return packageName;
	}

	private List<JavaFileObject> findAll(String packageName,
			List<Bundle> foundInBundles) {

		List<JavaFileObject> result;
		packageName = packageName.replaceAll("\\.", "/");

		result = new ArrayList<JavaFileObject>();
		Bundle[] b = context.getBundles();
		for (Bundle bundle : b) {
			boolean found = enumerateWiring(packageName, result, bundle);
			if (found) {
				foundInBundles.add(bundle);
			}
		}

		return result;
	}

	public JavaFileObject getFile(String localName) {
		return contentMap.get(localName);
	}

	private boolean enumerateWiring(String packageName,
			List<JavaFileObject> result, Bundle b) {
		// List<CustomJavaFileObject> resultList = new
		// ArrayList<CustomJavaFileObject>();
		String packageNameDot = packageName.replaceAll("/", ".");
		if (b.getSymbolicName().startsWith("navajo.script")) {
			// ignore script bundles
			return false;
		}
		BundleWiring bw = b.adapt(BundleWiring.class);
		if (bw == null) {
			// logger.debug("Can not retrieve entries for bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" as it doesn't seem to be resolved.");
			return false;
		}
		boolean foundExportedPackage = false;
		List<BundleCapability> sss = bw.getCapabilities("osgi.wiring.package");
		// System.err.println("WIRES of bundle: "+b.getSymbolicName()+" # of wires: "+sss.size());
		for (BundleCapability bundleWire : sss) {
			// System.err.println("wire: "+bundleWire.getAttributes());
			String exported = (String) bundleWire.getAttributes().get(
					"osgi.wiring.package");
			if (packageNameDot.equals(exported)) {
				// System.err.println(">>>>> FOUND: "+exported);
				foundExportedPackage = true;
			}
			// System.err.println("exp: "+exported+" pack: "+packageNameDot);
		}

		if (!foundExportedPackage) {
			// System.err.println("Package: "+packageNameDot+" is not found in package "+b.getSymbolicName());
			return false;
		}
		// System.err.println("Package: "+packageNameDot+" IS found in package "+b.getSymbolicName());
		Collection<String> cc = bw.listResources(packageName, null,
				BundleWiring.LISTRESOURCES_LOCAL);
		boolean found = false;
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
					found = true;
					// } catch (FileNotFoundException e) {
					// final CustomJavaFileObject customJavaFileObject = new
					// CustomJavaFileObject(resource, uri,(URL)null,Kind.CLASS);
					// result.add(customJavaFileObject);
					// }
				} catch (Exception e1) {
					logger.warn("URI failed for URL: " + u + " ignoring.");
				}
			}
		}
		return found;
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

}
