package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiSubstanceExtension extends AbstractTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
 
	}

	public String getDescription() {
		return "Substance extensions";
	}

	public String[] getIncludes() {
		return new String[] { "com/dexels/navajo/tipi/swing/substance/substanceclassdef.xml" };
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String getId() {
		return "substance";
	}

	public String requiresMainImplementation() {
		return "swing";
	}

	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("substance.jar");
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("TipiSubstance.jar");
		return jars;
	}

	public String getConnectorId() {
		return null;
	}

	public List<String> getRequiredExtensions() {

		return null;
	}

	public String getProjectName() {
		return "TipiSubstance";
	}

	public String getDeploymentDescriptor() {
		return "TipiSubstance.jnlp";
	}

	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("https://substance.dev.java.net/");
		return l;
	}

}
