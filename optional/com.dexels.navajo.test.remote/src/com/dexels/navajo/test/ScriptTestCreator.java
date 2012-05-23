package com.dexels.navajo.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ScriptTestCreator {

	public void create(File scriptDir, File outputDir) {
		System.err.println("Entering file enumeration: "+scriptDir);
		FileSystemEnumerator dss = new FileSystemEnumerator(scriptDir);
		List<String> l = dss.getQualifiedScriptNames();
		try {
			for (String ame : l) {
				File in = new File(scriptDir, ame+".xml");
				createFile(in,ame,outputDir);
			}
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			createManifest(l,outputDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createManifest(List<String> l, File outputDir) throws IOException {
		File man = new File(outputDir, "test.txt");
		FileWriter fw = new FileWriter(man);
		for (String entry : l) {
			fw.write(entry.replace("/", ".") +"\n");
		}
		fw.flush();
		fw.close();
	}	

	private void createFile(File in, String ame, File outputDir) {
//		File inFolder = in.getParentFile();
		String packagee = null;
		String className = null;
		File destFolder = null;
		if(ame.indexOf("/")==-1) {
			destFolder = outputDir;
			packagee = "";
			className = ame;
		} else {
			packagee = ame.substring(0,ame.lastIndexOf("/") );
			className = ame.substring(ame.lastIndexOf("/")+1,ame.length());
			destFolder = new File(outputDir,packagee);
		}
		
		if(!destFolder.exists()) {
			destFolder.mkdirs();
		}
		File destFile = new File(destFolder,className+".java");
		System.err.println("Generating package: "+packagee);
		if(destFile.exists()) {
			return;
		}
		try {
			writeJava(destFile, packagee, className);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeJava(File destFile, String packagee, String className) throws IOException {
		// TODO Auto-generated method stub
		if(destFile.exists()) {
			System.err.println("Skipping existing file: "+destFile);
			return;
		}
		FileWriter fw = new FileWriter(destFile);
		fw.write("// Generated stub by Dexels. PACKAGE: "+packagee+"\n\n");
		if(!"".equals(packagee)) {
			fw.write("package "+packagee.replaceAll("/", ".")+";\n\n");
		}


		fw.write("import com.dexels.navajo.test.ScriptTestCase;\n\n");
		fw.write("// Importing:  \n");
		fw.write("public class "+className+" extends ScriptTestCase {\n\n");
		fw.write("  public void testResult() {\n  }\n\n  @Override\n  public String getInputName() {\n    return null;\n  }\n}\n\n");
		
		
		
		fw.flush();
		fw.close();
		
	}

	public static void main(String[] args) {
		ScriptTestCreator c = new ScriptTestCreator();
		c.create(new File("/Users/frank/Documents/Spiritus/Ticketing-serv/scripts"), new File("/Users/frank/Documents/Spiritus/NavajoRemoteTest/generated"));
	}
}
