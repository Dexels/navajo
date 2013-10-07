package com.dexels.navajo.tipi.dev.core.extensionmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLParseException;



public class XMLExtension  {

	
	private final static Logger logger = LoggerFactory
			.getLogger(XMLExtension.class);
	
	private final List<String> thirdPartyList = new ArrayList<String>();
	private final List<String> includes = new ArrayList<String>();
	private final List<String> requires = new ArrayList<String>();
	private final List<String> jars = new ArrayList<String>();
	private String id = null;
	private String description = null;
	private String project = null;
	
	public XMLExtension() {
	}
	
	protected void loadXML(File xmlName)  {
		try {
			InputStream is = new FileInputStream(xmlName);
			Reader r = new InputStreamReader(is);
			XMLElement xx = new CaseSensitiveXMLElement();
			xx.parseFromReader(r);
			r.close();
			is.close();
			loadXML(xx);
		} catch (XMLParseException e) {
			logger.error("Error: ",e);
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		
	}
	private void loadXML(XMLElement xx) {
		id = xx.getStringAttribute("id");
		project = xx.getStringAttribute("project");
		List<XMLElement> desc = xx.getElementsByTagName("description");
		if(desc!=null && !desc.isEmpty()) {
			description = desc.get(0).getContent();
		}
		List<XMLElement> includes = xx.getElementsByTagName("includes");
		if(includes!=null && !includes.isEmpty()) {
			XMLElement include = includes.get(0);
			for (XMLElement element : include.getChildren()) {
				this.includes.add(element.getStringAttribute("path"));
			}
		}
		
		List<XMLElement> requires = xx.getElementsByTagName("requires");
		if(requires!=null && !requires.isEmpty()) {
			XMLElement require = requires.get(0);
			for (XMLElement element : require.getChildren()) {
				this.requires.add(element.getStringAttribute("id"));
			}
		}
		List<XMLElement> deps = xx.getElementsByTagName("other");
		if(deps!=null && !deps.isEmpty()) {
			XMLElement dep = deps.get(0);
			for (XMLElement element : dep.getChildren()) {
				this.thirdPartyList.add(element.getStringAttribute("href"));
			}
		}
		
		List<XMLElement> jarList = xx.getElementsByTagName("jars");
		if(requires!=null && !jarList.isEmpty()) {
			XMLElement jarr = jarList.get(0);
			for (XMLElement element : jarr.getChildren()) {
				this.jars.add(element.getStringAttribute("path"));
			}
		}
	}


	@Deprecated
	public final String getConnectorId() {
		return null;
	}

	public final List<String> getDependingProjectUrls() {
		return thirdPartyList;
	}

	@Deprecated
	public final String getDeploymentDescriptor() {
		return null;
	}

	public final String getDescription() {
		return description;
	}

	public final String getId() {
		return id;
	}

	public final String[] getIncludes() {
		String[] in = new String[includes.size()];
		for (int i = 0; i < in.length; i++) {
			in[i] = includes.get(i);
		}
		return in;
	}


	public final List<String> getJars() {
		return jars;
	}

	public final String getProjectName() {
		return project;
	}

	public final List<String> getRequiredExtensions() {
		return requires;
	}



	public final boolean isMainImplementation() {
		logger.info("Need to implement main detection");
		return false;
	}

	public final  String requiresMainImplementation() {
		return null;
	}

}
