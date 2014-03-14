package tipiswingrich;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;

import com.dexels.navajo.tipi.TipiContext;

	public class TipiRichExtension extends TipiAbstractXMLExtension  {

		private static final long serialVersionUID = 1814273515558897341L;

		public TipiRichExtension() {
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
