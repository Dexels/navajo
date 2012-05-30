package navajorhino;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.rhino.RhinoHandler;
import com.dexels.navajo.server.HandlerFactory;
import com.dexels.navajo.server.ServiceHandler;

public class Version extends com.dexels.navajo.version.AbstractVersion {

	@SuppressWarnings("rawtypes")
	private ServiceRegistration handler;

	private static BundleContext defaultContext = null;
	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		defaultContext = bc;
		System.err.println("Starting Navajo Rhino");
//		FunctionFactoryInterface fi= 
		FunctionFactoryFactory.getInstance();
//		RhinoRepository rp = new RhinoRepository();
//		RepositoryFactory.registerRepository(
//				"com.dexels.navajo.rhino.RhinoRepository", rp);
		RhinoHandler rhinoHandler = new RhinoHandler();
		HandlerFactory.registerHandler("com.dexels.navajo.rhino.RhinoHandler",
				rhinoHandler);
		
		 Dictionary<String, Object> wb = new Hashtable<String, Object>();
		 wb.put("handlerName", "com.dexels.navajo.rhino.RhinoHandler");
		 handler = context.registerService(ServiceHandler.class.getName() ,rhinoHandler, wb);

//		 Dictionary<String, Object> wb2 = new Hashtable<String, Object>();
//		 wb2.put("repositoryName", "com.dexels.navajo.rhino.RhinoRepository");
//		context.registerService(Repository.class,rp, wb);
	}
	
	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		if(handler!=null) {
			handler.unregister();
		}
		defaultContext = null;
	}
	
	public static BundleContext getDefaultContext() {
		return defaultContext;
	}
	
}
