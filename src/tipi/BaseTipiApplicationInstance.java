package tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;

abstract class BaseTipiApplicationInstance implements TipiApplicationInstance {

	private TipiContext currentContext;

	public TipiContext getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(TipiContext currentContext) {
		this.currentContext = currentContext;
	}

	public final void startup() throws IOException, TipiException {
		TipiContext context = createContext();
		setCurrentContext(context);
		// context.switchToDefinition(getDefinition());
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
					System.err.println("Error parsing system property");
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
