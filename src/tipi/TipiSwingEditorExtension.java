package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiSwingEditorExtension extends AbstractTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Tipi Swing editor. WYSISYG editor ";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/swingeditor/swingeditorclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "swingeditor";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("rssutils.jar");
		
		return l;
	}

	public List<String> getMainJars() {
			return null;
	} 
	public String getConnectorId() {
		return "rss";
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
		l.add("http://java.sun.com/developer/technicalArticles/javaserverpages/rss_utilities/");
		
		return l;
	}	
	
}
