package com.dexels.navajo.compiler.navascript;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.navascript.tags.NavascriptTag;
import com.dexels.navajo.mapping.compiler.meta.MapDefinitionInterrogatorImpl;

public class Tester {

	public static void main(String [] args)  {

		try {
			MapDefinitionInterrogatorImpl mdii = new MapDefinitionInterrogatorImpl();
			mdii.addExtentionDefinition("com.dexels.navajo.adapter.StandardAdapterLibrary");
			mdii.addExtentionDefinition("com.dexels.navajo.adapter.core.NavajoEnterpriseCoreAdapterLibrary");
			mdii.addExtentionDefinition("com.dexels.navajo.mongo.adapter.MongoAdapterLibrary");
			mdii.addExtentionDefinition("com.dexels.sportlink.adapters.SportlinkAdapterDefinitions");
			mdii.addExtentionDefinition("com.dexels.navajo.resource.http.bundle.ResourceAdapterLibrary");


			FileInputStream fis = new FileInputStream("/Users/arjenschoneveld/ProcessQuerySchedule.xml");
			NavascriptTag navascript = (NavascriptTag)  NavajoFactory.getInstance().createNavaScript(fis, mdii);

			OutputStream os = System.out;
			navascript.formatNS3(0, os);
			os.close();
			
		} catch (Exception e) {
			System.err.print(e.getLocalizedMessage());
			System.exit(-1);
		}

	}
}

