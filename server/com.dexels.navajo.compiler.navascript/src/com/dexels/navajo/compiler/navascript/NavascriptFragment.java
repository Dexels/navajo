package com.dexels.navajo.compiler.navascript;

public interface NavascriptFragment {

	public void consumeToken(String s);
	
	public String consumedFragment();
	
	public void finalize();
}
