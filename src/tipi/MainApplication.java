package tipi;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.spi.ServiceRegistry;
import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;

public class MainApplication {

	private static SwingTipiApplicationInstance myApplication = null;

	static public void main(String[] args) throws Exception {
		// TODO Refactor Formatters in NavajoFactory, so this can be done later.
		try {
			Locale.setDefault(new Locale("nl", "NL"));
		} catch (SecurityException se) {
			System.err.println("Sandbox. Using default locale");
		}
		
		List<String> arrrgs = new ArrayList<String>();

		String definition = null;
		for (int i = 0; i < args.length; i++) {
				arrrgs.add(args[i]);
		}
		if(args.length>0 ) {
//				System.err.println("final arg: "+args[args.length-1].endsWith(".xml"));
				if(args[args.length-1].endsWith(".xml")) {
					definition = args[args.length - 1];
					
				}
			}
//		}
		initializeSwingApplication(checkStudio(), arrrgs, definition,null,null);
	}

	/**
	 * @return
	 */
	private static boolean checkStudio() {
		boolean studio = false;
		try {
			Class.forName("tipi.TipiToolsExtension");
			String s = System.getProperty("studio");
			if(s!=null) {
				studio = s.equals("true");
			}
//			System.err.println("Tipi studio found, but disabled for now");
//			studio = true;
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
	public static SwingTipiApplicationInstance initializeSwingApplication(final boolean studioMode, final List<String> arrrgs, final String def, final TipiApplet appletRoot, final RootPaneContainer otherRoot) {
		RepaintManager.setCurrentManager(new CheckThreadViolationRepaintManager());
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					if (studioMode) {
						myApplication = new SwingTipiApplicationInstance("develop", "tipi/develop.xml", arrrgs, appletRoot, otherRoot);
//						SwingTipiContext s = initialize("develop", "tipi/develop.xml", arrrgs, null, null);
						
					} else {
						if (def == null) {
							myApplication = new SwingTipiApplicationInstance("init", "init.xml", arrrgs, appletRoot, otherRoot);
						} else {
							if (def.endsWith(".xml")) {
								myApplication = new SwingTipiApplicationInstance("init", def, arrrgs, appletRoot, otherRoot);
							} else {
								myApplication = new SwingTipiApplicationInstance(def, "start.xml", arrrgs, appletRoot, otherRoot);
							}
						}
					}

					if(myApplication!=null) {
							myApplication.startup();
							if(studioMode) {
								((SwingTipiContext)myApplication.getCurrentContext()).injectApplication(def, arrrgs, "/init/tipi/sandbox");
							}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
		return myApplication;
	}

	public static List<String> parseBundleFile(String path) throws IOException {
		File f = new File(path);
		InputStream openStream = new FileInputStream(f);
		return parseBundleStream(openStream);
	}

	public static List<String> parseBundleUrl(URL u) throws IOException {
//		URL u = new URL(path);
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



//	public static Map<String, String> parseProperties(String gsargs) {
//		StringTokenizer st = new StringTokenizer(gsargs);
//		ArrayList<String> a = new ArrayList<String>();
//		while (st.hasMoreTokens()) {
//			String next = st.nextToken();
//			a.add(next);
//		}
//		return parseProperties(a);
//	}


}
