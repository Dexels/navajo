package tipiswing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.RootPaneContainer;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.BaseTipiApplicationInstance;
import tipi.TipiExtension;
import tipipackage.ITipiExtensionContainer;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actionmanager.OSGiActionManager;
import com.dexels.navajo.tipi.classdef.OSGiClassManager;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiContext;
import com.dexels.navajo.tipi.components.swingimpl.SwingTipiUserInterface;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.components.swingimpl.TipiScreen;
import com.dexels.navajo.tipi.internal.FileResourceLoader;
import com.dexels.navajo.tipi.swingclient.SwingClient;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class SwingTipiApplicationInstance extends BaseTipiApplicationInstance implements ITipiExtensionContainer {

	private String definition;

	public String getDefinition() {
		return definition;
	}

	private String definitionPath;
	private List<String> args;
	private TipiApplet appletRoot;
	private RootPaneContainer otherRoot;
	private BundleContext bundleContext;

	private File tipiInstallationFolder = null;
	private final Map<String,String> systemProperties = new HashMap<String, String>();
	
	private final static Logger logger = LoggerFactory.getLogger(SwingTipiApplicationInstance.class);
	
	public SwingTipiApplicationInstance(String definition,
			String definitionPath, List<String> args, TipiApplet appletRoot,
			RootPaneContainer otherRoot) {
		this.definition = definition;
		this.definitionPath = definitionPath;
		this.args = args;
		this.appletRoot = appletRoot;
		this.otherRoot = otherRoot;
	}

	public SwingTipiApplicationInstance(BundleContext bc) {
//		this.applicationContext = applicationContext;
		this.definition = "init";
		this.definitionPath="start.xml";
//		osgiWhiteBoard = true;
		setBundleContext(bc);
	}
	
	public void dispose(TipiContext t) {
		super.dispose(t);
		System.exit(0);
	}

	public TipiContext createContext() throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		Map<String, String> properties = parseProperties(args);

		if (definitionPath == null) {
			definitionPath = definition;
		}
		SwingTipiContext context = null;

// in non-osgi land:
		if(bundleContext==null) {
			List<TipiExtension> ll = TipiSwingExtension.getInstance().getTipiExtensionRegistry().getExtensionList();
			context = new SwingTipiContext(this,ll, null,properties);
		} else {
			context = new SwingTipiContext(this,new ArrayList<TipiExtension>(), null,properties);
		}
		
//		context.setBundleContext(this.bundleContext);
		// assuming OSGi mode, I guess?
		if(this.bundleContext!=null) {
//			context.setOSGiMode(true);
			context.setClassManager(new OSGiClassManager(this.bundleContext, context));
			context.setActionManager(new OSGiActionManager(this.bundleContext));
		}

		context.setAppletRoot(appletRoot);
		context.setOtherRoot(otherRoot);

		SwingTipiUserInterface stui = new SwingTipiUserInterface(context);
		SwingClient.setUserInterface(stui);
		context.setDefaultTopLevel(new TipiScreen());
		context.getDefaultTopLevel().setContext(context);
		// context.parseRequiredIncludes();
		// context.processRequiredIncludes();


		context.systemPropertyMap.putAll(systemProperties);
		context.processProperties(properties);

		if(tipiInstallationFolder!=null) {
			logger.info("Using install: "+tipiInstallationFolder.getAbsolutePath());

			
			logger.debug("Systemprop: "+context.systemPropertyMap);
			// TODO Fix support for HTTP based runs
			logger.warn("********** ENTERING FILE BASED MODE **********");
			File tipi = new File(tipiInstallationFolder,"tipi");
			File resource = new File(tipiInstallationFolder,"resource");
			context.setTipiResourceLoader(new FileResourceLoader(tipi));
			context.setGenericResourceLoader(new FileResourceLoader(resource));
		}

		
		//		BaseTipiApplicationInstance.processSettings(deploy, profile, installationFolder, extensionContainer)
		
//		TipiSwingExtension tse = new TipiSwingExtension();
//		tse.initialize(context);

		InputStream tipiResourceStream = context
				.getTipiResourceStream(definitionPath);
		if (tipiResourceStream == null) {
			logger.warn("Error starting up: Can not load tipi. Resource not found: "
							+ definitionPath);
			logger.debug("Codebase: " + context.getTipiResourceLoader());
			String fatalErrorMsg = "No connection allowed to server by security software, check your connection and security settings.";
			try {
				String msg = System.getProperty("fatalSystemErrorMessage");
				if (msg != null) {
					fatalErrorMsg = msg;
				}
			} catch (SecurityException e) {
				logger.error("Error detected",e);
			}
			tipiResourceStream = context.getTipiResourceStream("init.xml");
			if (tipiResourceStream == null) {
				logger.debug("Still failed");
				context.showFatalStartupError(fatalErrorMsg);
				context.shutdown();
			} else {
				logger.debug("recovered");
			}
		} else {
			try {
				context.parseStream(tipiResourceStream, null);
				// context.switchToDefinition(definition);
			} catch (XMLParseException e) {
				logger.error("Error detected",e);
			} catch (TipiException e) {
				logger.error("Error detected",e);
			}
		}
		return context;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public void addOptionalInclude(TipiExtension te) {
		
	}

	@Override
	public void setSystemProperty(String key, String value) {
		logger.info("Adding system property (FAKE!): "+key+" value: "+value);
		systemProperties.put(key, value);
	}

	public void setInstallationFolder(File installationFolder) {
		this.tipiInstallationFolder = installationFolder;
	}

	public File getInstallationFolder() {
		return this.tipiInstallationFolder;
	}

	@Override
	public void setEvalUrl(URL context, String relativeUri) {
		
	}

	@Override
	public void setContextUrl(URL contextUrl) {
		throw new UnsupportedOperationException("Not implemented just yet");
	}

	@Override
	public URL getContextUrl() {
		throw new UnsupportedOperationException("Not implemented just yet");
	}

	@Override
	public Locale getLocale() {
		return null;
	}

	@Override
	public void close() {
		logger.warn("Close requested in SwingTipiApplication instance. (but ignored");
	}

}
