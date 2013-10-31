package com.dexels.navajo.tipi.dev.ant;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
	
	public static void callAnt(InputStream antStream, File baseDir, Map<String,String> userProperties, Map<String,Class<?>> taskDefinitions, String target, PrintStream errorStream) throws IOException {
		File temp = createTempFile(antStream);
		callAnt(temp, baseDir, userProperties, taskDefinitions, target,errorStream);
		temp.delete();
		
	}
	
	private static File createTempFile(InputStream antStream) throws IOException {
		File f = File.createTempFile("tipiAnt", ".xml");
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, antStream);

		return f;
	}

	public static void callAnt(File buildFile, File baseDir, Map<String,String> userProperties, Map<String,Class<?>> taskDefinitions, String target, PrintStream errorStream) throws IOException {
		userProperties.put("tipiAppstore", "true");
		
		Project p = new Project();
		p.setBaseDir(baseDir);
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
		p.setUserProperty("baseDir", baseDir.getAbsolutePath());		
		if(userProperties!=null) {
			for (Entry<String,String> w : userProperties.entrySet()) {
//				logger.info("Adding property: "+w.getKey()+" value: "+w.getValue());
				p.setUserProperty(w.getKey(), w.getValue());		
			}
		}
		if(errorStream!=null) {
			DefaultLogger consoleLogger = new DefaultLogger();
			consoleLogger.setErrorPrintStream(errorStream);
			consoleLogger.setOutputPrintStream(errorStream);
			consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
			p.addBuildListener(consoleLogger);
		}
		if(taskDefinitions!=null) {
			for (Entry<String,Class<?>> e : taskDefinitions.entrySet()) {
				p.addTaskDefinition(e.getKey(), e.getValue());
			}
		}

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
			p.fireBuildFinished(e);
			e.printStackTrace();
		}
	}



}
