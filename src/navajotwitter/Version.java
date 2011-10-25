package navajotwitter;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.twitter.TwitterAdapterLibrary;

public class Version extends com.dexels.navajo.version.AbstractVersion implements BundleActivator {
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();

		TwitterAdapterLibrary library = new TwitterAdapterLibrary();
		fi.injectExtension(library);
		for(String adapterName: fi.getAdapterNames(library)) {
//			fi.getAdapterDefinition(name, ed)
			String adapterClass = fi.getAdapterClass(adapterName,library);
			Class<?> c = Class.forName(adapterClass);
			 Dictionary<String, Object> props = new Hashtable<String, Object>();
			 props.put("adapterName", adapterName);
			 props.put("adapterClass", c.getName());
			if(adapterClass!=null) {
				context.registerService(Class.class.getName(), c, props);
			}
		}
	}


	@Override
	public void shutdown() {
		super.shutdown();
	}
	

	public static void main(String[] args) {
		
	}
}
