package com.dexels.navajo.util;

import java.util.ArrayList;

public class RemoteSourceDefinition {

private String sourceLocation;
	private ArrayList<ScriptDefinition> callList = new ArrayList<ScriptDefinition>();
	private ArrayList<ScriptDefinition> readList = new ArrayList<ScriptDefinition>();
	
	public RemoteSourceDefinition(String path) {
		sourceLocation = path;
	}
	
	public void addCallService(String serviceName){
		if(serviceName.startsWith("'")){
			serviceName = serviceName.substring(1);
		}
		if(serviceName.endsWith("'")){
			serviceName = serviceName.substring(0, serviceName.length() - 1);
		}
		if(!callList.contains(serviceName)){
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
		if(!readList.contains(serviceName)){
			readList.add(new ScriptDefinition(serviceName));
		}
	}
	
	public String getPath() {
		return sourceLocation;
	}
	
	public String getName(){
		return sourceLocation.substring(sourceLocation.lastIndexOf("/") + 1, sourceLocation.lastIndexOf("."));
	}
	
	public ScriptDefinition[] getCallServices(){
		ScriptDefinition[] result = new ScriptDefinition[callList.size()];
		result = (ScriptDefinition[]) callList.toArray(result);
		return result;
	}
	
	public ScriptDefinition[] getReadServices(){
		ScriptDefinition[] result = new ScriptDefinition[readList.size()];
		result = (ScriptDefinition[]) readList.toArray(result);
		return result;
	}
	
}
