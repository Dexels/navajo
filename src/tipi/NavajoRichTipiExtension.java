package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class NavajoRichTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	
	public String getDescription() {
		return "Rich Tipi extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/components/rich/richclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return "rich";
	}

	public String requiresMainImplementation() {
		// TODO Auto-generated method stub
		return "swing";
	}

	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("NavajoRichClient.jar");
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("NavajoRichTipi.jar");
		return jars;
	}
	
	public String getConnectorId() {
		return null;
	}

	public List<String> getRequiredExtensions() {
		List<String> l = new LinkedList<String>();
		l.add("swing");
		return l;
	}

	public String getProjectName() {
		return "NavajoRichTipi";
	}

	public String getDeploymentDescriptor() {
		return "NavajoRichTipi.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		// TODO Auto-generated method stub
		List<String> l = new LinkedList<String>();
		l.add("https://timingframework.dev.java.net/");
		l.add("https://animatedtransitions.dev.java.net/");
		l.add("http://filthyrichclients.org/");
		return l;

	}

}
