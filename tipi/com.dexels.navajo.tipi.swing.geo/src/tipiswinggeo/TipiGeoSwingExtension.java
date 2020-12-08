/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipiswinggeo;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

import tipi.TipiAbstractXMLExtension;

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
