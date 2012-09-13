package com.dexels.navajo.tipi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntRun {
	
 static String callAnt(File buildFile, File baseDir, Map<String,String> userProperties, String target) throws IOException {
		userProperties.put("tipiAppstore", "true");
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(output);
		
		
		
		Project p = new Project();
		p.setBaseDir(baseDir);
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
		p.setUserProperty("baseDir", baseDir.getAbsolutePath());		
		for (Entry<String,String> w : userProperties.entrySet()) {
			System.err.println("Setting: key: "+w.getKey()+" value: "+w.getValue());
			p.setUserProperty(w.getKey(), w.getValue());		
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
			e.printStackTrace();
			
			p.fireBuildFinished(e);
		}
		output.flush();
		output.close();
		return new String(output.toByteArray());
	}
}
