package com.dexels.navajo.tipi.headless;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiScreen;
import com.dexels.navajo.tipi.internal.FileResourceLoader;

public class HeadlessApplicationInstance {

	
	private final static Logger logger = LoggerFactory
			.getLogger(HeadlessApplicationInstance.class);

	public static void main(String[] args) throws Exception {
		List<TipiExtension> ll = new ArrayList<TipiExtension>();
		ll.add(new TipiCoreExtension());
		// Map<String, String> properties = parseProperties(args);
		// initialize("init", "init.xml", properties);
		initialize("init", new File("."), ll );
		Thread.sleep(2000);
	}

	public static TipiContext initialize(String definition, File tipiDir,
			List<TipiExtension> ed) throws Exception {
		return initialize(definition, definition + ".xml", tipiDir,
				parseProperties(new String[] {}), ed);
	}

	public static TipiContext initialize(String definition, File tipiDir,
			String[] args, List<TipiExtension> ed) throws Exception {
		return initialize(definition, definition + ".xml", tipiDir,
				parseProperties(args), ed);
	}

	public static TipiContext initialize(String definition,
			String definitionPath, File tipiDir,
			Map<String, String> properties, List<TipiExtension> ed) throws Exception {
		if (definitionPath == null) {
			definitionPath = definition;
		}
		TipiContext context = null;
		// System.setProperty("com.dexels.navajo.tipi.maxthreads","0");
		context = new HeadlessTipiContext(ed);
//		for (TipiExtension tipiExtension : ed) {
//			context.processRequiredIncludes(tipiExtension);
//			tipiExtension.initialize(context);
//		}
		FileResourceLoader frl = new FileResourceLoader(tipiDir);
		context.setTipiResourceLoader(frl);
		context.setDefaultTopLevel(new TipiScreen(context));
		context.processProperties(properties);
		InputStream tipiResourceStream = context
				.getTipiResourceStream(definitionPath);
		if (tipiResourceStream == null) {
			logger.error("Error starting up: Can not load tipi");
		} else {
			context.parseStream(tipiResourceStream,null);
			context.switchToDefinition(definition);
		}
		return context;
	}

	public static Map<String, String> parseProperties(String gsargs) {
		StringTokenizer st = new StringTokenizer(gsargs);
		ArrayList<String> a = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a);
	}

	public static Map<String, String> parseProperties(String[] args) {
		List<String> st = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			st.add(args[i]);
		}
		return parseProperties(st);
	}

	public static Map<String, String> parseProperties(List<String> args) {
		Map<String, String> result = new HashMap<String, String>();
		for (String current : args) {
			if (current.startsWith("-D")) {
				String prop = current.substring(2);
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					result.put(name, value);
				} catch (NoSuchElementException ex) {
					logger.error("Error parsing system property",ex);
				} catch (SecurityException se) {
					logger.error("Security exception: " + se.getMessage(),se);
					
				}
			}
		}

		return result;
	}
}
