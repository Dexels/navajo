package tipi;

import java.util.*;

public class TipiCore implements TipiExtension {

	public String getDescription() {
		return "Core tipi";
	}

	public String[] getIncludes() {
		// TODO Auto-generated method stub
//		s.add("com/dexels/navajo/tipi/classdef.xml");
//		s.add("com/dexels/navajo/tipi/actions/actiondef.xml");
	
		return new String[]{"com/dexels/navajo/tipi/classdef.xml","com/dexels/navajo/tipi/actions/actiondef.xml"};
	}

	public boolean isMainImplementation() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getId() {
		return "core";
	}

	public String requiresMainImplementation() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<String> getLibraryJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("NavajoClient.jar");
		jars.add("NavajoDocument.jar");
		jars.add("NavajoRuntime.jar");
		jars.add("NavajoFunctions.jar");
		jars.add("NavajoServer.jar");
		jars.add("NavajoClient.jar");
		return jars;
	}

	public List<String> getMainJars() {
		ArrayList<String> jars = new ArrayList<String>();
		jars.add("tipipackage.jar");
		return jars;
	}

	public String getConnectorId() {
		return "http";
	}
}
