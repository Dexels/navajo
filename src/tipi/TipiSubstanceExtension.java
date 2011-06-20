package tipi;

import com.dexels.navajo.tipi.TipiContext;

public class TipiSubstanceExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiSubstanceExtension() {
		loadDescriptor();
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing
 
	}


//
//
//	public List<String> getLibraryJars() {
//		ArrayList<String> jars = new ArrayList<String>();
//		jars.add("substance.jar");
//		return jars;
//	}
//
//	public List<String> getMainJars() {
//		ArrayList<String> jars = new ArrayList<String>();
//		jars.add("TipiSubstance.jar");
//		return jars;
//	}



}
