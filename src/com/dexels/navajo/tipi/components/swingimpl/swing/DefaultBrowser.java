package com.dexels.navajo.tipi.components.swingimpl.swing;

public class DefaultBrowser {

	// private static final String WIN_ID = "Windows";
	private static final String WIN_PATH = "rundll32";
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

	private static final String MAC_PATH = "open";

	// private static final String UNIX_PATH = "netscape";
	// private static final String UNIX_FLAG = "-remote openURL";

	public DefaultBrowser() {
		super();
	}

	public static boolean displayURL(String url) {

		boolean result = true;
		boolean windows = false;
		String os = System.getProperty("os.name").toLowerCase();
		if (os.indexOf("windows") >= 0) {
			windows = true;
		}
		String cmd = null;
		try {
			if (windows) {
				cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
				System.err.println("Executing Windows command: " + cmd);
				Runtime.getRuntime().exec(cmd);
			} else {
				String lcOSName = System.getProperty("os.name").toLowerCase();
				boolean MAC_OS_X = lcOSName.startsWith("mac os x");
				if (MAC_OS_X) {
					cmd = MAC_PATH +" " + url;
					System.err.println("Executing MAC command: " + cmd);
					Runtime.getRuntime().exec(cmd);
					return result;
				} else {

				}
				
				if (url.indexOf(' ') != -1) {
					System.err.println("Warning, spaces in URL, might fail");
				}
				if (url.toLowerCase().endsWith(".doc") || url.toLowerCase().endsWith(".xls") || url.toLowerCase().endsWith(".ppt")
						|| url.toLowerCase().endsWith(".txt") || url.toLowerCase().endsWith(".rtf")) {
					cmd = "ooffice " + url;
				} else if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".gif") || url.toLowerCase().endsWith(".png")
						|| url.toLowerCase().endsWith(".tif") || url.toLowerCase().endsWith(".tiff")) {
					cmd = "display " + url;
				} else if (url.toLowerCase().endsWith(".pdf")) {
					cmd = "xpdf " + url;
				} else if (url.toLowerCase().endsWith(".jnlp")) {
					cmd = "mozilla " + url;
				}

				else { // we don't have a clue..
					cmd = "mozilla " + url;
				}
				System.err.println("EXECUTING COMMAND:   " + cmd);
				Runtime.getRuntime().exec(cmd);

			}
		} catch (java.io.IOException ex) {
			result = false;
			System.err.println("Could not invoke browser, command=" + cmd);
			System.err.println("Caught: " + ex);
			ex.printStackTrace();
		}
		return result;
	}

}
