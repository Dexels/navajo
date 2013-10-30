package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiGeoSwingExtension extends TipiAbstractXMLExtension  {

		private static final long serialVersionUID = 4050686070739379048L;

		public TipiGeoSwingExtension() {
		}

		@Override
		public void initialize(TipiContext tc) {
			// Do nothing

		}

		@Override
		public void start(BundleContext context) throws Exception {
			registerTipiExtension(context);
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			deregisterTipiExtension(context);
		}


	}
