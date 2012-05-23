package tipi;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.functions.MailFunctionDefinition;
import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.tipi.TipiContext;

public class TipiMailExtension extends TipiAbstractXMLExtension implements TipiExtension {


	private static final long serialVersionUID = -8495583222148257940L;
	private static TipiMailExtension instance = null;
	@SuppressWarnings("rawtypes")
	private final Set<ServiceRegistration> registrations = new HashSet<ServiceRegistration>();

	private final static Logger logger = LoggerFactory
			.getLogger(TipiMailExtension.class);
	
	public static TipiMailExtension getInstance() {
		return instance;
	}
	
	public TipiMailExtension() {
	}
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void start(BundleContext context) throws Exception {
		logger.info("Starting mail tipi bundle");
		registerTipiExtension(context);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		ExtensionDefinition extensionDef = new MailFunctionDefinition();
		fi.injectExtension(extensionDef);
		for (String functionName : fi.getFunctionNames(extensionDef)) {
			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
			 Dictionary<String, Object> props = new Hashtable<String, Object>();
			 props.put("functionName", functionName);
			 props.put("functionDefinition", fd);
			 ServiceRegistration sr  = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
			 registrations.add(sr);
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		for (ServiceRegistration sr : registrations) {
			sr.unregister();
		}
	}



}

