package tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipipackage.ITipiExtensionContainer;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

public abstract class BaseTipiApplicationInstance implements TipiApplicationInstance {

	private TipiContext currentContext;
//	private File installationFolder = null;
	private static final Logger logger = LoggerFactory.getLogger(BaseTipiApplicationInstance.class);
	public TipiContext getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(TipiContext currentContext) {
		this.currentContext = currentContext;
	}

	public final void startup() throws IOException, TipiException {
		TipiContext context = createContext();
		setCurrentContext(context);
	}

	public void dispose(TipiContext t) {
		t.exit();
	}

	public final void reboot() throws IOException, TipiException {
		TipiContext tc = currentContext;
		startup();
		dispose(tc);
	}

	// Utilities:
	
	public static void processSettings(String deploy, String profile,  File installationFolder, ITipiExtensionContainer extensionContainer)  {
		File settings = new File(installationFolder,"settings");

		Map<String, String> bundleValues = getBundleMap("arguments.properties",installationFolder);
		File profileProperties = new File(settings,"profiles/"+profile+".properties");
		if(profileProperties.exists()) {
			Map<String, String> profileValues = getBundleMap("profiles/"+profile+".properties",installationFolder);
			bundleValues.putAll(profileValues);
		} else {
			logger.info("No profile bundles present.");
		}
		logger.info("Settings: "+bundleValues);
		Map<String,String> resolvedValues = new HashMap<String, String>();
		for (Entry<String,String> entry : bundleValues.entrySet()) {
			if(entry.getKey().indexOf("/")<0) {
				resolvedValues.put(entry.getKey(), entry.getValue());
			} else {
				String[] elts = entry.getKey().split("/");
				if(elts[0].equals(deploy)) {
					resolvedValues.put(elts[1], entry.getValue());
				}
			}
			
		}
		logger.debug("RESOLVED TO: "+resolvedValues);
		resolvedValues.put("tipi.deploy", deploy);
		resolvedValues.put("tipi.profile", profile);
		

		// TODO Store these somewhere else
		for (Entry<String,String> entry : resolvedValues.entrySet()) {
			logger.debug("Setting: "+entry.getKey());
			extensionContainer.setSystemProperty(entry.getKey(), entry.getValue());
		}	
//		return resolvedValues;
	}

	public static Map<String, String> getBundleMap(String path, File installationFolder)  {
		File settings = new File(installationFolder,"settings");
		Map<String,String> bundleValues = new HashMap<String, String>();
		PropertyResourceBundle prb;
		try {
			FileReader argReader = new FileReader(new File(settings,path));
			prb = new PropertyResourceBundle(argReader);
		} catch (IOException e) {
			logger.info("Settings file: "+path+" not found in installationFolder: "+installationFolder.getAbsolutePath()+". continuing.");
			return bundleValues;
		}


		
		for (String key : prb.keySet()) {
			bundleValues.put(key, prb.getString(key));
		}
		return bundleValues;
	}




	protected Map<String, String> parseProperties(String gsargs)
			throws IOException {
		StringTokenizer st = new StringTokenizer(gsargs);
		ArrayList<String> a = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a);
	}

	protected Map<String, String> parseProperties(List<String> args)
			throws IOException {
		
		Map<String, String> result = new HashMap<String, String>();
		if(args==null) {
			return result;
		}
		int index = 0;
		for (String current : args) {
			if (current.indexOf("=") != -1) {
				String prop = current;
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					result.put(name, value);

				} catch (NoSuchElementException ex) {
					logger.error("Error parsing system property",ex);
				} catch (SecurityException se) {
					System.err
							.println("Security exception: " + se.getMessage());
					se.printStackTrace();
				}
			} else if (current.equals("-profile")) {
				loadProfile(args.get(index + 1), result);
			}
			index++;
		}
		return result;
	}

	private void loadProfile(String profileName, Map<String, String> result)
			throws IOException {
		File profileFile = new File("settings/profiles/" + profileName
				+ ".properties");
		File argumentsFile = new File("settings/arguments.properties");
		FileInputStream arguments = new FileInputStream(argumentsFile);
		readArguments(arguments, result);
		arguments.close();
		FileInputStream profile = new FileInputStream(profileFile);
		readArguments(profile, result);
		profile.close();
	}

	private void readArguments(FileInputStream profile,
			Map<String, String> result) throws IOException {
		PropertyResourceBundle prb = new PropertyResourceBundle(profile);
		for (String key : prb.keySet()) {
			result.put(key, prb.getString(key));
		}
	}
}
