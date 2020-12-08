/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipi;

import java.io.Serializable;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.functions.TipiCoreFunctionDefinition;

import navajo.ExtensionDefinition;

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
