package com.dexels.navajo.adapter.descriptionprovider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class DescriptionCacheMap implements Mappable {

	public boolean dumpCache = false;
	private Access myAccess;
	private NavajoConfig myConfig;
	public boolean doFlushCache = false;
//	public String doFlushUser = null;
	public int cacheSize = 0;
	public String parseResource = null;

	public String locale = null;
	private String username = null;
	
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
		myAccess = access;
		username = inMessage.getHeader().getRPCUser();
		myConfig = config;
	}

//	public void setDumpCache(boolean b) {
//		try {
//			myAccess.getOutputDoc().addMessage(myConfig.getDescriptionProvider().dumpCacheMessage(myAccess.getOutputDoc()));
//			
//		} catch (NavajoException e) {
//			e.printStackTrace();
//		}
//	}
//	
	public void store() throws MappableException, UserException {
		// l

	}
	public int getCacheSize() {
		if (myConfig.getDescriptionProvider()!=null) {
			return myConfig.getDescriptionProvider().getCacheSize();
		}
		return 0;
	}


	public void setDoFlushCache(boolean doFlushCache) {
		if (myConfig.getDescriptionProvider()!=null) {
			myConfig.getDescriptionProvider().flushCache();
		}
	}
	
	public void setParseResource(String name) {
		String path = myConfig.getResourcePath();
		if (path == null) {
			throw new IllegalStateException("No resource path found. Define paths/resource in the server.xml file."); 
		}
		if (myConfig.getDescriptionProvider()==null) {
			throw new IllegalStateException("No descriptionprovider found"); 
		}
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File langdir = new File(dir,locale);
		if (!langdir.exists()) {
			langdir.mkdirs();
		}
		
		File ff = new File(langdir, name+".properties");
		if (!ff.exists()) {
			throw new IllegalStateException("File not found: "+ff.getAbsolutePath()); 
		}
		try {
			parseResource(ff,name,locale);
		} catch (IOException e) {
			throw new IllegalStateException("File not found: "+ff.getAbsolutePath(),e); 
		}
	}

	private void parseResource(File ff,String context,String locale) throws IOException {
		myConfig.getDescriptionProvider().deletePropertyContext(locale,context);
		BufferedReader br = new BufferedReader(new FileReader(ff));
		String line = null;
		while ((line = br.readLine()) != null) {
			int sp = line.indexOf(' ');
			if (sp<1) {
				System.err.println("Fuck: "+line);
			}
			String key = line.substring(0,sp);
			String value = line.substring(sp+1,line.length());
//			System.err.println("KEY: >"+key+"<");
//			System.err.println("VALUE: >"+value+"<");
			myConfig.getDescriptionProvider().updateDescription(locale, key,value,context,username);
		}
		
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
