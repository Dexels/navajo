package tipi;

import java.io.Serializable;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.functions.TipiCoreFunctionDefinition;

public class TipiCoreExtension extends TipiAbstractXMLExtension implements Serializable {

	private static final long serialVersionUID = -1916256809513988908L;
	@SuppressWarnings("rawtypes")
	private final Set<ServiceRegistration> functionRegs = new HashSet<ServiceRegistration>();

	public TipiCoreExtension() {
		super();
	}

	public void initialize(TipiContext tc) {
		// Do nothing
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
		// register as 'special service, so components can 'require' a core, as this is always the case.
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
		ExtensionDefinition extensionDef = new TipiCoreFunctionDefinition();
		fi.injectExtension(extensionDef);
		if(context!=null) {
			// OSGi only:
			for (String functionName : fi.getFunctionNames(extensionDef)) {
				FunctionDefinition fd = fi.getDef(extensionDef,functionName);
				 Dictionary<String, Object> props = new Hashtable<String, Object>();
				 props.put("functionName", functionName);
				 props.put("functionDefinition", fd);
				 ServiceRegistration sr = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
				 functionRegs.add(sr);
			}
			
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		for (ServiceRegistration sr : functionRegs) {
			sr.unregister();
		}	}

}
