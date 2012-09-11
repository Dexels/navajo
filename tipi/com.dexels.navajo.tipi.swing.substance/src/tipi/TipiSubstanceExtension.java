package tipi;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.trident.TridentConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.swing.laf.api.LookAndFeelWrapper;

public class TipiSubstanceExtension extends TipiAbstractXMLExtension implements
		TipiExtension, BundleActivator {

	private static final long serialVersionUID = 5307279111204698579L;

	private final static Logger logger = LoggerFactory
			.getLogger(TipiSubstanceExtension.class);
	private ServiceRegistration<LookAndFeelWrapper> black;

	public TipiSubstanceExtension() {
		// loadDescriptor();
		UIManager.getLookAndFeelDefaults().put("ClassLoader",
				SubstanceBusinessBlackSteelLookAndFeel.class.getClassLoader());

	}

	public void initialize(TipiContext tc) {
		// Do nothing
	}

	@Override
	public void start(BundleContext context) throws Exception {
		ClassLoader tccl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(
					getClass().getClassLoader());

			TridentConfig.getInstance();
			registerTipiExtension(context);
			LookAndFeelWrapper lafw = new com.dexels.navajo.tipi.swing.laf.api.LookAndFeelWrapper() {

				@Override
				public void loadLookAndFeel() {
					SubstanceBusinessBlackSteelLookAndFeel s = new SubstanceBusinessBlackSteelLookAndFeel();
					UIManager.getLookAndFeelDefaults().put("ClassLoader",
							TipiSubstanceExtension.class.getClassLoader());
					UIManager.getDefaults();
					try {
						UIManager.setLookAndFeel(s);
					} catch (UnsupportedLookAndFeelException e) {
						logger.error(
								"Unexpected error while setting LookAndFeel", e);
					}
				}

				@Override
				public String getClassName() {
					return SubstanceBusinessBlackSteelLookAndFeel.class
							.getName();
				}
			};
			Dictionary<String, String> d = new Hashtable<String, String>();
			d.put("name", "BusinessBlackSteel");
			d.put("className",
					SubstanceBusinessBlackSteelLookAndFeel.class.getName());
			black = context.registerService(LookAndFeelWrapper.class, lafw, d);
		} finally {
			Thread.currentThread().setContextClassLoader(tccl);
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		black.unregister();
	}

}
