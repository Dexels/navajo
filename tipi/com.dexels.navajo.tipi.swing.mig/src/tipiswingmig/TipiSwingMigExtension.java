package tipiswingmig;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiSwingMigExtension extends TipiAbstractXMLExtension  {

		private static final long serialVersionUID = 3084757226054543075L;

		public TipiSwingMigExtension() {
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
