package navajorhino;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.rhino.RhinoHandler;
import com.dexels.navajo.rhino.RhinoRepository;
import com.dexels.navajo.server.HandlerFactory;
import com.dexels.navajo.server.RepositoryFactory;

public class Version extends dexels.Version {

	@Override
	public void start(BundleContext bc) throws Exception {
		System.err.println("Starting Navajo Rhino");
		super.start(bc);
		RhinoRepository rp = new RhinoRepository();
		RepositoryFactory.registerRepository("com.dexels.navajo.rhino.RhinoRepository", rp);
		HandlerFactory.registerHandler("com.dexels.navajo.rhino.RhinoHandler", new RhinoHandler());
		System.err.println("Registered rhino repository: "+rp);
	}
}
