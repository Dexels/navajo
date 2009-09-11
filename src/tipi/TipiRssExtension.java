package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiRssExtension extends AbstractTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Rss connector. Supplies an rss connector";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/rssclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "rss";
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
