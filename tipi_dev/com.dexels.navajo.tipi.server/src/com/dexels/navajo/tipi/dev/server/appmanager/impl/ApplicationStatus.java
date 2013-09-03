package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.ant.AntRun;
import com.dexels.navajo.tipi.dev.ant.LoggingOutputStream;
import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.core.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;

public class ApplicationStatus {

	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationStatus.class);

	private Date builtAt;
	private boolean exists = false;

	private ApplicationManager manager;
	private List<String> profiles;

	private List<ExtensionEntry> extensions;
	private String applicationName;
	private File appFolder;
	private String extensionRepository;
	private String buildType = null;
	private String currentDeploy = null;
	private final List<Dependency> dependencyList = new ArrayList<Dependency>();
	
	private final Map<String, Map<String, String>> deploymentData = new HashMap<String, Map<String, String>>();

	public String getCurrentDeploy() {
		return currentDeploy;
	}

	public String getRealPath() {
		return appFolder.getAbsolutePath();
	}

	public String getBuildType() {
		return buildType;
	}

	public String propertyEntry(String deployment, String profile, String name) {
		Map<String, String> depl = deploymentData.get(deployment);
		if (depl == null) {
			return null;
		}
		String rawValue = depl.get(name);
		return processProfileData(rawValue, profile);
	}

	private String processProfileData(String rawValue, String profile) {
		if (rawValue == null) {
			return null;
		}
		return rawValue.replaceAll("\\[\\[profile\\]\\]", profile);
	}

	private Map<String, Boolean> profileNeedsRebuild = new HashMap<String, Boolean>();

	private PropertyResourceBundle settings;

	public String getRepository() {
		return extensionRepository;
	}

	// public void setRepository(String extensionRepository) {
	// this.extensionRepository = extensionRepository;
	// }

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public ApplicationManager getManager() {
		return manager;
	}

	public void setManager(ApplicationManager manager) {
		this.manager = manager;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public Date getBuiltAt() {
		return builtAt;
	}

	public void setBuiltAt(Date builtAt) {
		this.builtAt = builtAt;
	}

	public List<ExtensionEntry> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<ExtensionEntry> extensions) {
		this.extensions = extensions;
	}

	public String getApplicationName() {
		return applicationName;
	}

	private void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void load(File appDir) throws IOException {
		this.appFolder = appDir;
		File tipiSettings = new File(appDir, "settings/tipi.properties");
		if (!tipiSettings.exists()) {
			setApplicationName(appDir.getName());
			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		settings = new PropertyResourceBundle(fis);
		fis.close();
//		extensionRepository = settings.getString("repository");
//		extensions = new LinkedList<ExtensionEntry>();
//		StringTokenizer st = new StringTokenizer(
//				settings.getString("extensions"), ",");
//		while (st.hasMoreElements()) {
//			String element = (String) st.nextElement();
//			extensions.add(new ExtensionEntry(element));
//
//		}

		setExists(true);
		applicationName = appDir.getName();
		processProfiles(appDir);
		build();
	}

	public void build() throws IOException {
		dependencyList.clear();
		List<String> extraHeaders = new ArrayList<String>();
		extraHeaders.add("Permissions: all-permissions");
		extraHeaders.add("Codebase: *");
		
		String deps = settings.getString("dependencies");
		File unsigned = new File(this.appFolder, "unsigned");

		if(unsigned.exists()) {
			FileUtils.deleteDirectory(unsigned);
		}
		if (!unsigned.exists()) {
			unsigned.mkdirs();
		}
		String[] d = deps.split(",");
		for (String dependency : d) {
			
			logger.info("Dependency: " + dependency);
			Dependency dd = new Dependency(dependency);
			dependencyList.add(dd);
		}
		for (Dependency dd : dependencyList) {
			UnsignJarTask.downloadDepencency(dd, new File(unsigned.getAbsolutePath()),extraHeaders);
		}
		LocalJnlpBuilder jj = new LocalJnlpBuilder();
//		jj.parseArguments(baseDir, profile, deployment)
		List<String> profiles = new ArrayList<String>();
		profiles.add("knvb");
		Map<String,String> tipiProperties = new HashMap<String, String>();
		for (String key : settings.keySet()) {
			tipiProperties.put(key,settings.getString(key));
			
		}
		jj.buildFromMaven(settings,dependencyList,appFolder,profiles,appFolder.toURI().toURL().toString(),"");
		signall();
	}

	private void signall() {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
//			<taskdef name="p200ant" classname="de.matthiasmann.p200ant.P200AntTask"/>

			
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			tasks.put("p200ant", de.matthiasmann.p200ant.P200AntTask.class);
			props.put("storepass", settings.getString("sign_storepass"));
			props.put("alias", settings.getString("sign_alias"));
			props.put("keystore", new File(appFolder,settings.getString("sign_keystore")).getAbsolutePath());
//			props.put("storepass", "sp0rtl1nk");
//			props.put("alias", "server");
//			props.put("keystore", new File(appFolder,"keystore/keystore.ks").getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(ApplicationStatus.class.getClassLoader().getResourceAsStream("ant/localsign.xml"), this.appFolder, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	private void processProfiles(File appDir) {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(appDir, "settings/profiles");

		if (profilesDir.exists()) {
			for (File file : profilesDir.listFiles()) {
				if (file.canRead() && file.isFile()
						&& file.getName().endsWith(".properties")) {
					String profileName = file.getName().substring(0,
							file.getName().length() - ".properties".length());
					boolean b = profileNeedsRebuild(file, profileName, appDir);
					pro.add(profileName);
					profileNeedsRebuild.put(profileName, b);
				}
			}
		}
		if (pro.isEmpty()) {
			String profileName = "Default";
			boolean b = profileNeedsRebuild(null, profileName, appDir);
			pro.add(profileName);
			profileNeedsRebuild.put(profileName, b);
		}
		setProfiles(pro);
	}

	private boolean profileNeedsRebuild(File profileProperties,
			String profileName, File appDir) {
		File jnlpFile = new File(appDir, profileName + ".jnlp");
		if (!jnlpFile.exists()) {
			return false;
		}
		if (profileProperties != null) {
			if (profileProperties.lastModified() > jnlpFile.lastModified()) {
				return true;
			}
		}
		File args = new File(appDir, "settings/arguments.properties");
		if (args.lastModified() > jnlpFile.lastModified()) {
			return true;
		}
		File tipiprops = new File(appDir, "settings/arguments.properties");
		if (tipiprops.lastModified() > jnlpFile.lastModified()) {
			return true;
		}

		return false;
	}

	public boolean isValid() {
		File libDir = new File(appFolder, "lib");
		if (!libDir.exists()) {
			return false;
		}
		if (libDir.list().length == 0) {
			return false;
		}
		return true;
	}

	public Map<String, Boolean> getRebuildMap() {
		return profileNeedsRebuild;
	}
}
