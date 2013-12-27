package tipi;

import java.io.Serializable;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.functions.TipiCoreFunctionDefinition;

public class TipiCoreExtension extends TipiAbstractXMLExtension implements Serializable {

	private static final long serialVersionUID = -1916256809513988908L;
//	private final Set<ServiceRegistration> functionRegs = new HashSet<ServiceRegistration>();

	public TipiCoreExtension() {
		super();
	}

	@Override
	public void initialize(TipiContext tc) {
		// Do nothing
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		registerTipiExtension(context);
		ExtensionDefinition extensionDef = new TipiCoreFunctionDefinition();
		registerAll(extensionDef);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		deregisterTipiExtension(context);
	}

}
