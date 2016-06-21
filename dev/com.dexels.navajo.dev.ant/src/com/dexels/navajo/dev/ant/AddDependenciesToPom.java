package com.dexels.navajo.dev.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.dexels.navajo.dev.ant.feature.CaseSensitiveXMLElement;
import com.dexels.navajo.dev.ant.feature.XMLElement;

public class AddDependenciesToPom extends Task {

	private String input;
	private String template;
	private XMLElement xe;
	private String destination;

	@Override
	public void execute() throws BuildException {
		this.log("AddDependenciesToPom..."+input+" >> "+template+" >>> "+destination);
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
//				String name = e.getKey();
				addDependencies((String) e.getValue());

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
		if(bootrepo==null) {
			return;
		}
		XMLElement deps = xe.getChildByTagName("dependencies");
		String[] lines = bootrepo.split(",");
		for (String line : lines) {
			System.err.println("line: "+line);
			String[] proto = line.split(":");
			if(!proto[0].equals("mvn")) {
				throw new IllegalArgumentException("Only mvn url are supported, not: "+proto[0]);
			}
			String[] parts = proto[1].split("/");
			deps.addChild(appendDependency(parts[0],parts[1],parts[2]));
			System.err.println("PRO: "+proto[1]);
		}

	}

	private XMLElement appendDependency(String groupId, String artifactId, String version) {
		XMLElement dep = new CaseSensitiveXMLElement("dependency");
		dep.addTagKeyValue("groupId", groupId);
		dep.addTagKeyValue("artifactId", artifactId);
		dep.addTagKeyValue("version", version);
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

	
	public static void main(String[] args) {
		AddDependenciesToPom ad = new AddDependenciesToPom();
		ad.setBundles("dexels.bundles.cfg");
		ad.setTemplate("pom-template.xml");
		ad.execute();
	}
}
