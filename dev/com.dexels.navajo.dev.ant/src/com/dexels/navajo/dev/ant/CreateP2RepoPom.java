package com.dexels.navajo.dev.ant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.dexels.navajo.dev.ant.feature.CaseSensitiveXMLElement;
import com.dexels.navajo.dev.ant.feature.XMLElement;

public class CreateP2RepoPom extends Task {

	private List<String> input;
	private String template;
	private XMLElement xe;
	private String destination;
	private String version;
	private String id;
	private boolean transitive = true;
	private final Set<String> groups = new HashSet<>();
	@Override
	public void execute() {
		File templateFile = new File(template);
		xe = new CaseSensitiveXMLElement(false);
		Map<Object,Object> p = new HashMap<>();

		try {
			try(Reader r = new FileReader(templateFile)) {
				xe.parseFromReader(r);
			}
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
		XMLElement featureDefs = xe.getAllElementsByTagName("featureDefinitions").stream().findFirst().get();


		updateVersionTags(featureDefs.getChildByTagName("feature"),id,version);
		XMLElement artifactList = featureDefs.getChildByTagName("feature").getChildByTagName("artifacts");
		
		List<String> lines =Arrays.asList(bootrepo.split(",")).stream().sorted().collect(Collectors.toList());
		for (String line : lines) {
			String[] proto = line.split(":");
			if(!proto[0].equals("mvn")) {
				throw new IllegalArgumentException("Only mvn url are supported, not: "+proto[0]);
			}
			String[] parts = proto[1].split("/");
			String groupId = parts[0];
			String artifactId = parts[1];
			String artifactVersion = parts[2];
			Optional<String> type = parts.length>3? Optional.of(parts[3]) : Optional.empty();
			Optional<String> classifier = parts.length>4? Optional.of(parts[4]) : Optional.empty();
			if(!type.isPresent() || "jar".equals(type.get())) {
				artifactList.addChild(createArtifact(groupId,artifactId,artifactVersion,type,classifier));
			}
		}

	}
	
//	<feature> <!-- Generate a feature including artifacts that are listed below inside the feature element-->
//  <id>spring.feature</id>
//  <version>4.3.11</version>
//  <label>Spring Framework 4.3.11 Feature</label>
//  <providerName>A provider</providerName>
//  <description>${project.description}</description>
//  <copyright>A copyright</copyright>
//  <license>A licence</license>
	
	private void updateVersionTags(XMLElement featureTag, String id, String version) {
		featureTag.getChildByTagName("id").setContent(id);
		featureTag.getChildByTagName("version").setContent(version);
	}

	// TODO classifier?
	private XMLElement createArtifact(String groupId, String artifactId, String version, Optional<String> type, Optional<String> classifier) {
//        <artifact>
//        	<id>org.springframework:spring-core:jar:4.3.11.RELEASE</id>
//        </artifact>
		XMLElement artifact = new CaseSensitiveXMLElement("artifact");
		String uri = groupId+":"+artifactId+":"+version;
		if(type.isPresent()) {
			uri = uri+":"+type.get();
		}
		artifact.addTagKeyValue("id", uri);
		if(!transitive) {
			artifact.addTagKeyValue("transitive", "false");
		}
		return artifact;

	}

	public void setVersion(String version) {
		this.version = version;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setTransitive(boolean transitive) {
		this.transitive = transitive;
	}
	
	public void setBundles(String input) {
		this.input = Arrays.asList(input.split(","));
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
		CreateP2RepoPom ad = new CreateP2RepoPom();
		ad.setBundles("core.cfg");
		ad.setTemplate("pom-create-targetplatform-template.xml");
		ad.setDestination("test-pom");
		ad.setId("myfeatureid");
		ad.setVersion("1.2.3.4");
		ad.execute();
	}
}
