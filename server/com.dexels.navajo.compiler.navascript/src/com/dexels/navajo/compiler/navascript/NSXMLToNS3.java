package com.dexels.navajo.compiler.navascript;

import java.io.FileInputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;

public class NSXMLToNS3 {

	public static void main(String [] args)  throws Exception {
		
		FileInputStream fis = new FileInputStream("/Users/arjenschoneveld/MappedArray.xml");
		NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis);
		navascript.setMapChecker(new MapDefinitionInterrogatorImpl());
		
		navascript.formatNS3(0, System.err);
	}
}

