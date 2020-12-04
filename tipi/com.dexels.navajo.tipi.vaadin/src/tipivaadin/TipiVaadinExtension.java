/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipivaadin;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.classdef.ClassManager;
import com.dexels.navajo.tipi.classdef.IClassManager;
import com.dexels.navajo.tipi.vaadin.functions.VaadinFunctionDefinition;

import navajo.ExtensionDefinition;
import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

public class TipiVaadinExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 6145737357886902779L;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinExtension.class); 
	
	private static TipiVaadinExtension instance = null;
	
	private transient BundleContext context;
	protected IClassManager classManager = null;
	
	private File installationFolder = null;
	
	public void initialializeExtension(File installationFolder) {
		// This method will be called multiple times. It should only be done one for every extension directory.
		// OTOH, OSGi should be safe for this and simply refuse to re-add an extension
		this.installationFolder = installationFolder;
		File extensions = new File(installationFolder, "extensions");
		TipiVaadinExtension.getInstance().installAllExtensions(extensions);
	}

	@Override
	public BundleContext getBundleContext() {
		return context;
	}

	public TipiVaadinExtension() 
			 {
		setVaadinExtension(this);
		loadDescriptor();
	}

	private static void setVaadinExtension(TipiVaadinExtension instance) {
		TipiVaadinExtension.instance = instance;
	}

	@Override
	public void start(BundleContext bc) throws Exception {
		this.context = bc;
		super.start(bc);
		classManager = new ClassManager(this.getClass().getClassLoader());
		registerTipiExtension(context);
		ExtensionDefinition extensionDef = new VaadinFunctionDefinition();
		registerAll(extensionDef);
		logger.debug("Started vaadin tipi bundle");
		FunctionFactoryFactory.getInstance().addFunctionResolver(classManager);

//		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
//		fi.injectExtension(extensionDef);
//		for (String functionName : fi.getFunctionNames(extensionDef)) {
//			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
//			 Dictionary<String, Object> props = new Hashtable<String, Object>();
//			 props.put("functionName", functionName);
//			 props.put("functionDefinition", fd);
//			 ServiceRegistration sr = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
//			 adapterRegs.add(sr);
//		}
//		
//	        props.put("Language", "English");
//	        context.registerService(
//	            DictionaryService.class.getName(), new DictionaryImpl(), props);
	}

	
	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		FunctionFactoryFactory.getInstance().removeFunctionResolver(classManager);
		
		super.stop(context);
	}
	
	@Override
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
