package com.dexels.navajo.jsp.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

public class NavajoServerContext {
	private PageContext pageContext;
	private File currentFolder;
	
	public File getCurrentFolder() {
		return currentFolder;
	}

	public void setCurrentFolder(File currentFolder) {
		this.currentFolder = currentFolder;
	}
	
	public boolean isValidInstallation() {
		return false;
	}
	
	public String getContextName() {
		return getPageContext().getServletContext().getContextPath();
	}

	public String getContextPath() {
		return getPageContext().getServletConfig().getServletContext().getRealPath(".");

	}
	public String getServerInfo() {
		return "Aap: "+getPageContext().getServletConfig().getServletContext().getServerInfo();

	}


	public String getPath() {
		try {
			String rootPath = getScriptRoot().getCanonicalPath();
			String currentPath = getCurrentFolder().getCanonicalPath();
			if(rootPath.equals(currentPath)) {
				return "";
			}
			String relativePath = currentPath.substring(rootPath.length()+1, currentPath.length())+"/";
			return relativePath;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setPath(String path) {
		File newCurrent = new File(getCurrentFolder(),path);
		try {
			// Check if we aren't navigating outside the script folder
			if(newCurrent.getCanonicalPath().startsWith(getScriptRoot().getCanonicalPath())) {
				setCurrentFolder(newCurrent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPageContext(PageContext pageContext) throws IOException {
		
		if(this.pageContext==null) {
			this.pageContext = pageContext;
			setCurrentFolder(getScriptRoot());
		} else {
			this.pageContext = pageContext;
		}
	}

	public PageContext getPageContext() {
		return pageContext;
	}
	
	public File getNavajoRoot() throws IOException {
		String installedContext = InstallerContext.getNavajoRoot(getPageContext().getServletContext().getContextPath().substring(1));
		System.err.println("Retrieved context: "+installedContext);
		if(installedContext==null) {
			String real = pageContext.getServletContext().getRealPath("");
			return new File(real);			
		} else {
			return new File(installedContext);
		}
	}

	public File getScriptRoot() throws IOException {
		return new File(getNavajoRoot(),"scripts/");
	}

	public List<String> getScripts() {
		List<String> all = new ArrayList<String>();
		File[] filelist = getCurrentFolder().listFiles();
		for (File file : filelist) {
			if(file.isFile() && file.getName().endsWith("xml")) {
				all.add(file.getName().substring(0,file.getName().length()-4));
			}
		}
		return all;
	}
	
	public List<File> getFolders() throws IOException {
		List<File> all = new ArrayList<File>();
		System.err.println("Current Folder: "+getCurrentFolder().getAbsolutePath());
		File[] filelist = getCurrentFolder().listFiles();
		if(!getCurrentFolder().equals(getScriptRoot())) {
			all.add(new File(getCurrentFolder(),".."));
		}
		for (File file : filelist) {
			if(file.isDirectory()) {
				all.add(file);
			}
		}
		return all;
	}

}
