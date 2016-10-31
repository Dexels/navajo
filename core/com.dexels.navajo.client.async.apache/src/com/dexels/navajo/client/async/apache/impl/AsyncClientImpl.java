package com.dexels.navajo.client.async.apache.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;

public class AsyncClientImpl implements ManualAsyncClient {

	private CloseableHttpAsyncClient client;

	private String name;
	private String server;
	private String username;
	private String password;
	
	private boolean closeAfterUse = false;

	private final static Logger logger = LoggerFactory
			.getLogger(AsyncClientImpl.class);

	private int actualCalls = 0;

//	private final static int CONNECT_TIMEOUT = 2000;
//	private final static int MAX_CONNECTIONS_PER_ADDRESS = 200;

	private boolean useHttps = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.client.async.AsyncClient#getActualCalls()
	 */
	private synchronized int getActualCalls() {
		return actualCalls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.client.async.AsyncClient#setActualCalls(int)
	 */
	private synchronized void setActualCalls(int actualCalls) {
		this.actualCalls = actualCalls;
		logger.debug("Calls now: " + this.actualCalls);
	}
	
	static {
        AsyncClientFactory.setInstance(AsyncClientImpl.class);
    }

	public AsyncClientImpl() {
		// myThreadPool = new NavajoThreadPool();
		// client.setThreadPool(myThreadPool);
		// client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
//		final SslContextFactory factory = new SslContextFactory();
//		client = new HttpClient();
//		client = HttpAsyncClients.createSystem();
		client = HttpAsyncClients.createDefault();
		 // max 200 concurrent connections to every address
//		client.setMaxConnectionsPerDestination(MAX_CONNECTIONS_PER_ADDRESS);

//		client.setConnectTimeout(CONNECT_TIMEOUT);
		// client.setThreadPool(executor);
		try {
			client.start();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		
	}
	
	

	public void activate(Map<String, Object> settings) {
	    closeAfterUse = false;
		String serverString = (String) settings.get("server");
		if (serverString == null) {
			serverString = (String) settings.get("url");
		}
		setServer(serverString);
		setUsername((String) settings.get("username"));
		setPassword((String) settings.get("password"));
		setName((String) settings.get("name"));
	}

	public void deactivate() {
	    close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#callService(com.dexels.navajo
	 * .document.Navajo, java.lang.String,
	 * com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
	public void callService(Navajo input, String service,
			final NavajoResponseHandler continuation) throws IOException {
		if (input == null) {
			input = NavajoFactory.getInstance().createNavajo();
		} else {
			input = input.copy();
		}
		input.addHeader(NavajoFactory.getInstance().createHeader(input,
				service, username, password, -1));
		callService(server, input, continuation, null);
	}

	@Override
	public Navajo callService(final Navajo input, final String service)
			throws IOException {

		final Object semaphore = new Object();
		final Set<Navajo> result = new HashSet<Navajo>();

		NavajoResponseHandler nrh = new NavajoResponseHandler() {
			Throwable caughtException = null;

			@Override
			public Throwable getCaughtException() {
				synchronized (semaphore) {
					return caughtException;
				}
			}

			@Override
			public void onResponse(Navajo n) {
				result.add(n);
				synchronized (semaphore) {
					semaphore.notify();
				}
			}

			@Override
			public void onFail(Throwable t) throws IOException {
				logger.error("Problem calling navajo: ", t);
				synchronized (semaphore) {
					caughtException = t;
					semaphore.notify();
				}

			}
		};
		callService(input, service, nrh);
		synchronized (semaphore) {
			try {
				while (result.isEmpty() && nrh.getCaughtException() == null) {
					semaphore.wait();
				}
			} catch (InterruptedException e) {
				logger.debug("Error: ", e);
			}
		}
		if (nrh.getCaughtException() != null) {
			throw new IOException("Error calling remote navajo: " + server,
					nrh.getCaughtException());
		}
		return result.iterator().next();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#callService(java.lang.String,
	 * java.lang.String, java.lang.String, com.dexels.navajo.document.Navajo,
	 * java.lang.String, com.dexels.navajo.client.NavajoResponseHandler)
	 */
	@Override
	public void callService(String url, String username, String password,
			Navajo input, String service,
			final NavajoResponseHandler continuation, Integer timeout) throws IOException,
			NavajoException {
		logger.info("Calling remote navajo async for url: {} ",url);
		if (input == null) {
			input = NavajoFactory.getInstance().createNavajo();
		} else {
			input = input.copy();
		}
		input.addHeader(NavajoFactory.getInstance().createHeader(input,
				service, username, password, -1));
		callService(url, input, continuation, timeout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#callService(com.dexels.navajo
	 * .api.Access, com.dexels.navajo.document.Navajo, java.lang.String,
	 * com.dexels.navajo.script.api.TmlRunnable,
	 * com.dexels.navajo.script.api.TmlRunnable,
	 * com.dexels.navajo.script.api.NavajoResponseCallback)
	 */
	@Override
	// Only used from Rhino
	public void callService(Access inputAccess, Navajo input,
			final String service, final TmlRunnable onSuccess,
			final TmlRunnable onFail,
			final NavajoResponseCallback navajoResponseCallback)
			throws IOException, NavajoException {
		final Access currentAccess = inputAccess.cloneWithoutNavajos();

		if (input == null) {
			input = NavajoFactory.getInstance().createNavajo();
		}
		currentAccess.setInDoc(input);
		Header header = input.getHeader();
		if (header == null) {
			header = NavajoFactory.getInstance().createHeader(input, service,
					currentAccess.rpcUser, currentAccess.rpcUser, -1);
			input.addHeader(header);
		}
		header.setRPCName(service);
		header.setRPCUser(currentAccess.rpcUser);
		header.setRPCPassword(currentAccess.rpcPwd);
		NavajoResponseHandler nrh = new NavajoResponseHandler() {
			Throwable caughtException = null;

			@Override
			public void onResponse(Navajo n) {
				setActualCalls(getActualCalls() - 1);
				currentAccess.setOutputDoc(n);
				try {
					if (onSuccess != null) {
						onSuccess.setResponseNavajo(n);
						if (navajoResponseCallback != null) {
							navajoResponseCallback.responseReceived(n);
						}
						setActualCalls(getActualCalls() - 1);
						SchedulerRegistry.getScheduler().submit(onSuccess,
								false);
					}
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public synchronized void onFail(Throwable t) throws IOException {
				caughtException = t;
				logger.warn("Error: ", caughtException);
				setActualCalls(getActualCalls() - 1);
				try {
					if (onFail != null) {
						SchedulerRegistry.getScheduler().submit(onFail, false);
					}
				} catch (IOException e) {
					logger.error("Error: ", e);
				} finally {
					setActualCalls(getActualCalls() - 1);
				}
			}

			@Override
			public synchronized Throwable getCaughtException() {
				return caughtException;
			}

		};
		setActualCalls(getActualCalls() + 1);

		callService(currentAccess.getRequestUrl(), input, nrh, null);
	}

	private void callService(final String url, Navajo n, final NavajoResponseHandler continuation, Integer timeout) throws IOException, NavajoException {

		logger.info("Calling service: {} at {} ", n.getHeader().getRPCName(), url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		n.write(baos);
		final byte[] byteArray = baos.toByteArray();
		
		HttpPost httppost = new HttpPost(url);
		configureProxy(httppost);
		httppost.addHeader("Content-Type", "text/xml; charset=utf-8");
		httppost.setEntity(new ByteArrayEntity(byteArray));

//		Future<HttpResponse> response = 
				
		client.execute(httppost, new FutureCallback<HttpResponse>() {

			@Override
			public void cancelled() {
				logger.info("Request cancelled");
				if (closeAfterUse) {
				    close();
				}
			}

			@Override
			public void completed(HttpResponse resp) {
				try {
					InputStream content = resp.getEntity().getContent();
					Navajo response = NavajoFactory.getInstance().createNavajo(content);
					if(continuation!=null) {
						continuation.onResponse(response);
					}
				} catch (Throwable e) {
					logger.info("Illegal TML detected.",e);
				} finally {
				    if (closeAfterUse) {
	                    close();
	                }
					setActualCalls(getActualCalls()-1);
				}
				
			}

			@Override
			public void failed(Exception e) {
				logger.error("HTTP call to: "+url+" failed: {}", e);
				if(continuation!=null) {
					try {
						continuation.onFail(e);
					} catch (IOException e1) {
						logger.error("Error: ", e1);
					}
				}
				if (closeAfterUse) {
                    close();
                }

			}
		});
	}

	private void configureProxy(HttpPost request) {
		String host = System.getenv("httpProxyHost");
		String port = System.getenv("httpProxyPort");
		if(host==null || port == null) {
			return;
		}
		HttpHost proxy = new HttpHost(host, Integer.parseInt(port));
        RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();		
        request.setConfig(config);
	}

	public static void main(String[] args) throws Exception {

		final ManualAsyncClient ac = new AsyncClientImpl();
		
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
				logger.error("whoops: ", t);
			}

			@Override
			public Throwable getCaughtException() {
				return null;
			}
		};

		// NavajoResponseHandler testFields =

		String service = "club/InitUpdateClub";

		Navajo input = NavajoFactory.getInstance().createNavajo();
		for (int i = 0; i < 10; i++) {
			// String service = "tests/InitNavajoMapTest3";
			ac.callService(input, service, showOutput);
			System.out.println("Exchange sent");
		}
		// Optionally set the HTTP method

		// I suspect the http thread pool has daemon threads, so the vm will
		// shut
		// down if there are no 'real' threads.
		Thread.sleep(10000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.client.async.AsyncClient#getServer()
	 */
	@Override
	public String getServer() {
		return server;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#setServer(java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.client.async.AsyncClient#getUsername()
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.client.async.AsyncClient#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.client.async.AsyncClient#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void close() {
		try {
			client.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	@Override
	public boolean useHttps() {
		return useHttps;
	}

	@Override
	public void setHttps(boolean useHttps) {
		this.useHttps = useHttps;
	}

	/**
	 * set the SSL socket factory to use whenever an HTTPS call is made.
	 * 
	 * @param algorithm
	 *            , the algorithm to use, for example: SunX509
	 * @param type
	 *            Type of the keystore, for example PKCS12 or JKS
	 * @param source
	 *            InputStream of the client certificate, supply null to reset
	 *            the socketfactory to default
	 * @param password
	 *            the keystore password
	 */
	@Override
	public void setClientCertificate(String algorithm, String keyStoreType,
			InputStream source, char[] password) throws IOException {
//		SslContextFactory socketFactory = new SslContextFactory(true);
//		try {
//
//			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//			try {
//				keyStore.load(source, password);
//			} finally {
//				source.close();
//			}
//			logger.info("Key loaded successfully");
//			KeyManagerFactory keyManagerFactory = KeyManagerFactory
//					.getInstance(algorithm);
//			keyManagerFactory.init(keyStore, password);
//			final KeyManager keyManager = keyManagerFactory.getKeyManagers()[0];
//			System.err.println("mana: " + (keyManager));
//			//
//			client = new HttpClient(socketFactory);
//			System.err.println("booom");
//			client.start();
//			return;
//		} catch (UnrecoverableKeyException e) {
//			throw new IOException("Error loading certificate: ", e);
//		} catch (KeyManagementException e) {
//			throw new IOException("Error loading certificate: ", e);
//		} catch (NoSuchAlgorithmException e) {
//			throw new IOException("Error loading certificate: ", e);
//		} catch (KeyStoreException e) {
//			throw new IOException("Error loading certificate: ", e);
//		} catch (CertificateException e) {
//			throw new IOException("Error loading certificate: ", e);
//		} catch (Exception e) {
//			throw new IOException("Error loading certificate: ", e);
//		}
	}

    @Override
    public void setCloseAfterUse(boolean closeAfterUse) {
        this.closeAfterUse = closeAfterUse;
        
    }

}
