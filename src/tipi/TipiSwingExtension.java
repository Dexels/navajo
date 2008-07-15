package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiSwingExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

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
		jars.add("TipiSwingClient.jar");
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

	public List<String> getRequiredExtensions() {
		return null;
	}

}
