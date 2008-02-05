package tipi;

import java.util.*;

public class TipiRssExtension implements TipiExtension {

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
		return null;
	}

	public List<String> getMainJars() {
			return null;
	} 

}
