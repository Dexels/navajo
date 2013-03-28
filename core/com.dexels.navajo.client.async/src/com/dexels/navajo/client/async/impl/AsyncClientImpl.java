package com.dexels.navajo.client.async.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;

public class AsyncClientImpl implements AsyncClient {

	private HttpClient client;

	private String server;
	private String username;
	private String password;

	private final static Logger logger = LoggerFactory.getLogger(AsyncClientImpl.class);
	
	private int actualCalls = 0;
	private static AsyncClient instance;

	private final static int CONNECT_TIMEOUT = 2000;
	private final static int READ_TIMEOUT = 300000;
	private final static int MAX_CONNECTIONS_PER_ADDRESS = 200;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getActualCalls()
	 */
	@Override
	public synchronized int getActualCalls() {
		return actualCalls;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setActualCalls(int)
	 */
	@Override
	public synchronized void setActualCalls(int actualCalls) {
		this.actualCalls = actualCalls;
		logger.debug("Calls now: "+this.actualCalls);
	}


	public static synchronized AsyncClient getInstance() {
		if (instance == null) {
			instance = new AsyncClientImpl();
		}
		return instance;
	}

	/**
	 * @param args
	 * @throws Exception
	 */

	public AsyncClientImpl(String server, String username, String password) throws Exception {
		this();
		this.server = server;
		this.username = username;
		this.password = password;
	}

	public AsyncClientImpl() {
		client = new HttpClient();
//		myThreadPool = new NavajoThreadPool();
//		client.setThreadPool(myThreadPool);
		client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
		client.setMaxConnectionsPerAddress(MAX_CONNECTIONS_PER_ADDRESS); // max 200 concurrent connections
																// to every address
		client.setTimeout(READ_TIMEOUT); // if no server reply, the
											// request expires
		client.setConnectTimeout(CONNECT_TIMEOUT);
		
		//client.setThreadPool(executor);
		try {
			client.start();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		logger.warn("Skipped the 'start' method call, it seems to have vanished. Don't know if it's a problem.");
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#callService(java.lang.String, com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
	public void callService(String service, final NavajoResponseHandler continuation) throws IOException, NavajoException {
		callService(null, service, continuation);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#callService(com.dexels.navajo.document.Navajo, java.lang.String, com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#callService(java.lang.String, java.lang.String, java.lang.String, com.dexels.navajo.document.Navajo, java.lang.String, com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
	public void callService(String url, String username, String password, Navajo input, String service, final NavajoResponseHandler continuation) throws IOException,
	NavajoException {
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		} else {
			input = input.copy();
		}
		input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));	
		callService(url, input, continuation);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#callService(com.dexels.navajo.server.Access, com.dexels.navajo.document.Navajo, java.lang.String, com.dexels.navajo.script.api.TmlRunnable, com.dexels.navajo.script.api.TmlRunnable, com.dexels.navajo.script.api.NavajoResponseCallback)
	 */
	@Override
	public void callService(Access inputAccess, Navajo input, final String service, final TmlRunnable onSuccess, final TmlRunnable onFail, final NavajoResponseCallback navajoResponseCallback)
			throws IOException, NavajoException {
		final Access currentAccess = inputAccess.cloneWithoutNavajos();
		
		
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
		setActualCalls(getActualCalls()+1);

		callService(currentAccess.getRequestUrl(), input, nrh);
	}

	private void callService(String url, Navajo n, final NavajoResponseHandler continuation) throws IOException, NavajoException {

		logger.debug("Calling service: " + n.getHeader().getRPCName());
		final ContentExchange exchange = new ContentExchange() {

			
			
			@Override
			protected void onExpire() {
				super.onExpire();
				try {
					continuation.onFail(null);
				} catch (IOException e) {
					logger.error("Error handling expired connection: ", e);
				} finally {
					setActualCalls(getActualCalls()-1);
				}

			}

			@Override
			protected void onRequestCommitted() throws IOException {
				logger.debug("Connection committed");
				super.onRequestCommitted();
			}

			@Override
			protected void onConnectionFailed(Throwable x) {
				logger.debug("Connection failed");
				super.onConnectionFailed(x);
				try {
					continuation.onFail(x);
				} catch (IOException e) {
					logger.error("Error handling connection: ", x);
				} finally {
					setActualCalls(getActualCalls()-1);
				}
			}

			@Override
			protected void onException(Throwable x) {
				logger.debug("Exception occurred");
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
				int status = super.getResponseStatus();
				if ( status != 200 ) {
					onException(new Exception("Could not open URL:" + server));
					return;
				}
				byte[] responseContentBytes = getResponseContentBytes();
				if(responseContentBytes!=null) {
					ByteArrayInputStream bais = new ByteArrayInputStream(responseContentBytes);
					try {
						Navajo response = NavajoFactory.getInstance().createNavajo(bais);
						if(continuation!=null) {
							continuation.onResponse(response);
						}
					} catch (RuntimeException e) {
						logger.info("Illegal TML detected:\n"+new String(responseContentBytes),e);
					} finally {
						setActualCalls(getActualCalls()-1);
					}
				} else {
					logger.debug("No response bytes detected!");
				}
			}

			@Override
			protected synchronized void onResponseContent(Buffer content) throws IOException {
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

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#createBlankNavajo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Navajo createBlankNavajo(String service, String user, String password) {
		Navajo result = NavajoFactory.getInstance().createNavajo();
		result.addHeader(NavajoFactory.getInstance().createHeader(result, service, user, password, -1));
		return result;
	}

	public static void main(String[] args) throws Exception {

		final AsyncClient ac = new AsyncClientImpl("http://spiritus.dexels.nl:9080/JsSportlink/Comet", "ROOT", "ROOT");
//		final AsyncClient ac = new AsyncClient("http://10.0.0.100:8080/JsSportlink/Comet", "ROOT", "ROOT");
//		final AsyncClient ac = new AsyncClient("http://localhost:9080/JsSportlink/Comet", "ROOT", "ROOT");
		
		final NavajoResponseHandler showOutput = new NavajoResponseHandler() {
			@Override
			public void onResponse(Navajo n) {
				logger.debug("Navajo finished!");
				try {
					logger.debug("Response2 ..");
					n.write(System.err);
				} catch (NavajoException e) {
					logger.error("Error: ", e);
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
				logger.debug("Navajo finished!");
				try {
					logger.debug("Response: ..");
					n.write(System.err);
					ac.callService(n, "person/ProcessSearchPersons", showOutput);
				} catch (NavajoException e) {
					logger.error("Error: ", e);
				} catch (IOException e) {
					logger.error("Error: ", e);
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

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getClient()
	 */
	@Override
	public HttpClient getClient() {
		return client;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setClient(org.eclipse.jetty.client.HttpClient)
	 */
	@Override
	public void setClient(HttpClient client) {
		this.client = client;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getServer()
	 */
	@Override
	public String getServer() {
		return server;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setServer(java.lang.String)
	 */
	@Override
	public void setServer(String server) {
		this.server = server;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

}
