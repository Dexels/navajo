package tipi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.css.CssFactory;
import com.dexels.navajo.tipi.css.impl.CssComponentResponderImpl;

	public class TipiCssExtension extends TipiAbstractXMLExtension implements BundleActivator {

		private static final long serialVersionUID = 952751902858599229L;

		public TipiCssExtension() {
		}

		@Override
		public void initialize(TipiContext tc) {
//			ApplyCss componentInstantiatedListener = new ApplyCss();
//			componentInstantiatedListener.setContext(tc);
//			tc.addComponentInstantiatedListener(componentInstantiatedListener);
			final CssComponentResponderImpl tcil = new CssComponentResponderImpl(tc);
			CssFactory.setInstance(tcil);
			tc.addComponentInstantiatedListener(tcil);

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
