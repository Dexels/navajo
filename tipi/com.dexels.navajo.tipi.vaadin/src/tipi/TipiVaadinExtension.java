package tipi;

import java.io.File;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.functions.VaadinFunctionDefinition;
import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.tipi.TipiContext;

public class TipiVaadinExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 6145737357886902779L;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinExtension.class); 
	
	private static TipiVaadinExtension instance = null;
	
	private transient BundleContext context;
	
	private File installationFolder = null;
	
	@SuppressWarnings("rawtypes")
	private final Set<ServiceRegistration> adapterRegs = new HashSet<ServiceRegistration>();

		
	public void initialializeExtension(File installationFolder) {
		// This method will be called multiple times. It should only be done one for every extension directory.
		// OTOH, OSGi should be safe for this and simply refuse to re-add an extension
		this.installationFolder = installationFolder;
		File extensions = new File(installationFolder, "extensions");
		TipiVaadinExtension.getInstance().installAllExtensions(extensions);
	}

	public BundleContext getBundleContext() {
		return context;
	}

	public TipiVaadinExtension() 
			 {
		instance = this;
		loadDescriptor();
	}

	@SuppressWarnings("rawtypes")
	public void start(BundleContext bc) throws Exception {
		this.context = bc;
		logger.info("Starting vaadin tipi bundle");
		registerTipiExtension(context);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		ExtensionDefinition extensionDef = new VaadinFunctionDefinition();
		fi.injectExtension(extensionDef);
		for (String functionName : fi.getFunctionNames(extensionDef)) {
			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
			 Dictionary<String, Object> props = new Hashtable<String, Object>();
			 props.put("functionName", functionName);
			 props.put("functionDefinition", fd);
			 ServiceRegistration sr = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
			 adapterRegs.add(sr);
		}
		
//	        props.put("Language", "English");
//	        context.registerService(
//	            DictionaryService.class.getName(), new DictionaryImpl(), props);
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		for (ServiceRegistration s : adapterRegs) {
			s.unregister();
		}
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}
	public static TipiVaadinExtension getInstance() {
		return instance;
	}

	public File getInstallationFolder() {
		return this.installationFolder;
	}
	
	
//
//	@Override
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		extensionContext = context;
//	}

}
