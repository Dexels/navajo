package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dexels.navajo.tipi.dev.core.util.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLElement;
import com.dexels.navajo.tipi.dev.core.util.XMLParseException;

public class JnlpDeployer {
	public void deploy(File jnlpFile, File destination, String codebase) throws XMLParseException, IOException {
		FileReader fr = new FileReader(jnlpFile);
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(fr);
		fr.close();
		String destinationRef = destination.getName();
		xe.setAttribute("href",destinationRef);
		xe.setAttribute("codebase",codebase);
		FileWriter fw = new FileWriter(destination);
		xe.write(fw);
		fw.flush();
		fw.close();
	}
}
