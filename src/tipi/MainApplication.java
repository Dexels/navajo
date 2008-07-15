package tipi;

import java.io.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;
import com.dexels.navajo.tipi.tipixml.*;

public class MainApplication {

	static public void main(String[] args) throws Exception {
		String definition = null;
		String os = System.getProperty("os.name");
		if(os.toLowerCase().indexOf("linux")!=-1) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} else {
//			System.err.println("Ignoring LNF, ON LINUX ONLY!");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		}

		if (args.length < 1) {
			definition = "init";
		}  else {
			definition = args[args.length - 1];
		}

		boolean studio;
		try {
			Class.forName("tipi.TipiDevelopTools");
			studio = true;
		} catch (ClassNotFoundException e) {
			studio = false;
		}	
		final boolean studioMode = studio;
		final List<String> arrrgs = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			arrrgs.add(args[i]);
		}
		RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
		final String def = definition;
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				try {
					if (studioMode) {
						SwingTipiContext s = initialize("develop","tipi/develop.xml",arrrgs,null,null);
						s.injectApplication(def,arrrgs,"/init/tipi/sandbox");
					} else {
						if (def.endsWith(".xml")) {
							initialize("init",def,arrrgs,null,null);
						} else {
							initialize(def,"start.xml",arrrgs,null,null);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}});
	}


	
/**
 * Definitionpath allows a non standard definition path
 * @param definition
 * @param definitionPath
 * @param args
 * @param appletRoot
 * @param otherRoot
 * @return
 * @throws Exception
 */
	public static  SwingTipiContext initialize(String definition,String definitionPath, List<String> args, TipiApplet appletRoot, RootPaneContainer otherRoot) throws Exception {
		Map<String, String> properties = parseProperties(args);

		if(definitionPath==null) {
			definitionPath = definition;
		}
		SwingTipiContext context = null;
		context = new SwingTipiContext(null);
		context.setAppletRoot(appletRoot);
		context.setOtherRoot(otherRoot);
		SwingTipiUserInterface stui = new SwingTipiUserInterface(context);
		SwingClient.setUserInterface(stui);
		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
//		context.parseRequiredIncludes();
//		context.processRequiredIncludes();

		context.processProperties(properties);
		
		System.err.println("Openingin definition: "+definition);
		InputStream tipiResourceStream = context.getTipiResourceStream(definitionPath);
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
