package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.jxlayer.impl.*;

public class TipiJxLayerExtension extends AbstractTipiExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		tc.getTipiValidationDecorator(new JxValidationDecorator());
	}

	public String getDescription() {
		return "JXLayer extended libraries";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/jxlayerclassdef.xml"};
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
		List<String> l = new LinkedList<String>();
		l.add("swingx");
		return l;
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
