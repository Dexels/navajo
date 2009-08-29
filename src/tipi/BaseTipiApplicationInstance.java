package tipi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.dexels.navajo.tipi.TipiContext;

public abstract class BaseTipiApplicationInstance implements TipiApplicationInstance {

	private TipiContext currentContext;

	public TipiContext getCurrentContext() {
		return currentContext;
	}

	public void setCurrentContext(TipiContext currentContext) {
		this.currentContext = currentContext;
	}

	public final void startup() throws IOException {
		setCurrentContext(createContext());
	}

	
	public void dispose(TipiContext t) {
		t.exit();
	}

	
	public final void reboot() throws IOException {
		TipiContext tc =  currentContext;
		startup();
		dispose(tc);
	}
	

	
	
   // Utilities:

	private Map<String, String> parseProperties(String gsargs) {
		StringTokenizer st = new StringTokenizer(gsargs);
		ArrayList<String> a = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a);
	}
	
	protected Map<String, String> parseProperties(List<String> args) {
		Map<String, String> result = new HashMap<String, String>();
		for (String current : args) {
			if (current.indexOf("=")!=-1) {
				String prop = current;
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					result.put(name, value);

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
