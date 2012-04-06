package navajorhinocontinuations;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.adapter.RhinoAdapterLibrary;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.rhino.RhinoHandler;
import com.dexels.navajo.server.HandlerFactory;
import com.dexels.navajo.server.ServiceHandler;

public class Version extends com.dexels.navajo.version.AbstractVersion {

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		RhinoAdapterLibrary library = new RhinoAdapterLibrary();
		fi.injectExtension(library);
		for(String adapterName: fi.getAdapterNames(library)) {
//			fi.getAdapterDefinition(name, ed)
			String adapterClass = fi.getAdapterClass(adapterName,library);
			Class<?> c = Class.forName(adapterClass);
			 Dictionary<String, Object> props = new Hashtable<String, Object>();
			 props.put("adapterName", adapterName);
			 props.put("extension", library.getId());
			 props.put("adapterClass", c.getName());
			if(adapterClass!=null) {
				context.registerService(Class.class.getName(), c, props);
			}
		}
	
	}
}
