/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dexels.navajo.osgi.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;
import org.osgi.util.tracker.ServiceTracker;

public class FrameworkInstance {
	protected Framework framework;
	// private final static String APPSERVERBUNDLEDIR = "bundles/";
	// private final static String EXPLICITBUNDLEDIR = "WEB-INF/explicit/";

	private final String bundlePath;

	private ServiceTracker configurationInjectorTracker;
	private ServiceTracker obrTracker;

	private ConfigurationInjectionInterface configurationInjectionService;

	private RepositoryAdmin repositoryAdmin = null;

	public FrameworkInstance(String path) {
		bundlePath = path;
	}

	public FrameworkInstance(URL obrUrl) {
		bundlePath = null;
	}

	protected BundleContext getBundleContext() {
		if (framework == null) {
			log("Can't retrieve bundleContext: Framework isn't running.", null);
			return null;
		}
		return framework.getBundleContext();
	}

	public static void main(String[] args) throws BundleException,
			MalformedURLException, InterruptedException {
		FrameworkInstance fs = new FrameworkInstance("bundle");

		fs.start();
	}

	public Bundle installFromUrl(String url) throws MalformedURLException {
		Bundle installedBundle;
		try {
			System.err.println("url: " + url);
			installedBundle = framework.getBundleContext().installBundle(url);
			installedBundle.start();
			return installedBundle;
		} catch (BundleException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Bundle installFromClasspath(String path) throws BundleException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		Bundle installedBundle = framework.getBundleContext().installBundle(
				path, is);
		return installedBundle;
	}

	public void installAndStartFromClasspath(String[] paths)
			throws BundleException {
		List<Bundle> installed = new ArrayList<Bundle>();
		for (String path : paths) {
			Bundle b = installFromClasspath(path);
			installed.add(b);
		}
		for (Bundle bundle : installed) {
			if (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null) {
				bundle.start();
			}
		}
	}
	
	public void startTipi(String path, String deployment, String profile) throws IOException {
		Dictionary<String,String> properties = new Hashtable<String,String>();
		properties.put("tipi.context", path);
		properties.put("deployment", deployment);
		properties.put("profile", profile);
		
		configurationInjectionService.addConfiguration("com.dexels.navajo.tipi.swing.application", properties);
	}

	public final void start() {
		try {
			doStart();
		} catch (Exception e) {
			log("Failed to start framework", e);
			e.printStackTrace();
		}
	}

	public final void stop() {
		try {
			doStop();
		} catch (Exception e) {
			log("Error stopping framework", e);
		}
	}

	@SuppressWarnings("unchecked")
	protected void doStart() throws Exception {

		Framework felixFramework = getFrameworkFactory().newFramework(
				createConfig()); // new Felix(createConfig());
		felixFramework.init();
		felixFramework.start();
		this.framework = felixFramework;
//		framework.getBundleContext().addBundleListener(new BundleListener() {
//
//			@Override
//			public void bundleChanged(BundleEvent be) {
//				System.err.println("Bundle event");
//			}
//		});
//
//		framework.getBundleContext().addServiceListener(new ServiceListener() {
//
//			@Override
//			public void serviceChanged(ServiceEvent se) {
//				ServiceReference<?> serviceReference = se.getServiceReference();
//
//				System.err.println("Service event: " + se.getType());
//				if (serviceReference != null) {
//					String[] keys = serviceReference.getPropertyKeys();
//					for (String string : keys) {
//						Object value = serviceReference.getProperty(string);
//						System.err.println("Key: " + string + " value: "
//								+ value);
//						if (value instanceof String[]) {
//							String[] val = (String[]) value;
//							for (String element : val) {
//								System.err.println("item: " + element);
//							}
//						}
//					}
//				}
//			}
//		});
//
		configurationInjectorTracker = new ServiceTracker(
				framework.getBundleContext(),
				framework
						.getBundleContext()
						.createFilter(
								"(objectClass=com.dexels.navajo.osgi.runtime.ConfigurationInjectionInterface)"),
				null) {
			@Override
			public Object addingService(ServiceReference reference) {
				return super.addingService(reference);
			}

		};
		configurationInjectorTracker.open();

		obrTracker = new ServiceTracker(framework.getBundleContext(), framework
				.getBundleContext().createFilter(
						"(objectClass=org.osgi.service.obr.RepositoryAdmin)"),
				null) {
			@Override
			public Object addingService(ServiceReference reference) {
				return super.addingService(reference);
			}

		};
		obrTracker.open();

		installAndStartFromClasspath(new String[] {
				"org.apache.felix.scr-1.6.0.jar",
				"org.apache.felix.configadmin-1.2.8.jar",
				"org.apache.felix.fileinstall-3.2.0.jar",
				"org.apache.felix.bundlerepository-1.6.6.jar",
				"com.dexels.navajo.runtime.provisioning_1.0.1.jar",
				"slf4j-api-1.6.4.jar",
				"slf4j-simple-1.6.4.jar"
				});
		;

		configurationInjectionService = null;
		try {
			configurationInjectionService = (ConfigurationInjectionInterface) configurationInjectorTracker
					.waitForService(2000);
			repositoryAdmin = (RepositoryAdmin) obrTracker.waitForService(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (configurationInjectionService != null) {
			System.err.println("Admin found");
		} else {
			System.err.println("nope");
		}
		if (repositoryAdmin != null) {
			System.err.println("Repo found");
		} else {
			System.err.println("No repo found");
		}
		repositoryAdmin
				.addRepository(new URL(
						"https://source.dexels.com/nexus/content/shadows/navajo_snapshot_obr/.meta/obr.xml"));
		repositoryAdmin
				.addRepository(new URL(
						"https://source.dexels.com/nexus/content/shadows/thirdparty_obr/.meta/obr.xml"));

		String deps = "(&(symbolicname=com.dexels.navajo.api)(version>=1.0.2)),(&(symbolicname=com.dexels.navajo.tipi.swing.mig)(version>=1.0.9)),(&(symbolicname=com.dexels.navajo.tipi.swing.application)(version>=1.2.2)),(&(symbolicname=com.dexels.navajo.tipi.swing.geo)(version>=1.0.11)),(&(symbolicname=com.dexels.navajo.tipi.swing.editor)(version>=1.1.3)),(&(symbolicname=com.dexels.navajo.tipi.jabber)(version>=1.0.13)),(&(symbolicname=com.dexels.navajo.tipi.mail)(version>=1.0.25)),(&(symbolicname=com.dexels.navajo.tipi.swing.rich)(version>=1.0.11)),(&(symbolicname=com.dexels.navajo.tipi.swing.substance)(version>=1.1.7))";
		Resolver resolver = repositoryAdmin.resolver();
		installDependency(resolver, deps.split(","), true);
		resolver.deploy(true);

		startTipi("/Users/frank/Documents/workspace42/SportlinkClub", "test", "knvb");
	}

	private boolean installDependency(Resolver resolver, String[] dependencies,
			boolean performDeploy) {
		boolean isok = true;
		for (String dep : dependencies) {
			boolean result = exec(resolver,dep);
			if (!result) {
				isok = false;
			}
		}
		return isok;
	}

	public boolean exec(Resolver resolver, String args) {
		Resource[] resources = repositoryAdmin.discoverResources(args);
		if ((resources != null) && (resources.length > 0)) {
			resolver.add(resources[0]);
			if (resolver.resolve()) {
				for (Resource res : resolver.getRequiredResources()) {
					System.err.println("Deploying dependency: "
							+ res.getPresentationName() + " ("
							+ res.getSymbolicName() + ") " + res.getVersion());
					resolver.add(res);
				}
				return true;

			} else {
				System.err.println("Can not resolve " + resources[0].getId()
						+ " reason: ");
				for (Requirement req : resolver.getUnsatisfiedRequirements()) {
					System.err.println("missing " + req.getName() + " "
							+ req.getFilter());
				}
				return false;
			}
		} else {
			System.err.println("No such resource");
			return false;
		}
	}


	protected void doStop() throws Exception {
		if (this.framework != null) {
			this.framework.stop();
		}
		this.framework.waitForStop(10000);
		log("OSGi framework stopped", null);
		this.framework = null;
	}

	// TODO: add some config properties
	@SuppressWarnings("rawtypes")
	protected Map createConfig() throws Exception {
		Properties props = new Properties();
		// props.load(getResource("default.properties"));
		props.load(getResource("framework.properties"));

		Map<String, Object> map = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String value = (String) props.get(key);
			map.put(key.toString(), value);
			// System.err.println("putting: "+key+" value: "+value);
		}
		// StringBuffer bundlePath = new StringBuffer();
		// String resolvedBundlePath = getFilePath(BUNDLEDIR).toString();
		// bundlePath.append(resolvedBundlePath);
		// if (context == null) {
		// // We have no 'containing' appserver, so we build our own. Append
		// // the appserver bundle path.
		// String localBundlePath = getFilePath(APPSERVERBUNDLEDIR).toString();
		// bundlePath.append(",");
		// bundlePath.append(localBundlePath);
		// }
		log("Bundles at: " + bundlePath, null);
		// System.err.println("Resolved: " + bundlePath.toString());
		map.put("felix.fileinstall.dir", bundlePath);
		map.put("felix.fileinstall.log.level", "2");
		map.put("felix.fileinstall.noInitialDelay", "true");
		map.put("felix.fileinstall.poll", "10000");

		return map;
	}

	// felix.fileinstall.dir

	// private URL getUrl(String path) throws MalformedURLException {
	// return getFilePath(path).toURI().toURL();
	// }
	//
	// private File getFilePath(String path) {
	// if (this.context == null) {
	// File f = new File(System.getProperty("user.dir"));
	// File res = new File(f, path);
	// // TODO: This stream should be closed, is that going well?
	// return res;
	// }
	// File f = new File(this.context.getRealPath(path));
	// return f;
	// }

	private InputStream getResource(String path) throws IOException {
		return FrameworkInstance.class.getClassLoader().getResourceAsStream(
				path);
	}

	protected void log(String message, Throwable cause) {
		System.err.println("Message: " + message);
		if (cause != null) {
			cause.printStackTrace();
		}
	}

	private static FrameworkFactory getFrameworkFactory() throws Exception {
		java.net.URL url = FrameworkInstance.class
				.getClassLoader()
				.getResource(
						"META-INF/services/org.osgi.framework.launch.FrameworkFactory");
		if (url != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			try {
				for (String s = br.readLine(); s != null; s = br.readLine()) {
					s = s.trim();
					// Try to load first non-empty, non-commented line.
					if ((s.length() > 0) && (s.charAt(0) != '#')) {
						return (FrameworkFactory) Class.forName(s)
								.newInstance();
					}
				}
			} finally {
				if (br != null)
					br.close();
			}
		}

		throw new Exception("Could not find framework factory.");
	}

}
