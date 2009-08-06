package com.dexels.navajo.tipi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntRun {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
//		File buildFile = new File("build.xml");
//		Project p = new Project();
//		p.setUserProperty("ant.file", buildFile.getAbsolutePath());		
//		DefaultLogger consoleLogger = new DefaultLogger();
//		consoleLogger.setErrorPrintStream(System.err);
//		consoleLogger.setOutputPrintStream(System.out);
//		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
//		p.addBuildListener(consoleLogger);
//
//		try {
//			p.fireBuildStarted();
//			p.init();
//			ProjectHelper helper = ProjectHelper.getProjectHelper();
//			p.addReference("ant.projectHelper", helper);
//			helper.parse(p, buildFile);
//			p.executeTarget(p.getDefaultTarget());
//			p.fireBuildFinished(null);
//		} catch (BuildException e) {
//			p.fireBuildFinished(e);
//		}

		Map<String, String> props = new HashMap<String, String>();
		props.put("application", "aap");
		props.put("zipDir", "/Users/frank/Documents/runtime-New_configuration(1)/Baaaapaa/");
	//	props.put("zipDir", "aap.zip");
		
		File buildFile = new File("/Users/frank/Documents/Spiritus/TipiServer/WebContent/WEB-INF/ant/zipoutput.xml");
		FileInputStream fix = new FileInputStream(buildFile);
		String result = callAnt(fix, new File("/Users/frank/Documents/Spiritus/TipiServer/"), props);
		fix.close();
		System.err.println("result:\n"+result);
	}
	
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
		String result = callAnt(temp, baseDir, userProperties);
		temp.delete();
		return result;
		
	}
	
	private static File createTempFile(InputStream antStream) throws IOException {
		File f = File.createTempFile("tipiAnt", ".xml");
		FileOutputStream fos = new FileOutputStream(f);
		copyResource(fos, antStream);
		return f;
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
//		Map<String,Object> m =  p.getProperties();
	//	System.err.println("Map: "+m);
		output.flush();
		output.close();
		return new String(output.toByteArray());
	}
}
