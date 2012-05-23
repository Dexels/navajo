package com.dexels.navajo.tipi.appmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

public class ApplicationStatus {
	//private String name;
	private Date builtAt;
	private boolean exists = false;

	private ApplicationManager manager;
	private List<String> profiles;
	private List<String> deployments;


	private List<ExtensionEntry> extensions;
	private String applicationName;
	private File appFolder;
	private String extensionRepository;
	private boolean localSign;
	private String buildType = null;
	private String liveUrl = null;
	private String currentDeploy = null;
	
	private final Map<String,Map<String,String>> deploymentData = new HashMap<String,Map<String,String>>();
	
	
	public String getCurrentDeploy() {
		return currentDeploy;
	}

	public String getRealPath() {
		return appFolder.getAbsolutePath();
	}
	public String getLiveUrl() {
		return liveUrl;
	}

	public void setLiveUrl(String liveUrl) {
		this.liveUrl = liveUrl;
	}

	public String getBuildType() {
		return buildType;
	}
	
	public String propertyEntry(String deployment, String profile, String name) {
		Map<String,String> depl = deploymentData.get(deployment);
		if(depl==null) {
			// TODO maybe check tipi.properties?
			return null;
		}
		String rawValue = depl.get(name);
		return processProfileData(rawValue,profile);
	}
	

	private String processProfileData(String rawValue, String profile) {
		if(rawValue==null) {
			return null;
		}
		return rawValue.replaceAll("\\[\\[profile\\]\\]", profile);
	}

	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}

	public String getCvsModule() {
		return cvsModule;
	}

	public void setCvsModule(String cvsModule) {
		this.cvsModule = cvsModule;
	}

	public String getCvsRevision() {
		return cvsRevision;
	}

	public void setCvsRevision(String cvsRevision) {
		this.cvsRevision = cvsRevision;
	}

	private String cvsModule;
	private String cvsRevision;
	
	public boolean isLocalSign() {
		return localSign;
	}

	public void setLocalSign(boolean localSign) {
		this.localSign = localSign;
	}

	private Map<String,Boolean> profileNeedsRebuild = new HashMap<String, Boolean>();	
	public String getRepository() {
		return extensionRepository;
	}

//	public void setRepository(String extensionRepository) {
//		this.extensionRepository = extensionRepository;
//	}

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

	public boolean isCVS() {
		File cvsDir = new File(appFolder,"CVS");
		return cvsDir.exists();
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

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

//	public List<String> getApplicationLink() {
//		return getApplicationName()+"/Application.jnlp";
//	}
	
	private void setCurrentDeploy(String deploy) {
		currentDeploy = deploy;
		
	}
	
	public List<String> getDeployments() {
		return deployments;
	}


	public void setDeployments(List<String> deployments) {
		this.deployments = deployments;
	}

	
	public void load(File appDir ) throws IOException {
		this.appFolder = appDir;
		File tipiSettings = new File(appDir,"settings/tipi.properties");
		if(!tipiSettings.exists()) {
			setApplicationName(appDir.getName());
			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		PropertyResourceBundle settings = new PropertyResourceBundle(fis);
		fis.close();
		extensionRepository = settings.getString("repository");
		extensions = new LinkedList<ExtensionEntry>();
		StringTokenizer st = new StringTokenizer(settings.getString("extensions"),",");
	    while (st.hasMoreElements()) {
			String element = (String) st.nextElement();
			extensions.add(new ExtensionEntry(element));
				
			}
	    if(settings.containsKey("keystore")) {
	   	 setLocalSign(true);
	    }
	    if(settings.containsKey("build")) {
	   	 setBuildType(settings.getString("build"));
	    }
	    if(settings.containsKey("liveUrl")) {
	   	 setLiveUrl(settings.getString("liveUrl"));
	    }
	    
	    if(settings.containsKey("deploy")) {
	   	 setCurrentDeploy(settings.getString("deploy"));
	    }
	    
	    setExists(true);
	    applicationName = appDir.getName();
	    processProfiles(appDir);
	    processDeploys(appDir);
	    
	    File cvsDir = new File(appDir,"CVS");
	    if(cvsDir.exists()) {
	   	 File repository = new File(cvsDir,"Repository");
	   	 BufferedReader fr = new BufferedReader(new FileReader(repository));
	   	 setCvsModule(fr.readLine());
	   	 fr.close();
	   	 File tag = new File(cvsDir,"Tag");
	   	 if (tag.exists()) {
		   	 fr = new BufferedReader(new FileReader(tag));
		   	 setCvsRevision(fr.readLine().substring(1));
		   	 fr.close();
			} else {
				 setCvsRevision("HEAD");
			}

	    }
	    
	}
	


//	private long getYoungestFile(File folder) {
//		long youngest = 0;
//		for (File file : folder.listFiles()) {
//			long lastModified = 0;
//			if(file.isDirectory()) {
//				lastModified = getYoungestFile(file);
//			} else {
//				lastModified = file.lastModified();
//
//			}
//			if(lastModified > youngest) {
//				youngest = lastModified;
//			}
//		}
//		return youngest;
//	}

	private void processProfiles(File appDir) {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(appDir,"settings/profiles");
//		if(!profilesDir.exists() || profilesDir.listFiles().length ==0) {
//			pro.add("Default");
//			setProfiles(pro);
	//		return;
//		}
		if(profilesDir.exists()) {
			for (File file : profilesDir.listFiles()) {
				//pro.add(file.getName());
				if(file.canRead() && file.isFile() && file.getName().endsWith(".properties")) {
					String profileName = file.getName().substring(0,file.getName().length()-".properties".length());
//					System.err.println("Profilename: "+profileName);
					boolean b = profileNeedsRebuild(file,profileName, appDir);
					pro.add(profileName);
					profileNeedsRebuild.put (profileName,b);
				}
			}
		}
		if(pro.isEmpty()) {
			String profileName = "Default";
			boolean b = profileNeedsRebuild(null,profileName, appDir);
			pro.add(profileName);
			profileNeedsRebuild.put (profileName,b);
		}
		setProfiles(pro);
	}

	
	private void processDeploys(File appDir) throws IOException {
		List<String> deplo = new LinkedList<String>();
		File deploymentsDir = new File(appDir,"settings/deploy");
//		if(!profilesDir.exists() || profilesDir.listFiles().length ==0) {
//			pro.add("Default");
//			setProfiles(pro);
	//		return;
//		}
		deploymentData.clear();
		if(deploymentsDir.exists()) {
			for (File file : deploymentsDir.listFiles()) {
				//pro.add(file.getName());
				if(file.canRead() && file.isFile() && file.getName().endsWith(".properties")) {
					String deployName = file.getName().substring(0,file.getName().length()-".properties".length());
					deplo.add(deployName);
					addDeployData(deployName,file);
				}
			}
		}
		setDeployments(deplo);
	}
	
	private void addDeployData(String deployName, File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		PropertyResourceBundle settings = new PropertyResourceBundle(fis);
		Enumeration<String> keys = settings.getKeys();
		Map<String,String> element = new HashMap<String, String>();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = settings.getString(key);
			element.put(key, value);
			System.err.println("Adding data: "+deployName+" : "+key+" : "+value);
		}
		deploymentData.put(deployName, element);
	}

	public String getManagerUrl(String deploy) {
		Map<String,String> element = deploymentData.get(deploy);
		if(element==null) {
			return null;
		}
		return element.get("managerUrl");
	}
	
	private boolean profileNeedsRebuild(File profileProperties, String profileName, File appDir) {
		File jnlpFile = new File(appDir, profileName+".jnlp");
		if(!jnlpFile.exists()) {
			return false;
		}
		if(profileProperties!=null) {
			if(profileProperties.lastModified() > jnlpFile.lastModified()) {
				return true;
			}
		}
		File args = new File(appDir,"settings/arguments.properties");
		if(args.lastModified() > jnlpFile.lastModified()) {
			return true;
		}
		File tipiprops = new File(appDir,"settings/arguments.properties");
		if(tipiprops.lastModified() > jnlpFile.lastModified()) {
			return true;
		}

		return false;
	}

	public boolean isValid() {
		File libDir = new File(appFolder,"lib");
		if(!libDir.exists()) {
			return false;
		}
		if(libDir.list().length==0) {
			return false;
		}
		return true;
	}
	
	
	public Map<String,Boolean> getRebuildMap() {
		return profileNeedsRebuild;
	}
	public void writeFile(String path, Writer out) throws IOException {
		File f = new File(manager.getAppsFolder(),getApplicationName());
		File pp = new File(f,path);
		FileReader fr = new FileReader(pp);
		copyResource(out,fr);
		fr.close();
		out.flush();
	}
	
	/**
	 * Same as the stream edition. Does not close streams!
	 * @param out
	 * @param in
	 * @throws IOException
	 */
	private final void copyResource(Writer out, Reader in) throws IOException {
		BufferedReader bin = new BufferedReader(in);
		BufferedWriter bout = new BufferedWriter(out);
		char[] buffer = new char[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}
			if (read <= -1) {
				ready = true;
			}
		}
		bout.flush();
	
	}
}
