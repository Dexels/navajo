package com.dexels.navajo.jsp.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

public class InstallerContext {
	private PageContext pageContext;
	private Map<String,String> systemContexts = new HashMap<String, String>();
	private String currentContext;
	public boolean isValidInstallation() {
		
		getNavajoRootPath();
		

		if(currentContext==null) {
			return false;
		}
		File f = new File(currentContext);
//		System.err.println("Proposed root path seems to exist: "+f.getAbsolutePath()+" : "+f.exists());
		if(!f.exists()) {
			return false;
		}
//		File adapters = new File(f,"adapters");
		File scripts = new File(f,"scripts");
		if(!scripts.exists()) {
			return false;
		}
		File config = new File(f,"config");
		if(!config.exists()) {
			return false;
		}
		return config!=null;
	}

	protected String getNavajoRootPath() {
		if(pageContext==null) {
			System.err.println("Whoops");
			Thread.dumpStack();
		}
		String force = pageContext.getServletContext().getInitParameter("forcedNavajoPath");
		if(force!=null) {
//			System.err.println("Using the force! navajo.properties will be ignored!");
			currentContext = force;
		} else {
			try {
				loadSystemContexts();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return currentContext;
	}
	
	private void initialize() throws IOException {
		loadSystemContexts();
		String contextPath = getContextName().substring(1);
//		System.err.println("Context path: "+contextPath+" contexts: "+systemContexts);
		currentContext = systemContexts.get(contextPath);
//		saveSystemContexts();
	}

	private void loadSystemContexts() throws IOException {
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"navajo.properties");
		if(!navajo.exists()) {
			return;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));
		while(true) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			String[] r = line.split("=");
			systemContexts.put(r[0], r[1]);
		}
		br.close();
	}
	
//	private void saveSystemContexts() throws IOException {
//		File home = new File(System.getProperty("user.home"));
//		File navajo = new File(home,"navajo.properties");
//		FileWriter fw = new FileWriter(navajo);
//		for (Entry<String,String> e: systemContexts.entrySet()) {
//			fw.write(e.getKey()+"="+e.getValue()+"\n");
//		}
//		fw.flush();
//		fw.close();
//	}
	
	public String getNavajoRoot(String serverContext) throws IOException {
		return getNavajoRootPath();
//		ic.loadSystemContexts();
//		
//		return ic.systemContexts.get(serverContext);
	}
	
	public String getSuggestedPath() {
		File home = new File(System.getProperty("user.home"));
		String contextPath = getContextName().substring(1);
		File path = new File(home,contextPath);
		return path.getAbsolutePath();
	}
	
	public String getContextName() {
		return getPageContext().getServletContext().getContextPath();
	}

	public String getContextPath() {
		return getPageContext().getServletConfig().getServletContext().getRealPath("");
	}

	public String getServerInfo() {
		return "Server: "+getPageContext().getServletConfig().getServletContext().getServerInfo();
	}

	public void setPageContext(PageContext pageContext) {
		if(this.pageContext==null) {
			this.pageContext = pageContext;
			try {
				initialize();
//				System.err.println("InstallerContext initialized");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			this.pageContext = pageContext;
		}
	}

	public PageContext getPageContext() {
		return pageContext;
	}
	



}
