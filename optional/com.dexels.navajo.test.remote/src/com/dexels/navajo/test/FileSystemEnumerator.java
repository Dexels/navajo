package com.dexels.navajo.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSystemEnumerator implements ScriptEnumerator {

	private File baseDir;


	public FileSystemEnumerator(File baseDir) {
		this.baseDir = baseDir;
	}
	
	public List<String> getQualifiedScriptNames() {
		List<String> result = new ArrayList<String>();
		walkTree(baseDir, baseDir, "", result);
		return result;
	}

	
	public void walkTree(File base, File current, String path, List<String> output) {
		File[] fff = current.listFiles();
		for (int i = 0; i < fff.length; i++) {
			String mmmm = path+"/"+fff[i].getName();
			if(fff[i].isDirectory()) {
				walkTree(base,fff[i],mmmm,output);
			}
			if(fff[i].isFile() && fff[i].getName().endsWith(".xml")) {
				mmmm = mmmm.substring(0,mmmm.length()-4);
				if(mmmm.startsWith("/")) {
					mmmm = mmmm.substring(1);
				}
				output.add(mmmm);
			}
		}
	}

	public static void main(String[] args) {
		FileSystemEnumerator aa = new FileSystemEnumerator(new File("/Users/frank/Documents/Spiritus/Ticketing-serv/scripts"));
		List<String> bb = aa.getQualifiedScriptNames();
		System.err.println(">> "+bb);
	}
}
