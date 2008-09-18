package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiMailExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Mail connector. Uses the java mail api to connect to a imap server. TODO: POP support";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/mailclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return false;
	}


	public String getId() {
		
		return "mail";
	}


	public String requiresMainImplementation() {
		return null;
	}

	public List<String> getLibraryJars() {
		List<String> l = new LinkedList<String>();
		l.add("mail.jar");
		l.add("activation.jar");
		return l;
	}

	public List<String> getMainJars() {
		List<String> l = new LinkedList<String>();
		l.add("TipiMail.jar");
		return l;
	} 
	public String getConnectorId() {
		return "mail";
	}

	public List<String> getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getProjectName() {
		return "TipiMail";
	}

	public String getDeploymentDescriptor() {
		return "TipiMail.jnlp";
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("http://java.sun.com/javase/technologies/desktop/javabeans/jaf/");
		l.add("http://java.sun.com/products/javamail/");
		
		return l;
	}	
}
