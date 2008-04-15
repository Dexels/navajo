package tipi;

import java.util.*;

public class TipiEchoExtension implements TipiExtension {

	public String getDescription() {
		return "Standard echo implementation";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/components/echoimpl/echoclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return true;
	}

	public String getId() {
		return "echo";
	}

	public String requiresMainImplementation() {
		return null;
	}
	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		return jars;
	}
	public String getConnectorId() {
		return null;
	}

}
