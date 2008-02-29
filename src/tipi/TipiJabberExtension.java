package tipi;

import java.util.*;

public class TipiJabberExtension implements TipiExtension {

	public String getDescription() {
		return "Jabber extensions. Swing component for now, can easily be refactored to core";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/jabber/jabberclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "jabber";
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
