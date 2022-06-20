package com.dexels.navajo.document.navascript.tags;

public interface MapDefinitionInterrogator {

	public boolean isMethod(String adapter,String m) throws Exception;
	
	public boolean isField(String adapter, String m) throws Exception;

	public boolean isDeclaredField(String className, String m) throws Exception;

	public boolean isValidClass(String className);
	
	public boolean isValidAdapter(String adapter);
	
}
