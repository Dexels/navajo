package com.dexels.navajo.tipi.dev.core.extensionmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dexels.navajo.tipi.dev.core.util.XMLElement;

public abstract class ExtensionClassdefProcessor {

	private File outputDir;
	private File distributionDir;
	private String deployRepository;
	
	public String getDeployRepository() {
		return deployRepository;
	}

	public void setDeployRepository(String deployRepository) {
		this.deployRepository = deployRepository;
	}

	public File getDistributionDir() {
		return distributionDir;
	}

	public void setDistributionDir(File distributionDir) {
		this.distributionDir = distributionDir;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	protected abstract void processTipiContext(URL repository, String extension, String version, List<String> extensions, Map<String, XMLElement> allComponents, Map<String, XMLElement> allActions,
			Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues, Map<String, XMLElement> allTypes,
			Map<String, XMLElement> allFunctions) ;

	public void execute(URL repository, String originalExtension, String version, Map<String,List<XMLElement>> classDefElements) {
		Map<String, XMLElement> allComponents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allActions = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allEvents = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allValues = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allTypes = new HashMap<String, XMLElement>();
		Map<String, XMLElement> allFunctions = new HashMap<String, XMLElement>();

	
		List<String> extensions = new ArrayList<String>();
		extensions.addAll(classDefElements.keySet());
		parseStream(classDefElements, allComponents, allActions, allEvents, allValues, allTypes, allFunctions);
		processTipiContext(repository, originalExtension, version, extensions, allComponents, allActions, allEvents, allValues, allTypes, allFunctions);
		
	}
 
	private void parseStream(Map<String,List<XMLElement>> classDefElements, Map<String, XMLElement> allComponents,
			Map<String, XMLElement> allActions, Map<String, XMLElement> allEvents, Map<String, XMLElement> allValues,
			Map<String, XMLElement> allTypes, Map<String, XMLElement> allFunctions)
			 {

		
		for (Entry<String, List<XMLElement>> entry : classDefElements.entrySet()) {
			String extension = entry.getKey();
			List<XMLElement> list = entry.getValue();
			
			for (XMLElement xe : list) {
				
			
			List<XMLElement> c = xe.getChildren();
			for (Iterator<XMLElement> iter = c.iterator(); iter.hasNext();) {
				XMLElement element = iter.next();
				if (element.getName().equals("tipiclass")) {
					allComponents.put(element.getStringAttribute("name"), element);
					element.setAttribute("extension", extension);
					processClass(element, allEvents, allValues);
					continue;
				}
				if (element.getName().equals("tipiaction")) {
					allActions.put(element.getStringAttribute("name"), element);
					element.setAttribute("extension", extension);
					// Process actions
					continue;
				}
				if (element.getName().equals("tipi-include")) {
//					String location = element.getStringAttribute("location");
					//logger.info("Ignoring include: " + element);

					continue;
				}
				if (element.getName().equals("tipi-parser")) {
					allTypes.put(element.getStringAttribute("name"), element);
					element.setAttribute("extension", extension);
					continue;
				}

				if (element.getName().equals("function")) {
					allFunctions.put(element.getStringAttribute("name"), element);
					element.setAttribute("extension", extension);
					continue;
				}

			}
			}
		}
		
		
		
	}

	private static void processClass(XMLElement componentElement, Map<String, XMLElement> allEvents,
			Map<String, XMLElement> allValues) {
		List<XMLElement> c = componentElement.getChildren();
		for (Iterator<XMLElement> iter = c.iterator(); iter.hasNext();) {
			XMLElement cc = iter.next();
			if (cc.getName().equals("events")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = iter2.next();
					if (eventElement.getName().equals("event")) {
						allEvents.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
			if (cc.getName().equals("values")) {
				List<XMLElement> ccc = cc.getChildren();
				for (Iterator<XMLElement> iter2 = ccc.iterator(); iter2.hasNext();) {
					XMLElement eventElement = iter2.next();
					if (eventElement.getName().equals("value")) {
						allValues.put(eventElement.getStringAttribute("name"), eventElement);
					}
				}
			}
		}
	}
	
	protected OutputStream writeResource(String path) throws FileNotFoundException {
		getOutputDir().mkdirs();
		File pathFile = new File(getOutputDir(),path);
		pathFile.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(pathFile);
		return fos;
	}

}
