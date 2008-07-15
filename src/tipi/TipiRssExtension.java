package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiRssExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Rss connector";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/rssclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "rss";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("rssutils.jar");
		
		return l;
	}

	public List<String> getMainJars() {
			return null;
	} 
	public String getConnectorId() {
		return "rss";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}

}
