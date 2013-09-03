package com.dexels.navajo.tipi.dev.ant.projectbuilder;

import java.util.List;

import org.apache.tools.ant.BuildException;

import com.dexels.navajo.tipi.dev.core.projectbuilder.ClientActions;


public class TipiDownloadJarTask extends BaseTipiClientTask {
	@Override
	public void execute() throws BuildException {
		if (extensions == null || "".equals(extensions)) {
			throw new BuildException("No extensions defined ");
		}
		List<String> missing = ClientActions.checkExtensions(repository,extensions);
		if(!missing.isEmpty()) {
			throw new BuildException("Requested extension(s) not available on repository: "+missing);
		}

	}
}
