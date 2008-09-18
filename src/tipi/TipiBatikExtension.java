package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiBatikExtension implements TipiExtension {
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Batik extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/swing/svgclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String getId() {
		return "batik";
	}

	public String requiresMainImplementation() {
		return "swing";
	}

	public String getConnectorId() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("xml-apis.jar");
		System.err.println("WARNING, OUTDATED!");
		l.add("batik-all.jar");
		l.add("xml-apis-ext.jar");
		return l;
	}

	public List<String> getMainJars() {
		List<String> l = new LinkedList<String>();
		l.add("TipiSvg.jar");
		return l;
	}

	public List<String> getRequiredExtensions() {
		return null;
	}

	public int compareTo(TipiExtension o) {
		// TODO Auto-generated method stub
		return 0;
	} 
	public String getProjectName() {
		return "TipiSvgBatik";
	}

	public String getDeploymentDescriptor() {
		return "TipiSvgBatik.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("http://xmlgraphics.apache.org/batik/");
		return l;

	}
}
