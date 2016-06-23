package com.dexels.navajo.dev.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.dexels.navajo.dev.ant.feature.CaseSensitiveXMLElement;
import com.dexels.navajo.dev.ant.feature.XMLElement;

public class AddDependenciesToPom extends Task {

	private String input;
	private String template;
	private XMLElement xe;
	private String destination;
	private final Set<String> groups = new HashSet<>();
	@Override
	public void execute() throws BuildException {
		File templateFile = new File(template);
		xe = new CaseSensitiveXMLElement(false);
		try {
			try(Reader r = new FileReader(templateFile)) {
				xe.parseFromReader(r);
			}
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
					addDependencies((String) e.getValue());
				} else {
					if(groups.contains(parts[1])) {
						addDependencies((String) e.getValue());
					} else {
						log("Ignoring module: "+name+" it is not in groups: "+this.groups);
					}
				}
				if(name.startsWith("core")) {
				}

			}
//			String bootrepo = p.getProperty("bootrepo");
//			log("pom:\n"+xe);
			File output = new File(destination);
			try(Writer w = new FileWriter(output)) {
				xe.write(w);
			}
		} catch (Throwable e) {
			log("Error creating pom",e,4);
			throw new BuildException("Error creating pom",e);
		}
	}

	private void addDependencies(String bootrepo) {
		log("Added dependency: "+bootrepo);
		if(bootrepo==null) {
			return;
		}
		XMLElement deps = xe.getChildByTagName("dependencies");
		String[] lines = bootrepo.split(",");
		for (String line : lines) {
//			System.err.println("line: "+line);
			String[] proto = line.split(":");
			if(!proto[0].equals("mvn")) {
				throw new IllegalArgumentException("Only mvn url are supported, not: "+proto[0]);
			}
			String[] parts = proto[1].split("/");
			String type = parts.length>3? parts[3] : "jar";
			String classifier = parts.length>4? parts[4] : null;
			deps.addChild(appendDependency(parts[0],parts[1],parts[2],type,classifier));
//			System.err.println("PRO: "+proto[1]);
		}

	}

	private XMLElement appendDependency(String groupId, String artifactId, String version,String type, String classifier) {
		XMLElement dep = new CaseSensitiveXMLElement("dependency");

		dep.addTagKeyValue("groupId", groupId);
		dep.addTagKeyValue("artifactId", artifactId);
		dep.addTagKeyValue("version", version);
		if(classifier!=null) {
			dep.addTagKeyValue("classifier", classifier);
		}
		if(type!=null && !"jar".equals(type) && !"".equals(type)) {
			dep.addTagKeyValue("type", type);
		}

		return dep;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setBundles(String input) {
		this.input = input;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setGroups(String groups) {
		this.groups.clear();
		for (String part : groups.split(",")) {
			this.groups.add(part);
		}
	}

	public static void main(String[] args) {
		AddDependenciesToPom ad = new AddDependenciesToPom();
		ad.setBundles("dexels.bundles.cfg");
		ad.setTemplate("pom-template.xml");
		ad.execute();
	}
}
