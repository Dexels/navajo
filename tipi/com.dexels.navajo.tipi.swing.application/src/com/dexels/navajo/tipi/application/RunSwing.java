package com.dexels.navajo.tipi.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiCoreExtension;

import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class RunSwing {

	
	private final static Logger logger = LoggerFactory
			.getLogger(RunSwing.class);
	/**
	 * FOR NON-OSGi
	 * @param args 
	 * @throws IOException 
	 * @throws XMLParseException 
	 */
	public static void main(String[] args) throws XMLParseException, IOException {
		TipiCoreExtension tce = new TipiCoreExtension();
		tce.loadDescriptor();
		tce.getTipiExtensionRegistry().registerTipiExtension(tce);
		final String path = System.getProperty("tipi.path");
		final String deploy = System.getProperty("tipi.deploy");
		final String profile = System.getProperty("tipi.profile");
		logger.info("Starting application. tipi.path: "+path+" deploy: "+deploy+" profile: "+profile);
		//		tse.getTipiExtensionRegistry().registerTipiExtension(tse);
		ApplicationComponent ac = new ApplicationComponent();
//		"/Users/frank/Documents/workspace42/SportlinkClub"
		ac.bootApplication(null, path, deploy, profile);
	}

}
