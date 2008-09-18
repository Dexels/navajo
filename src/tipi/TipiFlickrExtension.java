package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiFlickrExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Flickr extensions";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/flickr/flickrclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}
	public String getId() {
		return "flickr";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("flickrapi-1.0b4.jar");
		return l;
	}

	public List<String> getMainJars() {
		List<String> l = new LinkedList<String>();
		l.add("TipiFlickr.jar");
		return l;
	} 
	public String getConnectorId() {
		return "flickr";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}
	public String getProjectName() {
		return "TipiFlickr";
	}

	public String getDeploymentDescriptor() {
		return "TipiFlickr.jnlp";
	}

	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("http://flickrj.sourceforge.net/");
		return l;

	}
}
