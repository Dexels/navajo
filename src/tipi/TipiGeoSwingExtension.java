package tipi;

import java.util.*;

import com.dexels.navajo.tipi.TipiContext;

public class TipiGeoSwingExtension implements TipiExtension {

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

	public void initialize(TipiContext tc) {
		// TODO Auto-generated method stub
		
	}

}
