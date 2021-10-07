package com.dexels.navajo.compiler.navascript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.mapping.compiler.meta.MapDefinitionInterrogatorImpl;

public class NSXMLToNS3 {

	public static void main(String [] args)  throws Exception {

		MapDefinitionInterrogatorImpl mdii = new MapDefinitionInterrogatorImpl();
		mdii.addExtensionDefinition("com.dexels.navajo.adapter.StandardAdapterLibrary");
		mdii.addExtensionDefinition("com.dexels.navajo.adapter.core.NavajoEnterpriseCoreAdapterLibrary");
		mdii.addExtensionDefinition("com.dexels.navajo.mongo.adapter.MongoAdapterLibrary");
		mdii.addExtensionDefinition("com.dexels.navajo.resource.http.bundle.ResourceAdapterLibrary");

		String argstate = "";
		String inputFile = null;
		String outputFile = null;

		if (args.length == 0)
		{
			System.out.println("Usage: java xmltons [-d] -i input [-o output] [-c config]");
			System.out.println();
			System.out.println();
			System.out.println("  input : a navascript or tsl script file");
			System.out.println("  output: a navascript 3 file");
			System.out.println("  Option:");
			System.out.println("    -d     debug");
		}

		for ( String arg : args ) {

			if ( arg.equals("-i") ) {
				argstate = "readinput";
			} else if ( arg.equals("-o")) {
				argstate = "writeoutput";
			} else if ( argstate.equals("readinput") ) {
				inputFile = arg;
			} else if ( argstate.equals("writeoutput")) {
				outputFile = arg;
			}
		}

		if ( inputFile == null) {
			System.out.println("No filename supplied");
			return;
		}

		try {

			FileInputStream fis = new FileInputStream(inputFile);
			NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis, mdii);

			OutputStream os = System.out;

			if ( outputFile != null ) {
				os = new FileOutputStream(outputFile);
			}


			navascript.formatNS3(0, os);
			os.close();
		} catch (Exception e) {
			System.err.println("Error transpiling " + inputFile + ": " + e.getMessage());
			System.exit(-1);
		}

	}
}

