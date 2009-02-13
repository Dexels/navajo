package com.dexels.navajo.tipi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

public class TipiDownloadJarTask extends BaseTipiClientTask {
	public void execute() throws BuildException {
		if (extensions == null || "".equals(extensions)) {
			throw new BuildException("No extensions defined ");
		}
		StringTokenizer st = new StringTokenizer(extensions, ",");
		while (st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
				appendExtension(ext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void parseProjectDefinition(URL projectURL, XMLElement result)
			throws MalformedURLException {
		System.err.println("PRoject dir: "+projectURL);
		URL unsigned = new URL(projectURL, "unsigned/");

		List<XMLElement> jars = result.getElementsByTagName("jar");
		File f = new File("lib");
		f.mkdirs();
		for (XMLElement element : jars) {
			String path = element.getStringAttribute("path");
			URL jar = new URL(unsigned, path);
			System.err.println("URL: " + jar);
			try {
				downloadJar(jar,path,f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void downloadJar(URL jar, String path, File f) throws IOException {
		File file = new File(f,path);
		System.err.println("File: "+f.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		InputStream is = jar.openStream();
		copyResource(fos, is);
	}
	
	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}


}
