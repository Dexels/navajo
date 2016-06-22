package com.dexels.navajo.dev.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CopyDependencies extends Task {

	private String input;
	private String destination;
	private final Set<String> groups = new HashSet<>();
	@Override
	public void execute() throws BuildException {
		try {
			File bundleFile = new File(input);
			Properties p = new Properties();
			try(FileInputStream fis = new FileInputStream(bundleFile)) {
				p.load(fis);
			}
			
			for (java.util.Map.Entry<Object, Object> e : p.entrySet()) {
				String name = (String) e.getKey();
				log("Parsing: "+name);
				String[] parts = name.split("\\.");
				if(parts.length==1) {
					copyFiles((String) e.getValue());
				} else {
					if(groups.contains(parts[1])) {
						copyFiles((String) e.getValue());
					} else {
						log("Ignoring module: "+name+" it is not in groups: "+this.groups);
					}
				}
				if(name.startsWith("core")) {
				}

			}
//			String bootrepo = p.getProperty("bootrepo");
//			log("pom:\n"+xe);

		} catch (Throwable e) {
			log("Error creating pom",e,4);
			throw new BuildException("Error creating pom",e);
		}
	}

	private void copyFiles(String bootrepo) throws IOException {
		log("Added dependency: "+bootrepo);
		if(bootrepo==null) {
			return;
		}
		String[] lines = bootrepo.split(",");
		for (String line : lines) {
			String[] proto = line.split(":");
			if(!proto[0].equals("mvn")) {
				throw new IllegalArgumentException("Only mvn url are supported, not: "+proto[0]);
			}
			String[] parts = proto[1].split("/");
			String classifier = parts.length>3? parts[3] : null;
			copyDependency(parts[0],parts[1],parts[2],classifier);
//			System.err.println("PRO: "+proto[1]);
		}

	}

	
	private void copyDependency(String group, String artifact, String version, String classifier) throws IOException {
		Path repo = Paths.get(System.getProperty("user.home"), ".m2","repository");
		String[] groupParts = group.split("\\.");
		Path resolved = repo;
		for (String part : groupParts) {
			resolved = resolved.resolve(part);
		}
		resolved = resolved.resolve(artifact);
		resolved = resolved.resolve(version);
		if (classifier==null) {
			resolved = resolved.resolve(artifact+"-"+version+".jar");
		} else {
			resolved = resolved.resolve(artifact+"-"+version+"-"+classifier+".jar");
		}
		Path dest = Paths.get(destination).resolve(artifact+"-"+version+".jar");
		
		System.err.println("Copying from: "+resolved.toString()+" to "+dest);
		Files.copy(resolved, dest);
		
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setBundles(String input) {
		this.input = input;
	}


	public void setGroups(String groups) {
		this.groups.clear();
		for (String part : groups.split(",")) {
			this.groups.add(part);
		}
	}

	public static void main(String[] args) {
		
	}
}
