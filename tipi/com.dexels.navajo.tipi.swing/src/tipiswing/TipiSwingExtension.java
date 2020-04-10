package tipiswing;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.swing.functions.TipiSwingFunctionDefinition;
import com.dexels.navajo.tipi.swing.laf.api.LookAndFeelWrapper;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

import navajo.ExtensionDefinition;
import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;
import tipi.TipiMainExtension;

public class TipiSwingExtension extends TipiAbstractXMLExtension implements
		TipiExtension,TipiMainExtension {

	private static final long serialVersionUID = 3083008630338044274L;

	private static TipiSwingExtension instance = null;

	private static final Logger logger = LoggerFactory.getLogger(TipiSwingExtension.class);


	public TipiSwingExtension() throws XMLParseException {
		instance = this;
	}


	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		registerTipiExtension(context);
		ExtensionDefinition extensionDef = new TipiSwingFunctionDefinition();
		registerAll(extensionDef);
//		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
//		fi.injectExtension(extensionDef);
//		if(context!=null) {
//			// OSGi only:
//			for (String functionName : fi.getFunctionNames(extensionDef)) {
//				FunctionDefinition fd = fi.getDef(extensionDef,functionName);
//				 Dictionary<String, Object> props = new Hashtable<String, Object>();
//				 props.put("functionName", functionName);
//				 props.put("functionDefinition", fd);
//				 ServiceRegistration sr = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
//				 functionRegs.add(sr);
//			}
//
//		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		deregisterTipiExtension(context);
	}

	public static TipiSwingExtension getInstance() {
		return instance;
	}

	@Override
	public void initialize(TipiContext tc) {
		// Do nothing

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LookAndFeelWrapper getLookAndFeel(String tipiLaf) {
		try {
			ServiceReference[] srl = getBundleContext().getServiceReferences(LookAndFeelWrapper.class.getName(), "(className="+tipiLaf+")");

			if(srl == null || srl.length==0) {
				return null;
			}
			ServiceReference laf = srl[0];
			return (LookAndFeelWrapper)getBundleContext().getService(laf);
		} catch (InvalidSyntaxException e) {
			logger.error("Odd OSGi error:",e);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setLookAndFeel(String tipiLaf) {
		if(getBundleContext()==null) {
			// not OSGi, go vintage:
			try {
				Class<LookAndFeel> lafClass = (Class<LookAndFeel>) Class.forName(tipiLaf);
				logger.info("lafclass resolved");
				LookAndFeel laf = lafClass.newInstance();
				UIManager.setLookAndFeel(laf);
			} catch (ClassNotFoundException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (InstantiationException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (IllegalAccessException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
			} catch (UnsupportedLookAndFeelException e) {
				logger.error("Error setting look and feel: "+tipiLaf,e);
            } catch (NoClassDefFoundError e) {
                logger.error("Error setting look and feel: " + tipiLaf, e);
			}
			return;
		}
		try {
			ServiceReference[] srl = getBundleContext().getServiceReferences(LookAndFeelWrapper.class.getName(), "(className="+tipiLaf+")");

			if(srl == null || srl.length==0) {
				logger.warn("No service found for LNF: "+tipiLaf);
				return;
			}
			ServiceReference laf = srl[0];

			LookAndFeelWrapper lwd = (LookAndFeelWrapper)getBundleContext().getService(laf);

			lwd.loadLookAndFeel();
		} catch (InvalidSyntaxException e) {
			logger.error("Odd OSGi error:",e);
		}
	}

}
