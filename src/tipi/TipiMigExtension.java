package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiMigExtension extends AbstractTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		
	}

	public String getDescription() {
		return "Tipi Swing Mig layout";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/mig/migclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "mig";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("miglayout-3.6-swing.jar");
		
		return l;
	}

	public List<String> getMainJars() {
			return null;
	} 
	public String getConnectorId() {
		return "mig";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}
	public String getProjectName() {
		return "TipiRss";
	}

	public String getDeploymentDescriptor() {
		return "TipiRss.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("http://www.miglayout.com/");
		
		return l;
	}	
	
}
