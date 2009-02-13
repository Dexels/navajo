package com.dexels.navajo.tipi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

public class TipiBuildXsd extends BaseTipiClientTask {

	@Override
	public void execute() throws BuildException {
		File xsd = new File("tipi/tipi.xsd");
		xsd.getParentFile().mkdirs();
		
		if(extensions==null || "".equals(extensions)) {
			
			throw new BuildException("No extensions defined ");
		}
		StringTokenizer st = new StringTokenizer(extensions,",");
		while(st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
				appendExtension(ext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void parseProjectDefinition(URL projectURL, XMLElement result) {
		List<XMLElement> includes = result.getElementsByTagName("include");
		
	}


}