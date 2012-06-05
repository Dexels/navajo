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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrameworkInstance {
	protected Framework framework;
//	private final static String APPSERVERBUNDLEDIR = "bundles/";
//	private final static String EXPLICITBUNDLEDIR = "WEB-INF/explicit/";

	private final static Logger logger = LoggerFactory
			.getLogger(FrameworkInstance.class);
	private final String bundlePath;
	
	public FrameworkInstance(String path) {
		bundlePath = path;
	}
	
	protected Object getBundleContext() {
		if(framework==null) {
			log("Can't retrieve bundleContext: Framework isn't running.",null);
			return null;
		}
		return framework.getBundleContext();
	}
 
	public static void main(String[] args) throws BundleException,
			MalformedURLException, InterruptedException {
		FrameworkInstance fs = new FrameworkInstance("bundle");
		fs.start();
	}

	
//	public Bundle install(String path) throws MalformedURLException {
//		Bundle installedBundle;
//		try {
//			String url = getUrl( path).toString();
//			System.err.println("url: "+url);
//			installedBundle = framework.getBundleContext().installBundle(url);
//			installedBundle.start();
//			return installedBundle;
//		} catch (BundleException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	private Bundle installFromClasspath(String path) throws BundleException {
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		Bundle installedBundle = framework.getBundleContext().installBundle(path,is);
		return installedBundle;
	}

	public void installAndStartFromClasspath(String[] paths) throws BundleException  {
		List<Bundle> installed = new ArrayList<Bundle>();
		for (String path : paths) {
			Bundle b = installFromClasspath(path);
			installed.add(b);
		}
		for (Bundle bundle : installed) {
			bundle.start();
		}
	}
	
	public final void start() {
		try {
			doStart();
//			install("org.apache.felix.configadmin-1.2.8.jar");
//			install("org.apache.felix.fileinstall-3.2.0.jar");
		} catch (Exception e) {
			log("Failed to start framework", e);
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

		Framework felixFramework = getFrameworkFactory().newFramework(createConfig()); //  new Felix(createConfig());
		felixFramework.init();
		felixFramework.start();
		this.framework = felixFramework;
//		log("TRACKERS registered!",null);
//		install("org.apache.felix.configadmin-1.2.8.jar");
//		install("org.apache.felix.fileinstall-3.2.0.jar");
		installAndStartFromClasspath(new String[]{"org.apache.felix.configadmin-1.2.8.jar","org.apache.felix.fileinstall-3.2.0.jar"});

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
	protected  Map createConfig() throws Exception {
		Properties props = new Properties();
//		props.load(getResource("default.properties"));
		props.load(getResource("framework.properties"));

		Map<String, Object> map = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String value = (String) props.get(key);
			map.put(key.toString(), value);
			System.err.println("putting: "+key+" value: "+value);
		}
//		StringBuffer bundlePath = new StringBuffer();
//		String resolvedBundlePath = getFilePath(BUNDLEDIR).toString();
//		bundlePath.append(resolvedBundlePath);
//		if (context == null) {
//			// We have no 'containing' appserver, so we build our own. Append
//			// the appserver bundle path.
//			String localBundlePath = getFilePath(APPSERVERBUNDLEDIR).toString();
//			bundlePath.append(",");
//			bundlePath.append(localBundlePath);
//		}
		log("Bundles at: " + bundlePath, null);
//		System.err.println("Resolved: " + bundlePath.toString());
		map.put("felix.fileinstall.dir",bundlePath);
		map.put("felix.fileinstall.log.level", "2");
		map.put("felix.fileinstall.noInitialDelay", "true");
		map.put("felix.fileinstall.poll", "10000");
		
		return map;
	}

	// felix.fileinstall.dir

//	private URL getUrl(String path) throws MalformedURLException {
//		return getFilePath(path).toURI().toURL();
//	}
//
//	private File getFilePath(String path) {
//		if (this.context == null) {
//			File f = new File(System.getProperty("user.dir"));
//			File res = new File(f, path);
//			// TODO: This stream should be closed, is that going well?
//			return res;
//		}
//		File f = new File(this.context.getRealPath(path));
//		return f;
//	}

	private InputStream getResource(String path) throws IOException {
		return FrameworkInstance.class.getClassLoader().getResourceAsStream(path);
	}

	protected void log(String message, Throwable cause) {
		logger.info(message,cause);
	}

	  private static FrameworkFactory getFrameworkFactory() throws Exception
	    {
	        java.net.URL url = FrameworkInstance.class.getClassLoader().getResource(
	            "META-INF/services/org.osgi.framework.launch.FrameworkFactory");
	        if (url != null)
	        {
	            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	            try
	            {
	                for (String s = br.readLine(); s != null; s = br.readLine())
	                {
	                    s = s.trim();
	                    // Try to load first non-empty, non-commented line.
	                    if ((s.length() > 0) && (s.charAt(0) != '#'))
	                    {
	                        return (FrameworkFactory) Class.forName(s).newInstance();
	                    }
	                }
	            }
	            finally
	            {
	                if (br != null) br.close();
	            }
	        }

	        throw new Exception("Could not find framework factory.");
	    }

}
