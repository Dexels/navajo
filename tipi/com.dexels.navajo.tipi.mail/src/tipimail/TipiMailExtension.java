/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipimail;

import navajo.ExtensionDefinition;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.mail.functions.MailFunctionDefinition;

public class TipiMailExtension extends TipiAbstractXMLExtension implements TipiExtension {


	private static final long serialVersionUID = -8495583222148257940L;
	private static TipiMailExtension instance = null;
	
	public static TipiMailExtension getInstance() {
		return instance;
	}
	
	public TipiMailExtension() {
	}
	@Override
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		ExtensionDefinition extensionDef = new MailFunctionDefinition();
		registerTipiExtension(context);
		registerAll(extensionDef);
//		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
//		fi.injectExtension(extensionDef);
//		for (String functionName : fi.getFunctionNames(extensionDef)) {
//			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
//			 Dictionary<String, Object> props = new Hashtable<String, Object>();
//			 props.put("functionName", functionName);
//			 props.put("functionDefinition", fd);
//			 ServiceRegistration sr  = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
//			 registrations.add(sr);
//		}
//		TestMail.main(new String[]{});
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
	}



}

