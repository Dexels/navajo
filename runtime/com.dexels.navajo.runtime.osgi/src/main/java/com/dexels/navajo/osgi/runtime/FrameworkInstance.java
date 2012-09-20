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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resolver;
import org.osgi.service.obr.Resource;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkInstance {
	protected Framework framework;
	// private final static String APPSERVERBUNDLEDIR = "bundles/";
	// private final static String EXPLICITBUNDLEDIR = "WEB-INF/explicit/";

	private String bundlePath;

	protected void setBundlePath(String bundlePath) {
		this.bundlePath = bundlePath;
	}

	private ServiceTracker configurationInjectorTracker;
	private ServiceTracker obrTracker;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FrameworkInstance.class);
	
	private ConfigurationInjectionInterface configurationInjectionService;


	private RepositoryAdmin repositoryAdmin = null;

	public FrameworkInstance(String path) {
		logger.debug("Initializing OSGi runtime with bundle path: {}",path);
		bundlePath = path;
	}

	public FrameworkInstance() {
		bundlePath = null;
	}

	protected ConfigurationInjectionInterface getConfigurationInjectionService() {
		return configurationInjectionService;
	}

	protected BundleContext getBundleContext() {
		if (framework == null) {
			log("Can't retrieve bundleContext: Framework isn't running.", null);
			return null;
		}
		final BundleContext bundleContext = framework.getBundleContext();
		if(bundleContext==null) {
			log("BAD: null bundleContext",null);
		}
		return bundleContext;
	}

	public static void main(String[] args) throws Exception {
		FrameworkInstance fs = new FrameworkInstance("bundle");
		if(args.length>0) {
			String directive = args[0];
			fs.start(directive);
		} else {
			logger.error("Can not start without a directive.. Please supply one using an argument");
		}
		logger.info("Working dir: "+System.getProperty("user.dir"));
		logger.info("Main thread done.");
	}

	// this seems inefficient, don't know if there is a better way
	private Bundle installIfNeeded(BundleInstall install) throws BundleException {
		Bundle[] bundles = framework.getBundleContext().getBundles();
		logger.info("Looking if bundle: "+install.getSymbolicName()+" already exists. Looking through "+bundles.length+" bundles.");
		for (Bundle bundle : bundles) {
			if(install.getSymbolicName().equals(bundle.getSymbolicName())) {
				logger.info("symbolic name matched");
				if(install.getVersion().equals(bundle.getVersion())) {
					logger.info("Version matched, not reinstalling");
					return bundle;
				} else {
					logger.info("but different version installed: "+bundle.getVersion()+" offered: "+install.getVersion());
					bundle.uninstall();
					logger.info("Uninstalled previous version.");
				}
			}
		}
		logger.info("not found");
		return installFromClasspath(install);
	}
	
	private Bundle installFromClasspath(BundleInstall bundleInstall) throws BundleException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(bundleInstall.getPath());
		Bundle installedBundle = framework.getBundleContext().installBundle(bundleInstall.getPath(), is);
		logger.info("Bundle installed: "+installedBundle.getSymbolicName());
		return installedBundle;
	}

	public void installAndStartFromClasspath(BundleInstall[] install)
			throws BundleException {
		List<Bundle> installed = new ArrayList<Bundle>();
		for (BundleInstall path : install) {
			Bundle b = installIfNeeded(path);
			installed.add(b);
		}
		for (Bundle bundle : installed) {
			if (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null) {
				bundle.start();
				logger.debug("Started bundle: "+bundle.getSymbolicName());
			}
		}
	}
	
	public void startSwingTipi(String path, String deployment, String profile) throws IOException, BundleException {
		configurationInjectionService.removeConfigutation("com.dexels.navajo.tipi.swing.application");
		Bundle b = installFromClasspath(new BundleInstall("com.dexels.navajo.tipi.swing.application-1.2.8.jar", "com.dexels.navajo.tipi.swing.application","1.2.8"));
		b.start(Bundle.START_TRANSIENT);
		Dictionary<String,String> properties = new Hashtable<String,String>();
		properties.put("tipi.context", path);
		properties.put("deployment", deployment);
		properties.put("profile", profile);
		configurationInjectionService.addConfiguration("com.dexels.navajo.tipi.swing.application", properties);
	}

	public final void start(final String directive) {
		try {
			doStart(directive);
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
	protected void doStart(final String directive) throws Exception {
		logger.info("Starting with directive {}",directive);
		Framework felixFramework = getFrameworkFactory().newFramework(createConfig());
		this.framework = felixFramework;
		logger.info("felixFramework created: "+felixFramework);
		felixFramework.init();
		logger.info("init called");
		logger.info("BundleContext: "+framework.getBundleContext());
		felixFramework.start();
		logger.info("start called called");
		

		configurationInjectorTracker = new ServiceTracker(
				framework.getBundleContext(),
				framework.getBundleContext().createFilter("(objectClass=com.dexels.navajo.osgi.runtime.ConfigurationInjectionInterface)"),
				null) {
			@Override 
			public Object addingService(ServiceReference reference) {
				return super.addingService(reference);
			}

		};
		configurationInjectorTracker.open();
		logger.info("configurationInjectortracker opened");
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
		logger.info("obrTracker opened");
		logger.debug("Trackers created and started");
		installAndStartFromClasspath(new BundleInstall[] {
				new BundleInstall("org.apache.felix.scr-1.6.0.jar","org.apache.felix.scr","1.6.0"),
				new BundleInstall("org.apache.felix.configadmin-1.2.8.jar","org.apache.felix.configadmin","1.2.8"),
				new BundleInstall("org.apache.felix.bundlerepository-1.6.6.jar","org.apache.felix.bundlerepository","1.6.6"),
				new BundleInstall("com.dexels.navajo.runtime.provisioning-1.0.3.jar","com.dexels.navajo.runtime.provisioning","1.0.3"),
				new BundleInstall("org.apache.felix.configadmin-1.2.8.jar","org.apache.felix.configadmin","1.2.8"),
			});

		installAndStartFromClasspath(new BundleInstall[]{new BundleInstall("org.apache.felix.fileinstall-3.2.0.jar","org.apache.felix.fileinstall","3.2.0")});
		logger.info("classpathbundles started");
		configurationInjectionService = null;
		try {
			repositoryAdmin = (RepositoryAdmin) obrTracker.waitForService(10000);
			configurationInjectionService = (ConfigurationInjectionInterface) configurationInjectorTracker.waitForService(10000);
//			configurationInjectionService.removeConfigutation("com.dexels.navajo.tipi.swing.application");
		} catch (InterruptedException e) {
			logger.error("Interrupted while waiting for trackers: ",e);
		}
		if(configurationInjectionService==null) {
			logger.warn("Config injection service not found");
		}
		if(directive!=null && directive.length()>0) {
			retrieveAndResolveDependencies(directive);
			injectBootConfiguration();
			startTipi(directive);
		}
//		configurationInjectionService.removeConfigutation("tipi.boot");
}

	private void retrieveAndResolveDependencies(String directive) throws MalformedURLException, IOException, Exception {
		
		String[] dir = directive.split("\\|");
		System.err.println(">> "+dir[0]);
		System.err.println(">>> "+dir[1]);
		File path = new File(dir[0]);
		File settings = new File(path,"settings/tipi.properties");
		Reader fr = new FileReader(settings);
		ResourceBundle rb = new PropertyResourceBundle(fr);
		fr.close();
		String repos = rb.getString("repositories");
		String deps = rb.getString("dependencies");
//		String repositories = "https://source.dexels.com/nexus/content/shadows/thirdparty_obr/.meta/obr.xml,https://source.dexels.com/nexus/content/shadows/navajo_snapshot_obr/.meta/obr.xml";
//		String deps = "(&(symbolicname=org.apache.felix.gogo.runtime)(version>=0.10.0)),(&(symbolicname=org.apache.felix.gogo.shell)(version>=0.10.0)),(&(symbolicname=org.apache.felix.gogo.command)(version>=0.12.0)),(&(symbolicname=com.dexels.navajo.api)(version>=1.0.2)),(&(symbolicname=com.dexels.navajo.tipi.swing.mig)(version>=1.0.9)),(&(symbolicname=com.dexels.navajo.tipi.swing.application)(version>=1.2.3)),(&(symbolicname=com.dexels.navajo.tipi.swing.geo)(version>=1.0.11)),(&(symbolicname=com.dexels.navajo.tipi.swing.editor)(version>=1.1.3)),(&(symbolicname=com.dexels.navajo.tipi.jabber)(version>=1.0.13)),(&(symbolicname=com.dexels.navajo.tipi.mail)(version>=1.0.25)),(&(symbolicname=com.dexels.navajo.tipi.swing.rich)(version>=1.0.11)),(&(symbolicname=com.dexels.navajo.tipi.swing.substance)(version>=1.1.8))";
		checkDependencies(repos,deps);
		
	}

	private void injectBootConfiguration() throws IOException {
		Hashtable<String,String> ht = new Hashtable<String,String>();
		ht.put("tipi.boot", "true");
		configurationInjectionService.addConfiguration("tipi.boot", ht);
	}

	private void startTipi(String tipiDirective) throws IOException, BundleException {
		String[] dir = tipiDirective.split("\\|");
		System.err.println(">> "+dir[0]);
		System.err.println(">>> "+dir[1]);
		startSwingTipi(dir[0], dir[1], dir[2]);
	}
	
	
	

	private void checkDependencies(String repositoryList, String dependencyList) throws IOException, Exception, MalformedURLException {
//		startSwingTipi("/Users/frank/Documents/workspace42/SportlinkClub", "test", "knvb");
		String[] repositories = repositoryList.split(",");
		String[] dependencies = dependencyList.split(",");
		for (String repo : repositories) {
			repositoryAdmin.addRepository(new URL(repo));
		}
		for (String dep : dependencies) {
			resolveAtomic(dep);
		}
		repositoryAdmin.addRepository(new URL("https://source.dexels.com/nexus/content/shadows/thirdparty_obr/.meta/obr.xml"));
		logger.info("done");
	}

	private void resolveAtomic(String deps) {
		Resolver resolver = repositoryAdmin.resolver();
		installDependency(resolver, deps.split(","));
		resolver.deploy(true);
	}

	private boolean installDependency(Resolver resolver, String[] dependencies) {
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
					logger.info("Deploying dependency: "
							+ res.getPresentationName() + " ("
							+ res.getSymbolicName() + ") " + res.getVersion());
					resolver.add(res);
				}
				return true;

			} else {
				logger.info("Can not resolve " + resources[0].getId()
						+ " reason: ");
				for (Requirement req : resolver.getUnsatisfiedRequirements()) {
					logger.info("missing " + req.getName() + " "
							+ req.getFilter());
				}
				return false;
			}
		} else {
			logger.info("No such resource");
			return false;
		}
	}


	protected void doStop() throws Exception {
		if (this.framework != null) {
			this.framework.stop();
		} else {
			return;
		}
		if(this.framework!=null) {
			this.framework.waitForStop(10000);
			log("OSGi framework stopped", null);
			this.framework = null;
		}
	}

	// TODO: add some config properties
	@SuppressWarnings("rawtypes")
	protected Map createConfig() throws Exception {
		Properties props = new Properties();
		props.load(getResource("framework.properties"));

		Map<String, Object> map = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String value = (String) props.get(key);
			map.put(key.toString(), value);
		}
		log("Bundles at: " + bundlePath, null);
		map.put("felix.fileinstall.dir", bundlePath);
		map.put("felix.fileinstall.log.level", "2");
		map.put("felix.fileinstall.noInitialDelay", "true");
		map.put("felix.fileinstall.poll", "15000");

		return map;
	}

	private InputStream getResource(String path) {
		return FrameworkInstance.class.getClassLoader().getResourceAsStream(
				path);
	}

	protected void log(String message, Throwable cause) {
		logger.info("Message: " + message);
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
				br.close();
			}
		}

		throw new Exception("Could not find framework factory.");
	}
	
	private class BundleInstall {
		
		public BundleInstall(String path,String symbolicName, String version) {
			this.path = path;
			this.symbolicName = symbolicName;
			this.version = Version.parseVersion(version);
		}
		
		public String getPath() {
			return path;
		}
		public String getSymbolicName() {
			return symbolicName;
		}
		public Version getVersion() {
			return version;
		}
		private String path;
		private String symbolicName;
		private Version version;
	}

}
