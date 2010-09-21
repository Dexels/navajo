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
	
	public void setFile(File file) throws IOException {
		this.file = file;
		contents = new Binary(file);
	}

	public int getFileAge() {
		return (int) (System.currentTimeMillis() - file.lastModified());
	}
	public int getSize() {
		return (int) file.length();
	}

	public String getMimeType() {
		return contents.getMimeType();
	}

	public Binary getContents() throws IOException {
		return contents;
	}
	
	public void setDoDelete(boolean b) {
		file.delete();
	}

	public String getName() {
		return file.getName();
	}
}
