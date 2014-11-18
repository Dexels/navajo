package com.dexels.navajo.dependency;

/**
 * <p>Title: Navajo Product Project</p>"
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$get
 */

/**
 *
 * $columnValue('AAP') -> Object o = contextMap.getColumnValue('AAP') -> symbolTable.put("$columnValue('AAP')", o);
 *laz
 *
 *
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.MapMetaData;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslPreCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private final static Logger logger = LoggerFactory.getLogger(TslPreCompiler.class);

	
	public TslPreCompiler(NavajoIOConfig config) {
		navajoIOConfig = config;
	}

	public void getIncludeDependencies(String script, String scriptPath, String workingPath,
			List<Dependency> deps, String scriptTenant) throws Exception {
		
		final String extension = ".xml";
		String fullScriptPath = null;
		Document tslDoc = null;
		InputStream is = null;
		
		if (scriptTenant != null) {
			fullScriptPath = scriptPath + "/" + script + "_" + scriptTenant + extension;
		} else {
			fullScriptPath = scriptPath + "/" + script + extension;
		}

		try {
			// Check for metascript.
			if (MapMetaData.isMetaScript(fullScriptPath)) {
				MapMetaData mmd = MapMetaData.getInstance();
				InputStream metais = navajoIOConfig.getScript(script, scriptTenant, extension);

				String intermed = mmd.parse(fullScriptPath, metais);
				metais.close();
				is = new ByteArrayInputStream(intermed.getBytes());
			} else {
				is = navajoIOConfig.getScript(script, scriptTenant, extension);
			}
			
		} catch (Exception e) {
			logger.error("Pre-compiler failed!", e);
			throw e;
		}
		
		tslDoc = XMLDocumentUtils.createDocument(is, false);
		NodeList includes = tslDoc.getElementsByTagName("include");
		int included = 0;
		for (int i = 0; i < includes.getLength(); i++) {
			findIncludeDependencies(includes.item(i), deps, scriptPath);
			included++;

			if (included > 1000) {
				throw new UserException(-1, "Too many included scripts!!!");
			}
		}

	}
	
	private void findIncludeDependencies(Node n, List<Dependency> deps, String scriptPath) throws UserException {
		String script = ((Element) n).getAttribute("script");
		if (script == null || script.equals("")) {
			throw new UserException(-1, 
					"No script name found in include tag (missing or empty script attribute): " + n);
		}
		File scriptPathFile = new File(scriptPath, FilenameUtils.getPath(script));
		
		
		// Going to check for tenant-specific include-variants
		 AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getName(script)+"*.xml");
		 Collection<File> files = FileUtils.listFiles(scriptPathFile, fileFilter, null);
		 for (File f : files) {
			 String includeScriptPath = f.getAbsolutePath().substring(scriptPath.length());
			 String includeScript = includeScriptPath.substring(1, includeScriptPath.indexOf(".xml"));
			 deps.add(new IncludeDependency(IncludeDependency.getScriptTimeStamp(includeScript), script, includeScript));
		 }
		 

	}
	
	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}

}