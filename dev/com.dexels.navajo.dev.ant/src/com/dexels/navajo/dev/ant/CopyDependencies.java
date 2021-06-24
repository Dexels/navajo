/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CopyDependencies extends Task {

	private List<String> input;
	private String destination;
	private final Set<String> groups = new HashSet<>();
	@Override
	public void execute() throws BuildException {
		Map<Object,Object> p = new HashMap<>();
		try {
			for (String inputFile : input) {
				File bundleFile = new File(inputFile);
				Properties prop = new Properties();
				try(FileInputStream fis = new FileInputStream(bundleFile)) {
					prop.load(fis);
				}
				p.putAll(prop);
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
//			String classifier = parts.length>3? parts[3] : null;
			String type = parts.length>3? parts[3] : "jar";
			if("".equals(type)) {
				type = "jar";
			}
			String classifier = parts.length>4? parts[4] : null;
			copyDependency(parts[0],parts[1],parts[2],type,classifier);
		}

	}

	
	private void copyDependency(String group, String artifact, String version, String type, String classifier) throws IOException {
		Path repo = Paths.get(System.getProperty("user.home"), ".m2","repository");
		String[] groupParts = group.split("\\.");
		Path resolved = repo;
		for (String part : groupParts) {
			resolved = resolved.resolve(part);
		}
		resolved = resolved.resolve(artifact);
		resolved = resolved.resolve(version);
		if (classifier==null) {
			resolved = resolved.resolve(artifact+"-"+version+"."+type);
		} else {
			resolved = resolved.resolve(artifact+"-"+version+"-"+classifier+"."+type);
		}
		Path dest = Paths.get(destination).resolve(artifact+"-"+version+"."+type);
		
		Files.copy(resolved, dest);
		
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setBundles(String input) {
		this.input = Arrays.asList(input.split(","));
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
