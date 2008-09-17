package tipi;

import java.util.*;

import com.dexels.navajo.tipi.*;

public class TipiEchoExtension implements TipiExtension {

	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	public String getDescription() {
		return "Standard echo implementation";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/tipi/components/echoimpl/echoclassdef.xml"};
	}

	public boolean isMainImplementation() {
		return true;
	}

	public String getId() {
		return "echo";
	}

	public String requiresMainImplementation() {
		return null;
	}
	public List<String> getLibraryJars() {
		List<String> jars = new ArrayList<String>();
		jars.add("commons-fileupload-1.0.jar");
		jars.add("Echo2_App.jar");
		jars.add("Echo2_Extras_WebContainer.jar");
		jars.add("Echo2_Extras_App.jar");
		jars.add("Echo2_FileTransfer_App.jar");
		jars.add("Echo2_FileTransfer_WebContainer.jar");
		jars.add("Echo2_WebContainer.jar");
		jars.add("Echo2_WebRender.jar");
		jars.add("echopointng-2.1.0rc5.jar");
		jars.add("jcommon-1.0.0.jar");
		jars.add("tucana-20060720.jar");
		return jars;
	}

	public List<String> getMainJars() {
		List<String> jars = new ArrayList<String>();
		jars.add("NavajoEchoTipi.jar");
		return jars;
	}
	public String getConnectorId() {
		return null;
	}

	public List<String> getRequiredExtensions() {
		return null;
	}
	public String getProjectName() {
		return "NavajoEchoTipi";
	}

	public String getDeploymentDescriptor() {
		return null;
	}
	public List<String> getDependingProjectUrls() {
		List<String> l = new LinkedList<String>();
		l.add("http://echo.nextapp.com/site/");
		return l;
	}
}
