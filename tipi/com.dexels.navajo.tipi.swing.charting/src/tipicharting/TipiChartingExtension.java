/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipicharting;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiChartingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107021813164792108L;

	public TipiChartingExtension() throws XMLParseException {
// NO		loadDescriptor();
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
