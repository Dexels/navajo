package com.dexels.navajo.client.async;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;

public class AsyncClient {

	private HttpClient client;

	private String server;
	private String username;
	private String password;

	private final static Logger logger = LoggerFactory.getLogger(AsyncClient.class);
	
	private int actualCalls = 0;
	private static AsyncClient instance;

	
	public synchronized int getActualCalls() {
		return actualCalls;
	}

	public synchronized void setActualCalls(int actualCalls) {
		this.actualCalls = actualCalls;
		logger.debug("Calls now: "+this.actualCalls);
	}


	public static AsyncClient getInstance() {
		if (instance == null) {
			instance = new AsyncClient();
		}
		return instance;
	}

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

	public AsyncClient() {
		client = new HttpClient();
//		myThreadPool = new NavajoThreadPool();
//		client.setThreadPool(myThreadPool);
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		client.setMaxConnectionsPerAddress(200); // max 200 concurrent connections
																// to every address
		client.setTimeout(30000); // 30 seconds timeout; if no server reply, the
											// request expires
		// client.setThreadPool(executor);
//		try {
//			client.start();
//		} catch (Exception e) {
//			logger.error("Error: ", e);
//		}
		logger.warn("Skipped the 'start' method call, it seems to have vanished. Don't know if it's a problem.");
	}

	public void callService(String service, final NavajoResponseHandler continuation) throws IOException, NavajoException {
		callService(null, service, continuation);
	}

	public void callService(Navajo input, String service, final NavajoResponseHandler continuation) throws IOException,
			NavajoException {
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		} else {
			input = input.copy();
		}
		input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));
		callService(server, input, continuation);
	}

	public void callService(Access inputAccess, Navajo input, final String service, final TmlRunnable onSuccess, final TmlRunnable onFail, final NavajoResponseCallback navajoResponseCallback)
			throws IOException, NavajoException {
		final Access currentAccess = inputAccess.cloneWithoutNavajos();
		
		
		
		NavajoClientFactory.getClientLogger().logInput(service, input);
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		currentAccess.setInDoc(input);
		Header header = input.getHeader();
		if(header==null) {
			header = NavajoFactory.getInstance().createHeader(input, service, currentAccess.rpcUser, currentAccess.rpcUser, -1);
			input.addHeader(header);
		}
		header.setRPCName(service);
		header.setRPCUser(currentAccess.rpcUser);
		header.setRPCPassword(currentAccess.rpcPwd);
		NavajoResponseHandler nrh = new NavajoResponseHandler() {

			@Override
			public void onResponse(Navajo n) {
				setActualCalls(getActualCalls()-1);
				NavajoClientFactory.getClientLogger().logOutput(service, n);
				currentAccess.setOutputDoc(n);
				try {
					if (onSuccess != null) {
						onSuccess.setResponseNavajo(n);
						if(navajoResponseCallback!=null) {
							navajoResponseCallback.responseReceived(n);
						}
						setActualCalls(getActualCalls()-1);
						SchedulerRegistry.getScheduler().submit(onSuccess, false);
					}
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onFail(Throwable t) throws IOException {
				setActualCalls(getActualCalls()-1);
				try {
					if (onFail != null) {
						SchedulerRegistry.getScheduler().submit(onFail, false);
					}
				} catch (IOException e) {
					logger.error("Error: ", e);
				} finally {
					setActualCalls(getActualCalls()-1);
				}
			}
		};
		NavajoClientFactory.getClientLogger().logInput(service, input);
		setActualCalls(getActualCalls()+1);

		callService(currentAccess.getRequestUrl(), input, nrh);
	}

	private void callService(String url, Navajo n, final NavajoResponseHandler continuation) throws IOException, NavajoException {

		System.err.println("Calling service: " + n.getHeader().getRPCName());
		final ContentExchange exchange = new ContentExchange() {

			
			
			@Override
			protected void onExpire() {
				super.onExpire();
				try {
					continuation.onFail(null);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					setActualCalls(getActualCalls()-1);
				}

			}

			@Override
			protected void onRequestCommitted() throws IOException {
				System.err.println("Connection committed");
				super.onRequestCommitted();
			}

			@Override
			protected void onConnectionFailed(Throwable x) {
				System.err.println("Connection failed");
				super.onConnectionFailed(x);
				try {
					continuation.onFail(x);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					setActualCalls(getActualCalls()-1);
				}
			}

			@Override
			protected void onException(Throwable x) {
				System.err.println("Exception occurred");
				super.onException(x);
				try {
					continuation.onFail(x);
				} catch (IOException e) {
					logger.error("Error: ", e);
				} finally {
					setActualCalls(getActualCalls()-1);
				}
				// TODO create 'error' navajo
			}

			@Override
			protected void onResponseComplete() throws IOException {
				super.onResponseComplete();
				// TODO: Add streaming?
//				int status = super.getResponseStatus();
				byte[] responseContentBytes = getResponseContentBytes();
				if(responseContentBytes!=null) {
					ByteArrayInputStream bais = new ByteArrayInputStream(responseContentBytes);
					try {
						Navajo response = NavajoFactory.getInstance().createNavajo(bais);
						if(continuation!=null) {
							continuation.onResponse(response);
						}
					} catch (RuntimeException e) {
						e.printStackTrace();
						System.err.println("Illegal TML detected:\n"+new String(responseContentBytes));
					} finally {
						setActualCalls(getActualCalls()-1);
					}
				} else {
					System.err.println("No response bytes detected!");
				}
			}

			@Override
			protected void onResponseContent(Buffer content) throws IOException {
				// TODO: Implement for streaming
//				HttpFields a = getRequestFields();
				super.onResponseContent(content);
			}

		};
		exchange.setMethod("POST");
		exchange.addRequestHeader("Connection", "Keep-alive");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		n.write(baos);
		byte[] byteArray = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		exchange.setRequestContentSource(bais);
		exchange.setURL(url);
		setActualCalls(getActualCalls()+1);
		client.send(exchange);
	}

	public Navajo createBlankNavajo(String service, String user, String password) {
		Navajo result = NavajoFactory.getInstance().createNavajo();
		result.addHeader(NavajoFactory.getInstance().createHeader(result, service, user, password, -1));
		return result;
	}

	public static void main(String[] args) throws Exception {

		final AsyncClient ac = new AsyncClient("http://spiritus.dexels.nl:9080/JsSportlink/Comet", "ROOT", "ROOT");
//		final AsyncClient ac = new AsyncClient("http://10.0.0.100:8080/JsSportlink/Comet", "ROOT", "ROOT");
//		final AsyncClient ac = new AsyncClient("http://localhost:9080/JsSportlink/Comet", "ROOT", "ROOT");
		
		final NavajoResponseHandler showOutput = new NavajoResponseHandler() {
			@Override
			public void onResponse(Navajo n) {
				System.err.println("Navajo finished!");
				try {
					System.err.println("Response2 ..");
					n.write(System.err);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFail(Throwable t) {

			}
		};

		
//		NavajoResponseHandler testFields = 
			
			new NavajoResponseHandler() {
			@Override
			public void onResponse(Navajo n) {
				System.err.println("Navajo finished!");
				try {
					System.err.println("Response: ..");
					n.write(System.err);
					ac.callService(n, "person/ProcessSearchPersons", showOutput);
				} catch (NavajoException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFail(Throwable t) {

			}
		};

//		String service = "InitTestBean";
		
				String service = "tests/InitSleepMap";
//				String service = "person/InitSearchPersons";
		
		
		for (int i = 0; i < 100; i++) {
//			String service = "tests/InitNavajoMapTest3";
			ac.callService(service, showOutput);
			System.out.println("Exchange sent");
		}
		// Optionally set the HTTP method

		// I suspect the http thread pool has daemon threads, so the vm will shut
		// down if there are no 'real' threads.
		Thread.sleep(10000);
//		ac.
//		ac.myThreadPool.rootPool.shutdownScheduler();
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
