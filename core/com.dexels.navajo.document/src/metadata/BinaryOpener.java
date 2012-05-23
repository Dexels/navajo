package metadata;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import metadata.FormatDescription;

import com.dexels.navajo.document.types.Binary;


public class BinaryOpener {

	// private static final String WIN_ID = "Windows";
	private static final String WIN_PATH = "rundll32";
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

	private static final String MAC_PATH = "open";

	
	private final static Logger logger = LoggerFactory
			.getLogger(BinaryOpener.class);
	// private static final String UNIX_PATH = "netscape";
	// private static final String UNIX_FLAG = "-remote openURL";

	public BinaryOpener() {
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
				logger.info("Executing Windows command: " + cmd);
				Runtime.getRuntime().exec(cmd);
			} else {
				String lcOSName = System.getProperty("os.name").toLowerCase();
				boolean MAC_OS_X = lcOSName.startsWith("mac os x");
				if (MAC_OS_X) {
					cmd = MAC_PATH +" " + url;
					logger.info("Executing MAC command: " + cmd);
					Runtime.getRuntime().exec(cmd);
					return result;
				} else {

				}
				
				if (url.indexOf(' ') != -1) {
					logger.info("Warning, spaces in URL, might fail");
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
				logger.info("EXECUTING COMMAND:   " + cmd);
				Runtime.getRuntime().exec(cmd);

			}
		} catch (java.io.IOException ex) {
			result = false;
			logger.info("Could not invoke browser, command=" + cmd);
			logger.info("Caught: " + ex);
			logger.error("Error: ", ex);
		}
		return result;
	}

	public static void openBinary(Binary b) {
		String extString = null;
		String fileNameEval = null;
	
		if (extString == null) {
			String mime = b.guessContentType();
			String ext = b.getExtension();
			FormatDescription fd = b.getFormatDescription();
			if(fd!=null) {
				if(ext==null) {
					List<String> extensions = fd.getFileExtensions();
					if(!extensions.isEmpty()) {
						ext = extensions.get(0);

					}
				}
			}
			if (mime != null) {
				if (mime.indexOf("/") != -1) {
					StringTokenizer st = new StringTokenizer(mime, "/");
					String major = st.nextToken();
					String minor = st.nextToken();
					logger.info("Binary type: " + major + " and minor: " + minor);
					if(ext!=null) {
						extString = ext;
					} else {
						extString = minor;
					}
				}
			}
		}

		try {
			if (fileNameEval == null) {
				fileNameEval = "data_";
			}
			File f = File.createTempFile(fileNameEval, "." + extString);
			BinaryOpener.displayURL(f.getAbsolutePath());
			f.deleteOnExit();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

	}
	
}
