package tipi;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

import javax.swing.UIManager.*;
import java.io.*;
import java.net.*;

import com.dexels.navajo.swingclient.*;

public class MainApplication {

	static public void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Usage: tipi <url to tipidef.xml>");
			return;
		}
		String definition = (String) args[args.length - 1];
		initialize(definition,args,null);
	}


	public static  SwingTipiContext initialize(String definition, Object[] args, TipiApplet appletRoot) throws Exception {
		Map properties = parseProperties(args);

		SwingTipiContext context = null;
		context = new SwingTipiContext();
		context.setAppletRoot(appletRoot);
		SwingTipiUserInterface stui = new SwingTipiUserInterface((SwingTipiContext) context);
		SwingClient.setUserInterface(stui);
		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
		context.parseRequiredIncludes();
		context.processRequiredIncludes();

		context.processProperties(properties);
		
		
		
		InputStream tipiResourceStream = context.getTipiResourceStream(definition);
		if(tipiResourceStream==null) {
			System.err.println("Error starting up: Can not load tipi");
		} else {
			context.parseStream(tipiResourceStream, definition, false);
		}
		return context;
	}

	public static Map parseProperties(String args) {
		StringTokenizer st = new StringTokenizer(args);
		ArrayList a = new ArrayList();
		while(st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a.toArray());
	}

	public static Map parseProperties(Object[] args) {
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
