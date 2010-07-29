package com.dexels.navajo.jsp.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.jsp.NavajoContext;
import com.dexels.navajo.jsp.server.impl.ScriptStatusImpl;

public class NavajoServerContext {
	private PageContext pageContext;
	private File currentFolder;
	private Map<String,Map<String,String>> cvsInfo = null;
	private ServletRequest request;
	private File customNavajoRoot;
	private NavajoContext navajoContext;
	private ScriptStatus scriptStatus;
	
	public ScriptStatus getScriptStatus() {
		return scriptStatus;
	}

	public NavajoContext getNavajoContext() {
		return navajoContext;
	}

	public void setNavajoContext(NavajoContext navajoContext) {
		this.navajoContext = navajoContext;
	}

	public void setCustomNavajoRoot(File customNavajoRoot) {
		this.customNavajoRoot = customNavajoRoot;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Map<String, Map<String, String>> getCvsInfo() {
		return cvsInfo;
	}

	public boolean isCVS() throws IOException {
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
	//		System.err.println("Running ant: "+buildFile.getAbsolutePath()+" baseDir: "+navajoRoot.getAbsolutePath());
			String result = AntRun.callAnt(buildFile, navajoRoot, params, null);
//			System.err.println("Result: \n"+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	
	public File getCurrentFolder() throws IOException {
		if(currentFolder==null) {
			return getScriptRoot();
		}
		return currentFolder;
	}
	
	private Map<String,Map<String,String>> currentFolderCvsInfo() throws IOException {
		File f = new File(getCurrentFolder(),"CVS/Entries");
		if(!f.exists()) {
			return null;
		}
		Map<String,Map<String,String>> result = new HashMap<String, Map<String,String>>();
		BufferedReader bf = new BufferedReader(new FileReader(f));
		while(true) {
			String line = bf.readLine();
			if(line==null) {
				break;
			}
			String[] info = line.split("/");
		
			if(info.length<4) {
				continue;
			}
			Map<String,String> infoMap = new HashMap<String,String>();
			String name = info[1];
			infoMap.put("revision", info[2]);
			infoMap.put("date", info[3]);
			result.put(name, infoMap);
			
		}
		return result;
	}

	public void setCurrentFolder(File currentFolder) throws IOException {
		// Ignore navigation to non existing folders
		if(!currentFolder.exists()) {
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
		if(getPageContext()==null) {
			return ".";
		}
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

	public void setPath(String path) throws IOException {
		File newCurrent = new File(getCurrentFolder(),path);
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
		this.pageContext = pageContext;
		if(getCurrentFolder()==null) {
			setCurrentFolder(getScriptRoot());
		}
	}

	public PageContext getPageContext() {
		return pageContext;
	}
	
	public File getNavajoRoot() throws IOException {
		if(getPageContext()==null) {
			return customNavajoRoot;
		}
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

	public List<String> getScripts() throws IOException {
		List<String> all = new ArrayList<String>();
		File[] filelist = getCurrentFolder().listFiles();
		for (File file : filelist) {
			int ii = file.getName().lastIndexOf('.');
			if(ii>=0 && !file.isDirectory()) {
				all.add(file.getName().substring(0,ii));
			}
		}
		return all;
	}
	

	public List<ScriptStatus> getScriptList() throws IOException {
		List<ScriptStatus> all = new ArrayList<ScriptStatus>();
		File[] filelist = getCurrentFolder().listFiles();
		for (File file : filelist) {
			int ii = file.getName().lastIndexOf('.');
			if(ii>=0 && !file.isDirectory()) {
				ScriptStatus s = new ScriptStatusImpl(this,getScriptRoot(), file, getCompiledRoot());
				all.add(s);
			}
		}
		Collections.sort(all);
		return all;
	}


	public File getContextRoot() {
		return new File(getContextPath());
	}
	
	private File getCompiledRoot() throws IOException {
		// TODO fix this monstrosity. Read it from the server.xml
		File file = new File(getNavajoRoot(),"compiled");
		if(file.exists()) {
			return file;
		}
		file = new File(getNavajoRoot(),"classes");
		return file;
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
		
		Collections.sort(all);
		return all;
	}

	
	public static void main(String[] args) throws IOException {
//		/Users/frank/Documents/Spiritus/NavajoServer/sample/Server
		NavajoServerContext nsc = new NavajoServerContext();
		nsc.setCustomNavajoRoot(new File("/Users/frank/Documents/Spiritus/NavajoJspServer/testContext/Server"));
		nsc.setCurrentFolder(nsc.getScriptRoot());
		List<ScriptStatus> ss = nsc.getScriptList();
		for (ScriptStatus scr : ss) {
			System.err.println(scr.toString());
		}
		List<File> ff = nsc.getFolders();
		File f = nsc.getCurrentFolder();
		for (File file : ff) {
			nsc.setCurrentFolder(file);
			List<ScriptStatus> sss = nsc.getScriptList();
			for (ScriptStatus scr : sss) {
				System.err.println(scr.toString());
			}
		}
		nsc.setCurrentFolder(f);
		ScriptStatus xxxx =  nsc.getScriptInfo("location/InitInsertLocation");
		System.err.println("x: "+xxxx);
		ScriptStatus yyyy =  nsc.getScriptInfoMap().get("location/InitInsertLocation");
		System.err.println("x: "+yyyy);
		
	}

	
	public ScriptInfoAccessMap getScriptInfoMap()  {
		return new ScriptInfoAccessMap(this);
	}

	public ScriptStatus getScriptInfo(String path)  {
		System.err.println("Getting info: "+path);
		if(path==null || "".equals(path)) {
			return null;
		}
		try {
			ScriptStatus s = new ScriptStatusImpl(this,getScriptRoot(), getCompiledRoot(), path);
			System.err.println("returning script status:"+s);
			return s;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.err.println("Fail?");
		return null;
	}
	
	public void setScript(String script) {
//
		scriptStatus = getScriptInfo(script);
		System.err.println("Script set done.");
	//	System.err.println("Scirpt: "+scriptStatus.getName());
	}
}
