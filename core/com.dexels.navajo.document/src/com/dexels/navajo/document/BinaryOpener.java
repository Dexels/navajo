package com.dexels.navajo.document;

import java.io.File;

import com.dexels.navajo.document.types.Binary;

public interface BinaryOpener {

	public boolean browse(String scheme, String url);

	public boolean browse(String url);

	public boolean mail(String url);
	
	public boolean open(Binary b);
	
	public boolean open(File f);
	
	public boolean open(String s);
	
//	public void mail(S)
}