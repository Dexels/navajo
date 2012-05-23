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
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.jsp.server.impl.ScriptStatusImpl;

public class NavajoServerContext {
	private PageContext pageContext;
	private File currentFolder;
	private Map<String,Map<String,String>> cvsInfo = null;
	private ServletRequest request;
	private File customNavajoRoot;
	private NavajoContext navajoContext;
	private ScriptStatus scriptStatus;
	private InstallerContext installerContext;
	
	public InstallerContext getInstallerContext() {
		return installerContext;
	}

	public void setInstallerContext(InstallerContext installerContext) throws IOException {
		this.installerContext = installerContext;

	}

	public ScriptStatus getScriptStatus() {
		return scriptStatus;
	}

	public NavajoContext getNavajoContext() {
		return navajoContext;
	}

	public void setNavajoContext(NavajoContext navajoContext) {
		this.navajoContext = navajoContext;
	}

	/**
	 * I think this one is not used. Should remove it
	 * @param customNavajoRoot
	 */
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
			AntRun.callAnt(buildFile, navajoRoot, params, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	public void setupClient() throws IOException {
		Map<String,String> settings = getClientSettings();
		
		getNavajoContext().setupClient(settings.get("server") , settings.get("username")  , settings.get("password"), settings.get("requestServerName"), Integer.parseInt(settings.get("requestServerPort")), settings.get("requestContextPath"),null, true);
	}

	public Map<String,String> getClientSettings() throws IOException {
		HttpServletRequest rr =  (HttpServletRequest) getPageContext().getRequest();
		StringBuffer sb = new StringBuffer();
		sb.append(rr.getServerName()+":");
		sb.append(rr.getServerPort());
		sb.append(rr.getContextPath());
		String server = sb.toString()+"/Comet";
		ResourceBundle client = getClientSettingsBundle();
		String username = "guest";
		String password = "guest";
		if(client!=null) {
			username = client.getString("username");
			password = client.getString("password");
			if(client.containsKey("servlet")) {
				String serv = client.getString("servlet");
				if(serv!=null) {
					server = sb.toString()+serv;
				}
			}
		}
		HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
		String requestServerName = request.getServerName();
		int requestServerPort = request.getServerPort();
		String requestContextPath = request.getContextPath();
		
		Map<String,String> result = new HashMap<String, String>();
		result.put("server", server);
		result.put("username", username);
		result.put("password", password);
		result.put("requestServerName", requestServerName);
		result.put("requestServerPort", ""+requestServerPort);
		result.put("requestContextPath", requestContextPath);
		return result;
	}
	
	public File getCurrentFolder() throws IOException {
		if(currentFolder==null) {
			currentFolder = getScriptRoot();
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
			currentPath = currentPath.replace('\\', '/');
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
	}

	public PageContext getPageContext() {
		return pageContext;
	}
	
	public File getNavajoRoot() throws IOException {
		if(getPageContext()==null) {
			System.err.println("Returning custom root. Don't know what this implies...");
			return customNavajoRoot;
		}
		
		String installedContext = getInstallerContext().getNavajoRoot(getContextPath().substring(1));

		if(installedContext==null) {
			String real = pageContext.getServletContext().getRealPath("");
			return new File(real);			
		}
		return new File(installedContext);
	}

	public File getScriptRoot() throws IOException {
		return new File(getNavajoRoot(),"scripts/");
	}


	public File getConfigRoot() throws IOException {
		return new File(getNavajoRoot(),"config/");
	}
	
	public ResourceBundle getClientSettingsBundle() throws IOException {
		File props = new File(getConfigRoot(),"client.properties");
		if(!props.exists()) {
			System.err.println("Not found.");
			return null;
		}
		FileReader fr = null;
		try {
		fr = new FileReader(props);
			PropertyResourceBundle b = new PropertyResourceBundle(fr);
			return b;
		} finally {
			fr.close();
		}
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
//		File scripts  = new File(getContextRoot(),"scripts");
//		File navajo  = new File(scripts,"navajo");
//		filelist = navajo.listFiles();
//		for (File file : filelist) {
//			int ii = file.getName().lastIndexOf('.');
//			if(ii>=0 && !file.isDirectory()) {
//				all.add("navajo/"+file.getName().substring(0,ii));
//			}
//		}
		Collections.sort(all);
		return all;
	}
	
	public List<String> getNavajoScripts() throws IOException {
		List<String> all = new ArrayList<String>();
		File scripts  = new File(getContextRoot(),"scripts");
		File navajo  = new File(scripts,"navajo");
		File[] filelist = navajo.listFiles();
		for (File file : filelist) {
			int ii = file.getName().lastIndexOf('.');
			if(ii>=0 && !file.isDirectory()) {
				all.add("navajo/"+file.getName().substring(0,ii));
			}
		}
		Collections.sort(all);
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
//		System.err.println("Current Folder: "+getCurrentFolder().getAbsolutePath());
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
	
	public ScriptInfoAccessMap getScriptInfoMap()  {
		return new ScriptInfoAccessMap(this);
	}

	public ScriptStatus getScriptInfo(String path)  {
		if(path==null || "".equals(path)) {
			return null;
		}
		try {
			ScriptStatus s = new ScriptStatusImpl(this,getScriptRoot(), getCompiledRoot(), path);
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
	}
}
