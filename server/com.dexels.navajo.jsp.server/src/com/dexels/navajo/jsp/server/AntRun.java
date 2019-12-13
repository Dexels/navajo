package com.dexels.navajo.jsp.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntRun {
	
	
	private final static Logger logger = LoggerFactory.getLogger(AntRun.class);
	
	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
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
	
	public static String callAnt(InputStream antStream, File baseDir, Map<String,String> userProperties) throws IOException {
		File temp = createTempFile(antStream);
		String result = callAnt(temp, baseDir, userProperties,null);
		temp.delete();
		return result;
	}
	
	private static File createTempFile(InputStream antStream) throws IOException {
		File f = File.createTempFile("tipiAnt", ".xml");
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, antStream);
		return f;
	}

	public static String callAnt(File buildFile, File baseDir, Map<String,String> userProperties, String target) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(output);
		
		Project p = new Project();
		p.setBaseDir(baseDir);
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
		p.setUserProperty("baseDir", baseDir.getAbsolutePath());		
		if(userProperties!=null) {
			for (Entry<String,String> w : userProperties.entrySet()) {
//				System.err.println("Setting: key: "+w.getKey()+" value: "+w.getValue());
				p.setUserProperty(w.getKey(), w.getValue());		
			}
		}
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(ps);
		consoleLogger.setOutputPrintStream(ps);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		p.addBuildListener(consoleLogger);

		try {
			p.fireBuildStarted();
			p.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			p.addReference("ant.projectHelper", helper);
			helper.parse(p, buildFile);
			if(target==null) {
				p.executeTarget(p.getDefaultTarget());
			} else {
				p.executeTarget(target);
			}
			p.fireBuildFinished(null);
		} catch (BuildException e) {
			logger.error("Error: ", e);
			
			p.fireBuildFinished(e);
		}
		output.flush();
		output.close();
		return new String(output.toByteArray());
	}
}
