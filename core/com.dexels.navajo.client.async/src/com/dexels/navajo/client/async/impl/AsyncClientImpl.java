package com.dexels.navajo.client.async.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

import org.eclipse.jetty.client.ContentExchange;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.Access;

public class AsyncClientImpl implements ManualAsyncClient {

	private HttpClient client;

	private String name;
	private String server;
	private String username;
	private String password;

	private final static Logger logger = LoggerFactory.getLogger(AsyncClientImpl.class);
	
	private int actualCalls = 0;

	private final static int CONNECT_TIMEOUT = 2000;
	private final static int READ_TIMEOUT = 300000;
	private final static int MAX_CONNECTIONS_PER_ADDRESS = 200;
	
	private boolean useHttps = false;

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#getActualCalls()
	 */
	private synchronized int getActualCalls() {
		return actualCalls;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.async.AsyncClient#setActualCalls(int)
	 */
	private synchronized void setActualCalls(int actualCalls) {
		this.actualCalls = actualCalls;
		logger.debug("Calls now: "+this.actualCalls);
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
	 * @see com.dexels.navajo.client.async.AsyncClient#callService(com.dexels.navajo.document.Navajo, java.lang.String, com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
	public void callService(Navajo input, String service, final NavajoResponseHandler continuation) throws IOException {
		if(input==null) {
			input = NavajoFactory.getInstance().createNavajo();
		} else {
			input = input.copy();
		}
		input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));
		callService(server, input, continuation);
	}
	
	@Override
	public Navajo callService(final Navajo input, final String service) throws IOException{
		
		final Object semaphore = new Object();
		final Set<Navajo> result = new HashSet<Navajo>();
				
		NavajoResponseHandler nrh = new NavajoResponseHandler() {
			
			@Override
			public void onResponse(Navajo n) {
				result.add(n);
				synchronized (semaphore) {
					semaphore.notify();
				}
			}
			
			@Override
			public void onFail(Throwable t) throws IOException {
				logger.error("Problem calling navajo: ",t);
				synchronized (semaphore) {
					semaphore.notify();
				}
				
			}
		};
		callService(input, service, nrh);
		synchronized (semaphore) {
			try {
				semaphore.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result.iterator().next();
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
	// Only used from Rhino
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

	public static void main(String[] args) throws Exception {

		final ManualAsyncClient ac = new AsyncClientImpl();
//		final AsyncClient ac = new AsyncClient("http://10.0.0.100:8080/JsSportlink/Comet", "ROOT", "ROOT");
//		final AsyncClient ac = new AsyncClient("http://localhost:9080/JsSportlink/Comet", "ROOT", "ROOT");
//		ac.setServer("http://penelope1.dexels.com:90/sportlink/test/knvb/Postman");
		ac.setServer("https://source.dexels.com/test/Postman");
		ac.setUsername("ROOT");
		ac.setPassword("R20T");
//		ac.setClientCertificate(algorithm, type, is, password)
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
				logger.error("whoops: ",t);
			}
		};

		
//		NavajoResponseHandler testFields = 
			
		
				String service = "club/InitUpdateClub";
		
		Navajo input = NavajoFactory.getInstance().createNavajo();
		for (int i = 0; i < 10; i++) {
//			String service = "tests/InitNavajoMapTest3";
			ac.callService(input, service, showOutput);
			System.out.println("Exchange sent");
		}
		// Optionally set the HTTP method

		// I suspect the http thread pool has daemon threads, so the vm will shut
		// down if there are no 'real' threads.
		Thread.sleep(10000);
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


	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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

	@Override
	public void close() {
		client.destroy();
	}


	public boolean useHttps() {
		return useHttps;
	}

	public void setHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}

	/**
	 * set the SSL socket factory to use whenever an HTTPS call is made.
	 * @param algorithm, the algorithm to use, for example: SunX509
	 * @param type Type of the keystore, for example PKCS12 or JKS
	 * @param source InputStream of the client certificate, supply null to reset the socketfactory to default
	 * @param password the keystore password
	 */
	@Override
	public void setClientCertificate(String algorithm, String keyStoreType, InputStream source,
			char[] password) throws IOException {
		SslContextFactory socketFactory = new SslContextFactory(true);
		try {
			
			  KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			  try {
				keyStore.load(source, password);
			} finally {
				source.close();
			}
			logger.info("Key loaded successfully");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
			keyManagerFactory.init(keyStore, password);
			final KeyManager keyManager = keyManagerFactory.getKeyManagers()[0];
			System.err.println("mana: "+(keyManager));
//			
			client = new HttpClient(socketFactory);
			System.err.println("booom");
			client.start();
			return;
		} catch (UnrecoverableKeyException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (KeyManagementException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (NoSuchAlgorithmException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (KeyStoreException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (CertificateException e) {
			throw new IOException("Error loading certificate: ",e);
		} catch (Exception e) {
			throw new IOException("Error loading certificate: ",e);
		}
	}



}
