package com.dexels.navajo.maven;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;
import com.dexels.navajo.tipi.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.util.XMLElement;

public class CreateMavenFromJar {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {

		CreateMavenFromJar j = new CreateMavenFromJar();
		j.createMavenFromJar("tipi", "core", new URL("http://repo2.maven.org/maven2/ant/ant-jai/1.6.2/ant-jai-1.6.2.jar"), new File("/Users/frank/maventest"), "1.3.1");
	}

	
	public void createMavenFromJar(String groupId, String projectId, URL jarFile, File destination, String version) throws IOException {
		System.err.println("Building: "+groupId+"/"+projectId);
		File groupFolder = new File(destination, groupId);
		File projectFolder = new File(groupFolder, projectId);
		File versionFolder = new File(projectFolder, version);
		versionFolder.mkdirs();
		createMetadata(groupId, projectId, projectFolder,version,true);
		createMetadata(groupId, projectId, versionFolder,version,false);
		ClientActions.downloadFile(jarFile, projectId+"-"+version+".jar", versionFolder, false,false);

		createPom(groupId, projectId, versionFolder,version);
		
	}


	private void createPom(String groupId, String projectId, File versionFolder, String version) throws IOException {
		File pomFile = new File(versionFolder,projectId+"-"+version+".pom");
		if(!pomFile.exists()) {
			System.err.println("Skipping existing pom!");
			return;
		}
		XMLElement project = new CaseSensitiveXMLElement();
		project.setName("project");
		project.addTagKeyValue("modelVersion","4.0.0");
		project.addTagKeyValue("groupId",groupId);
		project.addTagKeyValue("artifactId",projectId);
		project.addTagKeyValue("version",version);		
		FileWriter fw = new FileWriter(pomFile);
		project.write(fw);
		fw.close();
	}


	private void createMetadata(String groupId, String projectId, File projectFolder, String version, boolean addVersioning) throws IOException {
		XMLElement metadata = new CaseSensitiveXMLElement();
		metadata.setName("metadata");
		metadata.addTagKeyValue("groupId",groupId);
		metadata.addTagKeyValue("artifactId",projectId);
		metadata.addTagKeyValue("version",version);
		if(addVersioning) {
			XMLElement versioning = metadata.addTagKeyValue("versioning","");
			versioning.addTagKeyValue("version", version);			
		}
		
		File metadataFile = new File(projectFolder,"maven-metadata.xml");
		FileWriter fw = new FileWriter(metadataFile);
		metadata.write(fw);
		fw.flush();
		fw.close();
	}
}
