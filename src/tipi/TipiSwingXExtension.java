package tipi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiSwingXExtension extends TipiAbstractXMLExtension implements BundleActivator {

		public TipiSwingXExtension() {
//			loadDescriptor();
		}

		public void initialize(TipiContext tc) {
			// Do nothing

		}


		@Override
		public void start(BundleContext context) throws Exception {
			registerTipiExtension(context);
		}

		@Override
		public void stop(BundleContext context) throws Exception {
		}

	}
