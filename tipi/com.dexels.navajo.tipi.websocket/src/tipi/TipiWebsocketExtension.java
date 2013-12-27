package tipi;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.websocket.TipiWebsocketConnector;
import com.dexels.navajo.tipi.websocket.WebsocketSession;

@SuppressWarnings("unused")
public class TipiWebsocketExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 5014050975833573426L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWebsocketExtension.class);
	
	
	public TipiWebsocketExtension() {
	}
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
			TipiWebsocketConnector twt = new TipiWebsocketConnector(tc);
			final String sessionString = appstoreApplication+";"+appstoreTenant+";"+appstoreSession;
			twt.startup(uri,sessionString);
//			twt.sendMessage("tipi.appstore.session="+sessionRandom+"\n");
//			twt.sendMessage("tipi.appstore.application="+appstoreApplication+"\n");
//			twt.sendMessage("tipi.appstore.tenant="+appstoreTenant+"\n");
			XMLElement xe = new CaseSensitiveXMLElement();
			xe.setName("session");
			xe.setAttribute("session", appstoreSession);
			xe.setAttribute("application", appstoreApplication);
			xe.setAttribute("tenant", appstoreTenant);
//			twt.sendMessage(xe.toString());
		} catch (MalformedURLException e) {
			logger.error("Error: ", e);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		
	}

	
	public static void main(String[] args) throws Exception {
		URI uri = new URI("wss://club.sportlink.com/websocket");
		WebSocketClientFactory factory = new WebSocketClientFactory();
		factory.start();
		 WebSocketClient client = factory.newWebSocketClient();
		  Connection connection = client.open(uri, new WebSocket.OnTextMessage(){

			@Override
			public void onClose(int arg0, String arg1) {
				System.err.println("clooose");
			}

			@Override
			public void onOpen(Connection arg0) {
				System.err.println("oooopen!");
				
			}

			@Override
			public void onMessage(String s) {
				System.err.println("message received: "+s);
			}}).get(5, TimeUnit.SECONDS);
		   connection.sendMessage("club;knvb;braaap");
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
