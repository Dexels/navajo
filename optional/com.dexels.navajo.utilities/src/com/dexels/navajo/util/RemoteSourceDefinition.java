package com.dexels.navajo.util;

import java.util.ArrayList;

public class RemoteSourceDefinition {
	private String fileName;
	private String sourceLocation;
	private ArrayList<ScriptDefinition> callList = new ArrayList<ScriptDefinition>();
	private ArrayList<ScriptDefinition> readList = new ArrayList<ScriptDefinition>();
	
	public RemoteSourceDefinition(String path, String fileName) {
		this.fileName = fileName;
		sourceLocation = path + fileName;
	}
	
	public void addCallService(String serviceName){
		if(serviceName.startsWith("'")){
			serviceName = serviceName.substring(1);
		}
		if(serviceName.endsWith("'")){
			serviceName = serviceName.substring(0, serviceName.length() - 1);
		}
		boolean found = false;
		for (ScriptDefinition sd : callList) {
			if(sd.getScriptName().equals(serviceName)) {
				found = true;
			}
		}
		if(found){
			callList.add(new ScriptDefinition(serviceName));
		}
	}

	public void addReadService(String serviceName){
		if(serviceName.startsWith("'")){
			serviceName = serviceName.substring(1);
		}
		if(serviceName.endsWith("'")){
			serviceName = serviceName.substring(0, serviceName.length() - 1);
		}
		
		boolean found = false;
		for (ScriptDefinition sd : readList) {
			if(sd.getScriptName().equals(serviceName)) {
				found = true;
			}
		}
		if(!found){
			readList.add(new ScriptDefinition(serviceName));
		}

	}
	
	public String getPath() {
		return sourceLocation;
	}
	
	public String getName(){
		int start = 0;
		if(fileName.startsWith("/")){
			start = 1;
		}
		return fileName.substring(start, fileName.lastIndexOf("."));
	}
	
	public ScriptDefinition[] getCallServices(){
		ScriptDefinition[] result = new ScriptDefinition[callList.size()];
		result = callList.toArray(result);
		return result;
	}
	
	public ScriptDefinition[] getReadServices(){
		ScriptDefinition[] result = new ScriptDefinition[readList.size()];
		result = readList.toArray(result);
		return result;
	}
	
}
