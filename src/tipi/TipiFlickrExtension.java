package tipi;

import java.util.*;

public class TipiFlickrExtension implements TipiExtension {

	public String getDescription() {
		return "Youtube extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/youtube/actions/actiondef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}
	public String getId() {
		return "youtube";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getMainJars() {
		// TODO Auto-generated method stub
		return null;
	} 

}
