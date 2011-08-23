package tipi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiSubstanceExtension extends TipiAbstractXMLExtension implements TipiExtension, BundleActivator {


	private static final long serialVersionUID = 5307279111204698579L;

	public TipiSubstanceExtension() {
//		loadDescriptor();
		System.err.println("Created: TipiSubstanceExtension");
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing
 
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
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
