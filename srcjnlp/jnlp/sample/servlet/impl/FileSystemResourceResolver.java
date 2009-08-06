package jnlp.sample.servlet.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import jnlp.sample.servlet.ResourceResolver;

public class FileSystemResourceResolver implements ResourceResolver {
   private static final String JNLP_MIME_TYPE      = "application/x-java-jnlp-file";
   private static final String JAR_MIME_TYPE       = "application/x-java-archive";

   private static final String JAR_MIME_TYPE_NEW   = "application/java-archive";
   
   // Default extension for the JNLP file
   private static final String JNLP_EXTENSION      = ".jnlp";
   private static final String JAR_EXTENSION       = ".jar";
	final File baseDir;

	public FileSystemResourceResolver(File baseDir) {
		this.baseDir = baseDir;
	}
	public String getMimeType(String path) {
		if(path.endsWith(JNLP_EXTENSION)) {
			return JNLP_MIME_TYPE;
		}
		if(path.endsWith(JAR_EXTENSION)) {
			return JAR_MIME_TYPE_NEW;
		}
		return null;
	}

	public URL getResource(String path) throws IOException {
		System.err.println("Looking for path in: "+path);
		String realPath = path.substring("Apps/".length(),path.length());
		System.err.println("Looking for real path in: "+realPath);
		File result = new File(baseDir,realPath);
		return result.toURI().toURL();
//		return null;
	}
	@Override
	public File getDir(String dirPath) {
		String realPath = dirPath.substring("Apps/".length(),dirPath.length());
		return new File(baseDir,realPath);
	}
	@Override
	public long getLastModified(String path) {
		String realPath = path.substring("Apps/".length(),path.length());
		File result = new File(baseDir,realPath);
		return result.lastModified();
//		return 0;
	}

}
