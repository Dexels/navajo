package com.dexels.navajo.adapter.dirmap;

import java.io.File;
import java.io.IOException;

import com.dexels.navajo.document.types.Binary;

public class FileEntryMap {
	
	private File file = null;
	private Binary contents = null;
	
	
	public void setAbsolutePath(String path) throws IOException {
		setFile(new File(path));
	}

	public String getAbsolutePath() {
		return file.getAbsolutePath();
	}
	public void setFile(File file) throws IOException {
		this.file = file;
		createBinary(file);
	}

	protected Binary createBinary(File file) throws IOException {
		contents = new Binary(file);
		return contents;
	}

	public int getFileAge() {
		long age = System.currentTimeMillis() - file.lastModified();
		if(age > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		return (int) age;
	}
	public int getSize() {
		return (int) file.length();
	}

	public String getMimeType() throws IOException {
		if(contents==null) {
			createBinary(file);
		}
		return contents.getMimeType();
	}

	public Binary getContents() throws IOException {
		if(contents==null) {
			createBinary(file);
		}
		return contents;
	}
	
	/**
	 * @param b  
	 */
	public void setDelete(boolean b) {
		file.delete();
	}

	public String getName() {
		return file.getName();
	}
}
