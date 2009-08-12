package com.dexels.navajo.tipi.appmanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class ApplicationStatus {
	//private String name;
	private Date builtAt;
	private boolean exists = false;

	private ApplicationManager manager;
	private List<String> profiles;
	private List<ExtensionEntry> extensions;
	private String applicationName;
	private File appFolder;
	private String extensionRepository;
	
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
	    setExists(true);
	    applicationName = appDir.getName();
	    processProfiles(appDir);
	    
	}
	
	private long getYoungestFile(File folder) {
		long youngest = 0;
		for (File file : folder.listFiles()) {
			long lastModified = 0;
			if(file.isDirectory()) {
				lastModified = getYoungestFile(file);
			} else {
				lastModified = file.lastModified();

			}
			if(lastModified > youngest) {
				youngest = lastModified;
			}
		}
		return youngest;
	}

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
		if(libDir==null) {
			return false;
		}
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
