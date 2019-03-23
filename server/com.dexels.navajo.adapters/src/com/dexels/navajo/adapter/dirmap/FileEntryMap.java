package com.dexels.navajo.adapter.dirmap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.document.types.Binary;

public class FileEntryMap implements Mappable {
	
	private File file = null;
	private Binary contents = null;
	
	private static final Logger logger = LoggerFactory.getLogger(FileEntryMap.class);

	
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

	private Binary createBinary(File file) throws IOException {
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
		setDelete();
	}

	public void setDelete() {
		try {
			Files.delete(file.toPath());
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	public String getName() {
		return file.getName();
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}

	@Override
	public void kill() {
		
	}
}
