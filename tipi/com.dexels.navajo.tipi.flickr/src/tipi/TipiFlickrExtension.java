package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiFlickrExtension extends TipiAbstractXMLExtension  {

		private static final long serialVersionUID = 4369274770461702308L;

		public TipiFlickrExtension() {
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
			deregisterTipiExtension(context);
		}


	}
