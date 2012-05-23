package tipi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiCssExtension extends TipiAbstractXMLExtension implements BundleActivator {

		private static final long serialVersionUID = 952751902858599229L;

		public TipiCssExtension() {
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
			deregisterTipiExtension(context);
		}

	}
