package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiSourceDeployer {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSourceDeployer.class);
	
	public void deploy(File tipiDir, File documentationDir, List<String> pathElements, FileWriter overviewWriter) throws IOException {
		writeDirLink(tipiDir,overviewWriter,pathElements);
		for (File current : tipiDir.listFiles()) {
			if(current.isFile() && current.toString().endsWith("xml")) {
				deployFile(documentationDir,current,pathElements,overviewWriter);
			}
			if(current.isDirectory() && !current.getName().equals("CVS")) {
			
				List<String> cc = new LinkedList<String>(pathElements);
				cc.add(current.getName());
				File docdir = new File(documentationDir,current.getName());
				docdir.mkdir();
				deploy(current, docdir, cc,overviewWriter);
			}
		}
	}
	
	private void deployResource(File resourceDir,  FileWriter resourceListWriter, String appserverCodebase, List<String> pathElements) throws IOException {
		writeDirLink(resourceDir,resourceListWriter,pathElements);
		for (File current : resourceDir.listFiles()) {
			if(current.isFile()) {
				writeResourceLink(current,pathElements,resourceListWriter, appserverCodebase);
				
			}
			if(current.isDirectory() && !current.getName().equals("CVS")) {
			
				List<String> cc = new LinkedList<String>(pathElements);
				cc.add(current.getName());
//				File docdir = new File(docDestination,current.getName());
	//			docdir.mkdir();
				deployResource(current, resourceListWriter,appserverCodebase, cc);
			}
		}
	}
	
	private void writeDirLink(File tipiDir, FileWriter overviewWriter, List<String> pathElements) throws IOException {
		overviewWriter.write(indent(pathElements.size()));
		overviewWriter.write(tipiDir.getName()+"\n");
	}

	private String indent(int indent) {
		StringBuffer sb = new StringBuffer();
		sb.append("  ");
		for (int j = 0; j < indent; j++) {
			sb.append("  ");
		}
		sb.append("* ");
		return sb.toString();
	}

	private void deployFile(File documentationDir, File current, List<String> pathElements, FileWriter overviewWriter) throws IOException {
		logger.info("Current: "+current);
		String fileName = current.getName();
		String newName = fileName.substring(0,fileName.length()-4)+".txt";
		File f = new File(documentationDir,newName.toLowerCase());
		createDocSource(current,f);
		writeFileLink(current,pathElements,overviewWriter);
	}

	private void writeResourceLink(File current, List<String> pathElements, FileWriter overviewWriter, String appCodebase) throws IOException {
		overviewWriter.write(indent(pathElements.size()+1));
		String fileName = current.getName();
		//String newName = fileName.substring(0,fileName.length()-4);
		overviewWriter.write("[["+appCodebase + createHttpPath(pathElements)+"/"+fileName+"|"+fileName+"]]\n");

	}
	
	private void writeFileLink(File current, List<String> pathElements, FileWriter overviewWriter) throws IOException {
		overviewWriter.write(indent(pathElements.size()+1));
		String fileName = current.getName();
		String newName = fileName.substring(0,fileName.length()-4);
		overviewWriter.write("[[.:"+createWikiPath(pathElements)+":"+newName.toLowerCase()+"|"+newName+"]]\n");
	}

	private String createWikiPath(List<String> pathElements) {
		if(pathElements.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pathElements.size(); i++) {
			sb.append(pathElements.get(i).toLowerCase());
			if(i != pathElements.size()-1) {
				sb.append(":");
			}
		}
		return sb.toString();
	}
	
	private String createHttpPath(List<String> pathElements) {
		if(pathElements.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pathElements.size(); i++) {
			sb.append(pathElements.get(i));
			if(i != pathElements.size()-1) {
				sb.append("/");
			}
		}
		return sb.toString();
	}

	private void createDocSource(File current, File destination) throws IOException {
		FileWriter fw = new FileWriter(destination);
		Date d = new Date(current.lastModified());
		fw.write("Source last modified: "+d+"\n");
		fw.write("<code xml>\n");
		BufferedReader fr = new BufferedReader(new FileReader(current));
		String line = null;
		do {
			line = fr.readLine();
			if(line!=null) {
				fw.write(line+"\n");
			}
		} while(line!=null);
		fr.close();
		fw.write("</code>");
		fw.flush();
		fw.close();
	}

	public void deploy(String name, File source, File docDestination,File resourceDir, String appserverCodebase) throws IOException {
		File overview = new File(docDestination,"sourcetree.txt");
		FileWriter fw = new FileWriter(overview);
		deploy(source, docDestination, new LinkedList<String>(),fw);
		fw.flush();
		fw.close();
		
		writeindex(name, docDestination);		
		File resourceOverview = new File(docDestination,"resourcetree.txt");
		fw = new FileWriter(resourceOverview);
		deployResource(resourceDir,fw,appserverCodebase, new LinkedList<String>());
		fw.flush();
		fw.close();

	}


	private void writeindex(String name, File docDestination) throws IOException {
		File info = new File(docDestination,"info.txt");
		FileWriter fw = new FileWriter(info);
		fw.write("==== Application: "+name+" ====\n");
		fw.write("[[sourcetree|Tipi source]]\n");
		fw.write("[[resourcetree|Tipi resources]]\n");
		fw.flush();
		fw.close();
	}

	public static void main(String[] args) throws IOException {
		TipiSourceDeployer ts = new TipiSourceDeployer();
		ts.deploy("Monkey",new File("/Users/frank/Documents/TipiPluginWorkspace/SportlinkClubStudio/tipi"), new File("/Users/frank/Documents/TipiPluginWorkspace/SportlinkClubStudio/wikidoc"),new File("/Users/frank/Documents/TipiPluginWorkspace/SportlinkClubStudio/resource"),"http://www.aap.com/");
	}
}
