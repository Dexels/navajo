package jnlp.sample.servlet.impl;

import java.io.File;
import java.nio.file.FileSystem;

import jnlp.sample.servlet.ResourceVerifier;

public class FileVerifier implements ResourceVerifier {

	private File basePath;

	public FileVerifier(File basePath) {
		this.basePath = basePath;
	}

	@Override
	public void verifyResource(String path, File f) throws SecurityException {
		// TODO Auto-generated method stub
		
	}
	

}
