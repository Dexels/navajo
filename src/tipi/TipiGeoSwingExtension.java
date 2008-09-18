package tipi;

import java.util.*;


import com.dexels.navajo.tipi.TipiContext;


public class TipiGeoSwingExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Geographic tipi swing extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/swing/geo/geoclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return "geo";
	}

	public String requiresMainImplementation() {
		// TODO Auto-generated method stub
		return "swing";
	}

	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("swingx-ws-2008_05_04.jar");
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("TipiGeoSwing.jar");
		return jars;
	}
	
	public String getConnectorId() {
		return null;
	}

	public List<String> getRequiredExtensions() {
		List<String> l = new LinkedList<String>();
		l.add("swingx");
		return l;
	}
	public String getProjectName() {
		return "TipiGeoSwing";
	}

	public String getDeploymentDescriptor() {
		return "TipiGeoSwing.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("https://swingx-ws.dev.java.net/");
		return l;
	}
}
