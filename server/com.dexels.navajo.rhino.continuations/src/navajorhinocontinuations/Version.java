package navajorhinocontinuations;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.adapter.RhinoAdapterLibrary;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;

public class Version extends com.dexels.navajo.version.AbstractVersion {

	@SuppressWarnings("rawtypes")
	private final Set<ServiceRegistration> adapterRegs = new HashSet<ServiceRegistration>();
	@SuppressWarnings("rawtypes")
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
				ServiceRegistration sr = context.registerService(Class.class.getName(), c, props);
				adapterRegs.add(sr);
			}
		}
	
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void stop(BundleContext bc) throws Exception {
		for (ServiceRegistration sr :adapterRegs) {
			sr.unregister();
		}
		super.stop(bc);
	}
	
	
}
