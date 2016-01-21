package tipiwebsocket;

import java.net.MalformedURLException;
import java.net.URI;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
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

	
	public static void main(String[] args) throws Exception {
		URI uri = new URI("ws://localhost:8080/websocket");
        TipiWebSocket socket = new TipiWebSocket(uri,"blib;blab;blob",null);
        try {
//            URI echoUri = new URI(destUri);
            System.out.printf("Connecting to : %s%n", uri);
//            socket.awaitClose(5, TimeUnit.SECONDS);
            Thread.sleep(20000);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
//            try {
//                client.stop();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
//		WebSocketClientFactory factory = new WebSocketClientFactory();
//		factory.start();
//		 WebSocketClient client = factory.newWebSocketClient();
//		  Connection connection = client.open(uri, new WebSocket.OnTextMessage(){
//
//			@Override
//			public void onClose(int arg0, String arg1) {
//				System.err.println("clooose");
//			}
//
//			@Override
//			public void onOpen(Connection arg0) {
//				System.err.println("oooopen!");
//				
//			}
//
//			@Override
//			public void onMessage(String s) {
//				System.err.println("message received: "+s);
//			}}).get(5, TimeUnit.SECONDS);
//		   connection.sendMessage("club;knvb;braaap");
//	}
	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
	}
}
