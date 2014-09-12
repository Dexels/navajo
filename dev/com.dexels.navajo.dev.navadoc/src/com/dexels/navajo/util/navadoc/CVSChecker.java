package com.dexels.navajo.util.navadoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CVSChecker{
	
	private HashMap<String, String> scriptStatus = new HashMap<String, String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(CVSChecker.class);
	
	public CVSChecker(String path){
		try{
			
			Process q = Runtime.getRuntime().exec("cvs -Q status", new String[]{}, new File(path));
			BufferedReader read = new BufferedReader(new InputStreamReader(q.getInputStream(),"UTF-8"));
			String line = "";
			while((line = read.readLine()) != null){
				if(line.indexOf("File:") > -1){
					try{
						String servicename = line.substring(line.indexOf(": ") + 1, line.indexOf("Status"));
						String status = line.substring(line.indexOf("Status:") + 7);
					
//						System.err.println("Service: " + servicename + ", status: " + status);
						if(servicename != null && status != null){
							scriptStatus.put(servicename.trim(), status.trim());
						}						
					}catch(Exception e){
						System.err.println("Something went wrong parsing line: " + line);
					}
				}
			}
			q.destroy();
		}catch(Exception e){
			logger.error("Error: ", e);
		}				
	}
	
	public String getScriptStatus(String script){
		return scriptStatus.get(script);
	}
	
	public static void main(String[] args){
		new CVSChecker("/home/aphilip/workspace/sportlink-serv/navajo-tester/auxilary/scripts/club");
	}

} 