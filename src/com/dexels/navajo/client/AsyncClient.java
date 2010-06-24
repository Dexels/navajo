package com.dexels.navajo.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.thread.ThreadPool;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public class AsyncClient {

	private HttpClient client;

//	private  final  ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

	private String server;
	private String username;
	private String password;
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	public AsyncClient(String server, String username, String password) throws Exception {
		this();
		this.server = server;
		this.username = username;
		this.password = password;
	}

	public AsyncClient()  {
		client = new HttpClient();
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		client.setMaxConnectionsPerAddress(200); // max 200 concurrent connections to every address
		client.setTimeout(30000); // 30 seconds timeout; if no server reply, the request expires
//		client.setThreadPool(executor);
		try {
			client.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void callService(String service, final NavajoResponseHandler continuation) throws IOException, NavajoException {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		callService(input,service, continuation);
	}

	public void callService(Navajo input, String service, final NavajoResponseHandler continuation) throws IOException, NavajoException {
		input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));
		callService(server, input, continuation);
	}
	
	private void callService(String url, Navajo n, final NavajoResponseHandler continuation) throws IOException, NavajoException {

		System.err.println("Calling service: "+n.getHeader().getRPCName());
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
				byte[] responseContentBytes = getResponseContentBytes();
//				System.err.println(":response:\n"+new String(responseContentBytes));
				ByteArrayInputStream bais = new ByteArrayInputStream(responseContentBytes);
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
		
		AsyncClient ac = new AsyncClient("http://spiritus.dexels.nl:9080/NavajoSportlink/Postman","ROOT","ROOT");
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
		ac.callService("club/InitUpdateClub", continuation);
		// Optionally set the HTTP method
		 
		System.out.println("Exchange sent");
		// I suspect the http thread pool has daemon threads, so the vm will shut down if there are no 'real' threads.
		Thread.sleep(10000);
	}

	public HttpClient getClient() {
		return client;
	}

	public void setClient(HttpClient client) {
		this.client = client;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
