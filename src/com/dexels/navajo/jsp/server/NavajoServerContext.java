package com.dexels.navajo.jsp.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class NavajoServerContext {
	private PageContext pageContext;
	private File currentFolder;
	private Map<String,Map<String,String>> cvsInfo = null;
	private ServletRequest request;
	
	
	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Map<String, Map<String, String>> getCvsInfo() {
		return cvsInfo;
	}

	public boolean isCVS() {
		File cvsFolder = new File(getCurrentFolder(),"CVS");
		return cvsFolder.exists();
	}

	public void setCommand(String command)  {
		try{
			
			if("cvsUpdate".equals(command)) {
				String path = getPageContext().getRequest().getParameter("path");
				Map<String,String> props = new HashMap<String, String>();
				props.put("path", path);
				callAnt("WEB-INF/ant/cvsUpdate.xml", props);
				// flush cvs info:
				cvsInfo = currentFolderCvsInfo();
			}
			if("setFolder".equals(command)) {
				String path = getPageContext().getRequest().getParameter("path");
				setPath(path);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void callAnt(String antFile, Map<String,String> params) {
		try {
			File contextFile = new File(pageContext.getServletContext().getRealPath(""));
			File buildFile = new File(contextFile,antFile);
			File navajoRoot = getNavajoRoot();
			System.err.println("Running ant: "+buildFile.getAbsolutePath()+" baseDir: "+navajoRoot.getAbsolutePath());
			String result = AntRun.callAnt(buildFile, navajoRoot, params, null);
			System.err.println("Result: \n"+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	
	public File getCurrentFolder() {
		return currentFolder;
	}
	
	private Map<String,Map<String,String>> currentFolderCvsInfo() throws IOException {
		File f = new File(getCurrentFolder(),"CVS/Entries");
		System.err.println("CVS entries: "+f.getAbsolutePath());
		if(!f.exists()) {
			System.err.println("No entries: "+f.getAbsolutePath());
			return null;
		}
		Map<String,Map<String,String>> result = new HashMap<String, Map<String,String>>();
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(true) {
			String line = bf.readLine();
			if(line==null) {
				break;
			}
			System.err.println("Parsing: "+line);
			String[] info = line.split("/");
			for (String string : info) {
				System.err.println("LINE: "+string);
			}
			if(info.length<4) {
				continue;
			}
			Map<String,String> infoMap = new HashMap<String,String>();
			String name = info[1];
			infoMap.put("revision", info[2]);
			infoMap.put("date", info[3]);
			result.put(name, infoMap);
			
		}
		System.err.println("Result: "+result);
		return result;
	}

	public void setCurrentFolder(File currentFolder) throws IOException {
		// Ignore navigation to non existing folders
		if(!currentFolder.exists()) {
			System.err.println("Ifnoring: "+currentFolder.getAbsolutePath());
			return;
		}
		this.currentFolder = currentFolder;
		cvsInfo = currentFolderCvsInfo();
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
			System.err.println("Scriptroot: "+rootPath);
			String currentPath = getCurrentFolder().getCanonicalPath();
			System.err.println("currentPath: "+currentPath);
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
		System.err.println("Navicating to: "+newCurrent);
		try {
			// Check if we aren't navigating outside the script folder
			if(newCurrent.getCanonicalPath().startsWith(getScriptRoot().getCanonicalPath())) {
				setCurrentFolder(newCurrent);
			} else {
				System.err.println("Ignored!");
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
//			if(file.isFile() && file.getName().endsWith("xml")) {
			int ii = file.getName().lastIndexOf('.');
			if(ii>=0 && !file.isDirectory()) {
				all.add(file.getName().substring(0,ii));
			}
//			}
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
