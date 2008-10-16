package tipi;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class MainApplication {

	static public void main(String[] args) throws Exception {
		String definition = null;
		System.err.println("Working dir: "+System.getProperty("user.dir"));
		List<String> arrrgs = new ArrayList<String>();
		if(args.length>1 && "-open".equals(args[0])) {
				String path = args[1];
				File f = new File(path);
				System.setProperty("user.dir", f.getParent());
				arrrgs = parseBundleFile(path);
				definition = arrrgs.get(arrrgs.size()-1);
		} else {

			if(args.length>0) {
				definition = args[args.length - 1];
			}
			for (int i = 0; i < args.length; i++) {
				System.err.println("Original argument: "+args[i]);
				arrrgs.add(args[i]);
			}
			
		}
		initialize(checkStudio(), arrrgs, definition);
	}

	/**
	 * @return
	 */
	private static boolean checkStudio() {
		boolean studio;
		try {
			Class.forName("tipi.TipiDevelopTools");
			System.err.println("Tipi studio found, but disabled for now");
			studio = false;
		} catch (ClassNotFoundException e) {
			studio = false;
		}
		return studio;
	}

	/**
	 * @param studioMode
	 * @param arrrgs
	 * @param def
	 */
	private static void initialize(final boolean studioMode, final List<String> arrrgs, final String def) {
		RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
		System.err.println("Initialize: "+def+" == "+arrrgs);
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					if (studioMode) {
						SwingTipiContext s = initialize("develop", "tipi/develop.xml", arrrgs, null, null);
						s.injectApplication(def, arrrgs, "/init/tipi/sandbox");
					} else {
						if (def == null) {
							initialize("init", "init.xml", arrrgs, null, null);
						} else {
							if (def.endsWith(".xml")) {
								initialize("init", def, arrrgs, null, null);
							} else {
								initialize(def, "start.xml", arrrgs, null, null);
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	private static List<String> parseBundleFile(String path) throws IOException {
		File f = new File(path);
		InputStream openStream = new FileInputStream(f);
		return parseBundleStream(openStream);
	}

	private static List<String> parseBundleUrl(String path) throws IOException {
		URL u = new URL(path);
		InputStream openStream = u.openStream();
		return parseBundleStream(openStream);
	}

	/**
	 * Beware: Will close stream
	 * @param openStream
	 * @return list of params.
	 * @throws IOException
	 */
	private static List<String> parseBundleStream(InputStream openStream) throws IOException {
		List<String> result = new LinkedList<String>();

		InputStreamReader appUrl = new InputStreamReader(openStream);
		BufferedReader br = new BufferedReader(appUrl);
		String line = null;
		do {
			line = br.readLine();
			if (line != null) {
				result.add(line);
			}
		} while (line != null);

		openStream.close();
		return result;
	}

	/**
	 * Definitionpath allows a non standard definition path
	 * 
	 * @param definition
	 * @param definitionPath
	 * @param args
	 * @param appletRoot
	 * @param otherRoot
	 * @return
	 * @throws Exception
	 */
	public static SwingTipiContext initialize(String definition, String definitionPath, List<String> args, TipiApplet appletRoot,
			RootPaneContainer otherRoot) throws Exception {
		Map<String, String> properties = parseProperties(args);

		if (definitionPath == null) {
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
		// context.parseRequiredIncludes();
		// context.processRequiredIncludes();

		context.processProperties(properties);

		System.err.println("Openingin definition: " + definition);
		InputStream tipiResourceStream = context.getTipiResourceStream(definitionPath);
		if (tipiResourceStream == null) {
			System.err.println("Error starting up: Can not load tipi");
		} else {
			context.parseStream(tipiResourceStream, definition, false);
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

	public static Map<String, String> parseProperties(List<String> args) {
		Map<String, String> result = new HashMap<String, String>();
		for (String current : args) {
			if (current.indexOf("=")!=-1) {
				String prop = current;
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
