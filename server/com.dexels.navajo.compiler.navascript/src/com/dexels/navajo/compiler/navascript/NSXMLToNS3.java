package com.dexels.navajo.compiler.navascript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;

public class NSXMLToNS3 {

	public static void main(String [] args)  throws Exception {
		
		String argstate = "";
		String inputFile = null;
		String outputFile = null;
		
		if (args.length == 0)
	    {
	      System.out.println("Usage: java xmltons [-d] -i input [-o output]");
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
		
		FileInputStream fis = new FileInputStream(inputFile);
		NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis, new MapDefinitionInterrogatorImpl());
		
		OutputStream os = System.out;
		
		if ( outputFile != null ) {
			os = new FileOutputStream(outputFile);
		}
		
		navascript.formatNS3(0, os);
		os.close();
		
	}
}

