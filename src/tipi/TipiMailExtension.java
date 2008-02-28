package tipi;

import java.util.*;

public class TipiMailExtension implements TipiExtension {

	public String getDescription() {
		return "Mail connector";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/mailclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "mail";
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
