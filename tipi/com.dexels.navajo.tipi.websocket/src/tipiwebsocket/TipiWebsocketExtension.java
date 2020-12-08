/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipiwebsocket;

import java.net.MalformedURLException;
import java.net.URI;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.websocket.TipiWebSocket;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

public class TipiWebsocketExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 5014050975833573426L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWebsocketExtension.class);
	
	
	public TipiWebsocketExtension() {
	}
	@SuppressWarnings("unused")
	@Override
	public void initialize(TipiContext tc) {
		// Do nothing
		String appstoreUrl = tc.getSystemProperty("tipi.appstore.websocketurl");
		String appstoreSession =  SessionTokenFactory.getSessionTokenProvider().getSessionToken(); // tc.getSystemProperty("tipi.appstore.session");
		String appstoreApplication =  tc.getSystemProperty("tipi.appstore.application");
		String appstoreTenant =  tc.getSystemProperty("tipi.appstore.tenant");
		

		try {
			URI uri = new URI(appstoreUrl);
			logger.info("Connecting to: "+appstoreUrl);
			final String sessionString = appstoreApplication+";"+appstoreTenant+";"+appstoreSession;
	        TipiWebSocket socket = new TipiWebSocket(uri,sessionString, tc);

		} catch (MalformedURLException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		
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
