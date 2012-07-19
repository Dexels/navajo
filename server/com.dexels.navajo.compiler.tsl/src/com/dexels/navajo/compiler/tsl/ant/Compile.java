package com.dexels.navajo.compiler.tsl.ant;

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

public class Compile {
	public static void compile() throws IOException {
//		BatchCompiler.compile(commandLine, outWriter, errWriter, progress)
//		BatchCompiler.compile("-help", new PrintWriter(System.out), new PrintWriter(System.err), null);
		String res = callAnt(new File("build.xml"), new File("."), null);
		System.err.println("res: "+res);
	}


	public static String callAnt(File buildFile, File baseDir, Map<String,String> userProperties) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(output);
		
		Project p = new Project();
		p.setBaseDir(baseDir);
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
		p.setUserProperty("baseDir", baseDir.getAbsolutePath());		
		if(userProperties!=null) {
			for (Entry<String,String> w : userProperties.entrySet()) {
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
			
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
		} catch (BuildException e) {
			p.fireBuildFinished(e);
		}
		output.flush();
		output.close();
		return new String(output.toByteArray());
	}
}
