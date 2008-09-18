package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiJxLayerExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "SwingX extended libraries";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/swingxclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "jxlayer";
	}


	public String requiresMainImplementation() {
		return "swing";
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("jxlayer.jar");
		l.add("filters.jar");
		
		return l;
	}

	public List<String> getMainJars() {
		List<String> l = new LinkedList<String>();
		l.add("TipiJxLayer.jar");
		return l;
	} 
	public String getConnectorId() {
		return null;
	}

	public List<String> getRequiredExtensions() {
		return null;
	}
	public String getProjectName() {
		return "TipiJxLayer";
	}

	public String getDeploymentDescriptor() {
		return "TipiJxLayer.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("https://jxlayer.dev.java.net/");
		return l;
	}
}
