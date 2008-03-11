package tipi;

import java.util.*;

public class TipiSwingExtension implements TipiExtension {

	public String getDescription() {
		return "Standard swing implementation";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return true;
	}

	public String getId() {
		return "swing";
	}

	public String requiresMainImplementation() {
		return null;
	}
	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("NavajoSwingClient.jar");
		jars.add("TimingFrameWork-1.0.jar");
		jars.add("AnimatedTransitions-0.11.jar");
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("NavajoSwingTipi.jar");
		return jars;
	}
	public String getConnectorId() {
		return null;
	}

}
