/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipiecho;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiEchoExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = -6095356723833425401L;
	private static TipiEchoExtension instance;

	public TipiEchoExtension() throws XMLParseException, IOException {
		instance = this;
	}
	
	public static TipiEchoExtension getInstance() {
		return instance;
	}
 
	public void initialize(TipiContext tc) {

	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}


}
