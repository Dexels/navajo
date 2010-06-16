package com.dexels.navajo.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public class AsyncClient {

	private HttpClient client;



	/**
	 * @param args
	 * @throws Exception 
	 */
	
	public AsyncClient() throws Exception {
		client = new HttpClient();
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		client.setMaxConnectionsPerAddress(200); // max 200 concurrent connections to every address
		client.setTimeout(30000); // 30 seconds timeout; if no server reply, the request expires
		client.start();
	}
	
	public void callService(String url, Navajo n, final NavajoResponseHandler continuation) throws IOException, NavajoException {
		final ContentExchange exchange = new ContentExchange() {

			@Override
			protected void onConnectionFailed(Throwable x) {
				super.onConnectionFailed(x);
				// TODO create 'error' navajo
				
			}

			@Override
			protected void onException(Throwable x) {
				super.onException(x);
				// TODO create 'error' navajo
			}

			@Override
			protected void onResponseComplete() throws IOException {
				super.onResponseComplete();
				// TODO: Add streaming?
				ByteArrayInputStream bais = new ByteArrayInputStream(getResponseContentBytes());
				Navajo response = NavajoFactory.getInstance().createNavajo(bais);
				continuation.onResponse(response);
			}

			@Override
			protected void onResponseContent(Buffer content) throws IOException {
				// TODO: Implement for streaming
				super.onResponseContent(content);
			}
			
		};
		exchange.setMethod("POST");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		n.write(baos);
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		exchange.setRequestContentSource(bais);
		exchange.setURL(url);
		client.send(exchange);
	}
	
	public Navajo createBlankNavajo(String service, String user, String password) {
		Navajo result = NavajoFactory.getInstance().createNavajo();
		result.addHeader(NavajoFactory.getInstance().createHeader(result, service, user, password, -1));
		return result;
	}
	
	public static void main(String[] args) throws Exception {

		AsyncClient ac = new AsyncClient();
		NavajoResponseHandler continuation = new NavajoResponseHandler() {
			@Override
			public void onResponse(Navajo n) {
				System.err.println("Navajo finished!");
				try {
					n.write(System.err);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}
		};;;
		ac.callService("http://spiritus.dexels.nl:9080/NavajoSportlink/Postman", ac.createBlankNavajo("club/InitUpdateClub", "ROOT", "ROOT"), continuation);
		// Optionally set the HTTP method
		 
		System.out.println("Exchange sent");
		// I suspect the http thread pool has daemon threads, so the vm will shut down if there are no 'real' threads.
		Thread.sleep(10000);
	}

}
