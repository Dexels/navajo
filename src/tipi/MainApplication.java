package tipi;

import java.util.*;

import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.HttpResourceLoader;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;

import javax.swing.UIManager.*;
import java.io.*;
import java.net.URL;

import com.dexels.navajo.swingclient.*;

public class MainApplication {

	static public void main(String[] args) throws Exception {
//		SecurityManager securityManager = new SecurityManager() {
//			public void checkConnect(String arg0, int arg1, Object arg2) {
//			}
//
//			public void checkConnect(String arg0, int arg1) {
//			}
//
//			public void checkRead(FileDescriptor arg0) {
//			}
//
//			public void checkRead(String arg0, Object arg1) {
//			}
//
//			public void checkRead(String arg0) {
//			}
//		};

//		System.setSecurityManager(securityManager);
		initialize(args);
	}

	static public SwingTipiContext initialize(Object[] args) throws Exception {
		return initialize(args, null);
	}

	static public SwingTipiContext initialize(Object[] args, TipiApplet appletRoot) throws Exception {
		Map properties = checkForProperties(args);
		String tipiLaf = null;
		
		
		try {
			tipiLaf = System.getProperty("tipilaf");
		} catch (SecurityException e) {
		
		}
		if(tipiLaf == null) {
			tipiLaf = (String)properties.get("tipilaf");
		}
		
		if (args.length < 1) {
			System.err.println("Usage: tipi [-studio | -classic] <url to tipidef.xml>");
			return null;
		}
		try {
			Locale.setDefault(new Locale("nl", "NL"));
		} catch (SecurityException se) {
		}
		try {

			if (System.getProperty("com.dexels.navajo.DocumentImplementation") == null) {
				System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
			}
		} catch (SecurityException se) {
		}
		properties.put("com.dexels.navajo.propertyMap", "tipi.propertymap");
		try {
			System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
		} catch (SecurityException se) {
		}
		if (tipiLaf == null) {
			 System.err.println("No supplied laf. Using default: "+UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} else {
			System.err.println("Found supplied laf: " + tipiLaf);
			UIManager.setLookAndFeel(tipiLaf);

		}

		// UIManager.put("Button.showMnemonics", Boolean.TRUE);
		boolean debugMode = false;
		try {
			String debugStr = System.getProperty("com.dexels.navajo.tipi.debugMode");
			debugMode = debugStr != null && debugStr.equals("true");
		} catch (SecurityException se) {
			System.err.println("Switching off debugging: No access");
		}
		SwingTipiContext context = null;
		context = new SwingTipiContext();
		context.setAppletRoot(appletRoot);
		for (Iterator iter = properties.keySet().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			String value = (String) properties.get(element);
			context.setSystemProperty(element, value);
		}

		String tipiCodeBase = (String) properties.get("tipiCodeBase");
		if (tipiCodeBase != null) {
			context.setTipiResourceLoader(new HttpResourceLoader(new URL(tipiCodeBase)));
		}
		String resourceCodeBase = (String) properties.get("resourceCodeBase");
		if (resourceCodeBase != null) {
			context.setGenericResourceLoader(new HttpResourceLoader(new URL(resourceCodeBase)));
		}
		// context.setGenericResourceLoader(new HttpResourceLoader(new
		// URL("http://navajo.dexels.com/TipiClientDemo/resource/")));

		SwingTipiUserInterface stui = new SwingTipiUserInterface((SwingTipiContext) context);
		SwingClient.setUserInterface(stui);

//		context.setResourceBaseDirectory(new File("resource"));

		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
		((SwingTipiContext) context).setDebugMode(debugMode);

		String lastArg = (String) args[args.length - 1];
		System.err.println("Opening: " + lastArg);
		// System.err.println("Opening: " +
		// context.getResourceURL(args[args.length - 1]));
		context.parseRequiredIncludes();
		InputStream tipiResourceStream = context.getTipiResourceStream(lastArg);
		if(tipiResourceStream==null) {
			System.err.println("Error starting up: Can not load tipi");
			return null;
		}
		context.parseStream(tipiResourceStream, lastArg, false);
		// }
		// long diff = System.currentTimeMillis()-startupTime;
		// System.err.println("\n\n*************8 bootup: "+diff+"\n");
		return context;
	}

	private static Map checkForProperties(Object[] args) {
		Map result = new HashMap();
		for (int i = 0; i < args.length; i++) {
			String current = (String) args[i];
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
