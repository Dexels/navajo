package com.dexels.navajo.tipi.ant.projectbuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;


public class TipiDownloadJarTask extends BaseTipiClientTask {
	public void execute() throws BuildException {
		if (extensions == null || "".equals(extensions)) {
			throw new BuildException("No extensions defined ");
		}
		List<String> missing = ClientActions.checkExtensions(repository,extensions);
		if(!missing.isEmpty()) {
			throw new BuildException("Requested extension(s) not available on repository: "+missing);
		}
		StringTokenizer st = new StringTokenizer(extensions, ",");
		while (st.hasMoreTokens()) {
			String ext = st.nextToken();
			try {
//				ClientActions.
//				URL rep = new URL(repository);
//				URL projectURL = new URL(rep,ext+"/");
				//URL extensionURL = new URL(projectURL,"definition.xml");
				//XMLElement result = ClientActions.getXMLElement(extensionURL);
				ClientActions.downloadExtensionJars(ext,new URL(repository+ext+"/"),ClientActions.getExtensionXml(ext, repository));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
