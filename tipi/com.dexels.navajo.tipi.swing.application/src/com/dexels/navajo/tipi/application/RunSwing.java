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
		// enableFocusLogging(); // outputs all focus statements to log. Helpful in determining what focus is doing.
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
	
	// Copied from web and didn't want to bother about finding the correct logger implmentation.
	private static void enableFocusLogging()
	{
	      // Obtain a reference to the logger
        java.util.logging.Logger focusLog = java.util.logging.Logger.getLogger("java.awt.focus.Component");
        // The logger should log all messages
        focusLog.setLevel(java.util.logging.Level.ALL);
        // Create a new handler
        java.util.logging.ConsoleHandler handler = new java.util.logging.ConsoleHandler();
        // The handler must handle all messages
        handler.setLevel(java.util.logging.Level.ALL);
        // Add the handler to the logger
        focusLog.addHandler(handler);
	}

}
