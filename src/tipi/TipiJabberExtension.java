package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiJabberExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Jabber extensions. Swing component for now, can easily be refactored to core";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/jabber/jabberclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "jabber";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("smack.jar");
		l.add("smackx.jar");
		return l;
		}

	public List<String> getMainJars() {
			return null;
	} 
	public String getConnectorId() {
		return "jabber";
	}

	public List<String> getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

}
