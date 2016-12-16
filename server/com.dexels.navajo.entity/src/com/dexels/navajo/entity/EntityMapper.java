package com.dexels.navajo.entity;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.NavajoConfigInterface;

public class EntityMapper {
	private final static Logger logger = LoggerFactory.getLogger(EntityMapper.class);

	private NavajoConfigInterface navajoConfig;
	private Map<String, Set<String>> mappings = new HashMap<>();

	public void activate() {
		processMappings();
	}

	public Set<String> getEntities(String path) {
		if (!mappings.containsKey(path)) {
			return new HashSet<>();
		}
		return mappings.get(path);
	}

	private void processMappings() {
		String scriptPath = navajoConfig.getScriptPath();
		File entityDir = new File(scriptPath + File.separator + "entity");
		if (!entityDir.exists()) {
			return;
		}

		processMappings(entityDir);
	}

	// Can be called on file or directory. If on directory, call recursively on
	// each file
	private void processMappings(File file) {
		if (file.isFile()) {
			String filename = file.toString();
			if (filename.endsWith("entitymapping.xml")) {
				processMapping(file);
			}
		} else if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				processMappings(f);
			}
		}
	}

	private void processMapping(File file) {
		File parentFolder = file.getParentFile();

		String folder;
		if (parentFolder.equals(new File(navajoConfig.getScriptPath() + File.separator + "entity"))) {
			folder = ""; // Root folder
		} else {
			String folderString = parentFolder.toString();
			String pattern = Pattern.quote("scripts" + File.separator + "entity");
			folder = folderString.split(pattern)[1];
		}

		Set<String> existing = mappings.get(folder);
		if (existing == null) {
			existing = new HashSet<>();
			mappings.put(folder, existing);
		}
		try (FileReader fr = new FileReader(file);) {
			Navajo n = NavajoFactory.getInstance().createNavajo(fr);
			for (Message m : n.getMessage("entities").getElements()) {
				String entityPath = m.getProperty("entity").getValue();
				entityPath = entityPath.replace('/', '.');
				existing.add(entityPath);
			}
		} catch (IOException e) {
			logger.error("IOException on reading entitymapping {}", file.toString(), e);
		}
	}

	public void setNavajoConfig(NavajoConfigInterface nci) {
		logger.debug("Setting NavajoConfig");
		this.navajoConfig = nci;
	}

	public void clearNavajoConfig(NavajoConfigInterface nci) {
		logger.debug("Clearing NavajoConfig");
		this.navajoConfig = null;
	}

}
