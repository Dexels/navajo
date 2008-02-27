package tipi;

import java.io.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class MainApplication {

	static public void main(String[] args) throws Exception {
	
			if (args.length < 1) {
			System.err.println("Usage: tipi <url to tipidef.xml>");
			return;
		}
		final String definition = args[args.length - 1];
		final List<String> arrrgs = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			arrrgs.add(args[i]);
		}
		RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				try {
					initialize(definition,arrrgs,null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}});
	}


	public static  SwingTipiContext initialize(String definition, List<String> args, TipiApplet appletRoot) throws Exception {
		Map<String, String> properties = parseProperties(args);

		SwingTipiContext context = null;
		context = new SwingTipiContext();
		context.setAppletRoot(appletRoot);
		SwingTipiUserInterface stui = new SwingTipiUserInterface(context);
		SwingClient.setUserInterface(stui);
		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
//		context.parseRequiredIncludes();
//		context.processRequiredIncludes();

		context.processProperties(properties);
		
		
		
		InputStream tipiResourceStream = context.getTipiResourceStream(definition);
		if(tipiResourceStream==null) {
			System.err.println("Error starting up: Can not load tipi");
		} else {
			context.parseStream(tipiResourceStream, definition, false);
		}
		return context;
	}

	public static Map<String,String> parseProperties(String gsargs) {
		StringTokenizer st = new StringTokenizer(gsargs);
		ArrayList<String> a = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a);
	}

	public static Map<String,String> parseProperties(List<String> args) {
		Map<String,String> result = new HashMap<String,String>();
		for (String current : args) {
			if (current.startsWith("-D")) {
				String prop = current.substring(2);
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					result.put(name, value);
					// try {
					// System.setProperty(name, value);
					// } catch (SecurityException e) {
					// System.err.println("Setting property failed due to
					// security. No problem: "+e.getMessage());
					// }
				} catch (NoSuchElementException ex) {
					System.err.println("Error parsing system property");
				} catch (SecurityException se) {
					System.err.println("Security exception: " + se.getMessage());
					se.printStackTrace();
				}
			}			
		}

		return result;
	}
}
