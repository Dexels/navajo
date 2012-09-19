package tipi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.RepaintManager;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipiswing.SwingTipiApplicationInstance;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.application.InstallationPathResolver;
import com.dexels.navajo.tipi.components.swingimpl.TipiApplet;
import com.dexels.navajo.tipi.swingclient.components.CheckThreadViolationRepaintManager;

public class MainApplication {

	// TODO Remove evil static
	private static SwingTipiApplicationInstance myApplication = null;
	

	private final static Logger logger = LoggerFactory
			.getLogger(MainApplication.class);

	static public void main(String[] args) throws Exception {
		// TODO Refactor Formatters in NavajoFactory, so this can be done later.
		// TODO This is more urgent due to the OSGi loading
		TipiCoreExtension tce = new TipiCoreExtension();
		tce.loadDescriptor();
		tce.getTipiExtensionRegistry().registerTipiExtension(tce);
		TipiSwingExtension tse = new TipiSwingExtension();
		//tse.start(null);
		tse.loadDescriptor();
		tse.getTipiExtensionRegistry().registerTipiExtension(tse);
		
		SwingTipiApplicationInstance instance = runApp(args);
		instance.setInstallationFolder(new File("/Users/frank/Documents/workspace-indigo/SportlinkClub"));
		instance.getCurrentContext().switchToDefinition(
				instance.getDefinition());
	}

	public static SwingTipiApplicationInstance runApp(BundleContext bc, String applicationContext) throws IOException, TipiException {
		List<String> installationSettings = InstallationPathResolver.getInstallationFromPath(applicationContext);
		String installationPath = installationSettings.get(0);
		String applicationDeploy = null;
		String applicationProfile = null;
		if (installationSettings.size() > 1) {
			applicationDeploy = installationSettings.get(1);
		}
		if (installationSettings.size() > 2) {
			applicationProfile = installationSettings.get(2);
		}

		SwingTipiApplicationInstance stai = new SwingTipiApplicationInstance(bc);
//		public static void processSettings(String deploy, String profile,  File installationFolder, TipiContext context) throws IOException {
		File installationFolder = new File(installationPath);
		BaseTipiApplicationInstance.processSettings(applicationDeploy, applicationProfile, installationFolder, stai);
		stai.setInstallationFolder(installationFolder);
		return stai;
	}
	
	public static SwingTipiApplicationInstance runApp(BundleContext bc, String installationPath,String deploy,String profile) {
		SwingTipiApplicationInstance stai = new SwingTipiApplicationInstance(bc);
//		public static void processSettings(String deploy, String profile,  File installationFolder, TipiContext context) throws IOException {
		File installationFolder = new File(installationPath);
		logger.info("Using installationfolder: "+installationFolder.getAbsolutePath());
		BaseTipiApplicationInstance.processSettings(deploy, profile, installationFolder, stai);
		stai.setInstallationFolder(installationFolder);
		return stai;
	}
	
	public static SwingTipiApplicationInstance runApp( String[] args) {
		logger.debug("Startup NONOSGi");
		try {
			Locale.setDefault(new Locale("nl", "NL"));
		} catch (SecurityException se) {
			logger.debug("Sandbox. Using default locale");
		}

		List<String> arrrgs = new ArrayList<String>();

		String definition = null;
		for (int i = 0; i < args.length; i++) {
			arrrgs.add(args[i]);
		}
		if (args.length > 0) {
			if (args[args.length - 1].endsWith(".xml")) {
				definition = args[args.length - 1];

			}
		}
		return initializeSwingApplication(null,arrrgs, definition,
				null, null);
	}


	/**
	 * @param studioMode
	 * @param arrrgs
	 * @param def
	 */
	public static SwingTipiApplicationInstance initializeSwingApplication(
			final BundleContext bundleContext,
			final List<String> arrrgs,
			final String def, final TipiApplet appletRoot,
			final RootPaneContainer otherRoot) {

		RepaintManager
				.setCurrentManager(new CheckThreadViolationRepaintManager());
		try {
			if(SwingUtilities.isEventDispatchThread()) {
				logger.debug("Already in EDT. This is going to cause problems.");
			}
			SwingUtilities.invokeAndWait(new Runnable() {

				public void run() {
					try {
							if (def == null) {
								myApplication = new SwingTipiApplicationInstance(
										"init", "init.xml", arrrgs, appletRoot,
										otherRoot);
							} else {
								if (def.endsWith(".xml")) {
									myApplication = new SwingTipiApplicationInstance(
											"init", def, arrrgs, appletRoot,
											otherRoot);
								} else {
									myApplication = new SwingTipiApplicationInstance(
											def, "start.xml", arrrgs,
											appletRoot, otherRoot);
								}
							}
//						}
					// set bundle context, could be null, though
					myApplication.setBundleContext(bundleContext);
						if (myApplication != null) {
//							new TipiSwingExtension();
							
							myApplication.startup();
							String deff = def;
							if (deff == null) {
								deff = "init.xml";
							}

							// myApplication.getCurrentContext().switchToDefinition(def);
							// if(studioMode) {
							// ((SwingTipiContext)myApplication.getCurrentContext()).injectApplication(def,
							// arrrgs, "/init/tipi/sandbox");
							// }
						}

					} catch (Exception e) {
						logger.error("Error detected",e);
					}

				}
			});
		} catch (InterruptedException e) {
			logger.error("Error detected",e);
		} catch (InvocationTargetException e) {
			logger.error("Error detected",e);
		}

		return myApplication;
	}

	public static List<String> parseBundleFile(String path) throws IOException {
		File f = new File(path);
		InputStream openStream = new FileInputStream(f);
		return parseBundleStream(openStream);
	}

	public static List<String> parseBundleUrl(URL u) throws IOException {
		// URL u = new URL(path);
		InputStream openStream = u.openStream();
		return parseBundleStream(openStream);
	}

	/**
	 * Beware: Will close stream
	 * 
	 * @param openStream
	 * @return list of params.
	 * @throws IOException
	 */
	private static List<String> parseBundleStream(InputStream openStream)
			throws IOException {
		List<String> result = new LinkedList<String>();

		InputStreamReader appUrl = new InputStreamReader(openStream);
		BufferedReader br = new BufferedReader(appUrl);
		String line = null;
		do {
			line = br.readLine();
			if (line != null) {
				result.add(line);
			}
		} while (line != null);

		openStream.close();
		return result;
	}

}
