package com.dexels.navajo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteSourceIntrospection {
	public String host = "";
	public String username = "";
	public String password = "";
	public String path = "";
	public String extention = "";
	public String file = "";

	public RemoteSourceIntrospection() {
	}

	public RemoteSourceDefinition[] getFiles() {
		try {
			long start = System.currentTimeMillis();
			ArrayList<RemoteSourceDefinition> names = new ArrayList<RemoteSourceDefinition>();
			Connection conn = new Connection(host);
			conn.connect();
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			Session sess = conn.openSession();

			sess.execCommand("cd " + path + ";ls -RlF");

			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
//			System.err.println("Retrieving file information..");
			String currentPath = path;
			String subPath = "";
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				
				if(line.startsWith("./")){
					subPath = line.substring(1, line.indexOf(":"));
					currentPath = path + line.substring(1, line.indexOf(":"));
				}
				String file = line.substring(line.lastIndexOf(" ") + 1);
				if(file.endsWith(extention)){
					System.err.println("Path: " + path + ", subPath: " + subPath + ", file: " + file);
					RemoteSourceDefinition rsd = new RemoteSourceDefinition(path, subPath + "/" + file);
					names.add(rsd);
				}
			}
					
			sess.close();
			
			System.err.println("Reading dependencies for " + names.size() + " source files");
			// Now for all names, we inspect their dependencies;
			for(int i=0;i<names.size();i++){				
				RemoteSourceDefinition rsd = names.get(i);			
				inspectFile(conn, rsd);				
			}
						
			conn.close();
			System.err.println("Inspecting dependencies took " + (System.currentTimeMillis() - start) + " milliseconds");
			RemoteSourceDefinition[] result = new RemoteSourceDefinition[names.size()];
			result = (RemoteSourceDefinition[]) names.toArray(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace(System.err);			
		}
		return null;
	}
	
	private void inspectFile(Connection conn, RemoteSourceDefinition rsd) throws Exception{
		Session sess = conn.openSession();
		sess.execCommand("cat " + rsd.getPath());
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		
		while (true) {
			String line = br.readLine();
			if (line == null){
				break;
			}
			if(".xml".equalsIgnoreCase(extention) || "xml".equalsIgnoreCase(extention)){
				getTipiDependency(line, rsd);
			}
		}
		sess.close();
	}
	
	public void getTipiDependency(String line, RemoteSourceDefinition rsd){
		// A service= attribute for Tipi
		if(line.indexOf("service=") > -1){
			boolean callService = line.indexOf("<callService") > -1 || line.indexOf("injectNavajo") > -1;
			
			String dependency = line.substring(line.indexOf("service=") + 9);
			dependency = dependency.substring(0, dependency.indexOf("\""));
		
			if(dependency != null && dependency.indexOf("{") == -1){
				if(dependency.indexOf(";") > -1){
					StringTokenizer tok = new StringTokenizer(dependency, ";");
					while(tok.hasMoreTokens()){
						if(callService){
							rsd.addCallService(tok.nextToken());
						}else{
							rsd.addReadService(tok.nextToken());
						}
					}
				}else{
					if(callService){
						rsd.addCallService(dependency);
					}else{
						rsd.addReadService(dependency);
					}
				}
			}			
		}
		
		// Old style tipi ws call
		if(line.indexOf("<performMethod") > -1 && line.indexOf("method=") > -1){
			String dependency = line.substring(line.indexOf("method=") + 8);
			dependency = dependency.substring(0, dependency.indexOf("\""));
		
			if(dependency != null && dependency.indexOf("{") == -1){
				rsd.addCallService(dependency);
			}
		}		
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public static void main(String[] args) {
		RemoteSourceIntrospection rsi = new RemoteSourceIntrospection();
		rsi.setHost("hermes1.dexels.com");
		rsi.setUsername("sportlink");
		rsi.setPassword("sp0rtl1nk");
		rsi.setPath("/www/htdocs/knvb/club-asp/6/test/tipi");
		rsi.setExtention(".xml");

		RemoteSourceDefinition[] rsd = rsi.getFiles();
		for(int i=0;i<rsd.length;i++){
			System.err.println("Filepath: " + rsd[i].getPath());
			System.err.println("Filename: " + rsd[i].getName());
			ScriptDefinition[] services = rsd[i].getReadServices();
			for(int j=0;j<services.length;j++){
				System.err.println("      Reads: " + services[j].getScriptName());
			}
			services = rsd[i].getCallServices();
			for(int j=0;j<services.length;j++){
				System.err.println("      Calls: " + services[j].getScriptName());
			}
		}
	}

}
