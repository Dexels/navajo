package com.dexels.navajo.client.impl.javanet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class JavaNetNavajoClientImpl extends NavajoClient implements ClientInterface, Serializable {

	private static final long serialVersionUID = 4279069306367565223L;

	private final static Logger logger = LoggerFactory.getLogger(JavaNetNavajoClientImpl.class);

	public static final int CONNECT_TIMEOUT = 10000;

	private boolean markDescriptions = System.getProperty("MARK_DESCRIPTIONS")!=null;

	@Override
	protected Navajo doTransaction(Navajo inputNavajo, boolean useCompression, int retries, int exceptionCount)
			throws ClientException {
		Navajo resultNavajo = null;
		
//		useCompression = false;
		
		String service = inputNavajo.getHeader().getRPCName();
		
		HttpURLConnection con = null;
		try {
			URL url = new URL(getCurrentHost());
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(CONNECT_TIMEOUT);

			// Omit fancy http stuff for now
			con.setDoOutput(true);

			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
			appendHeaderToHttp(con, inputNavajo.getHeader());

			con.setRequestProperty("Connection", "Keep-Alive");

			if(useCompression) {
				if (!forceGzip) {
					con.setChunkedStreamingMode(1024);
					con.setRequestProperty("Transfer-Encoding", "chunked");
					con.setRequestProperty("Accept-Encoding", "deflate");
				} else {
					con.setRequestProperty("Content-Encoding", "gzip");

				}
			}

			postNavajo(inputNavajo, useCompression, con);
			System.err.println("Use gzip? "+forceGzip+" use compression: "+useCompression);
			resultNavajo = readResponse(service,useCompression, con);
//			if(System.getProperty("MARK_DESCRIPTIONS")

			if(this.markDescriptions ) {
				NavajoClient.addParagraphToAllPropertyDescriptions(resultNavajo, null);
				
			}
		} catch (Throwable t) {
			resultNavajo = handleException(t, getCurrentHost());
		}
		    

		return resultNavajo;
	}

	private Navajo handleException(Throwable exception, String host) throws ClientException {
		if (!generateConditionErrors) {
			logger.error("Error: ", exception);
			throw new ClientException(-1, -1, exception.getMessage(), exception);
		}

		Navajo n = null;
		if (exception instanceof java.net.UnknownHostException) {
			logger.warn("Connection problem: UnknownHostException exception to {}!", host, exception);
			n = NavajoFactory.getInstance().createNavajo();
			generateConnectionError(n, 7777777, "Unknown host: " + exception.getMessage());
		} else if (exception instanceof java.net.NoRouteToHostException) {
			logger.warn("Connection problem: NoRouteToHostException exception to {}!", host, exception);
			n = NavajoFactory.getInstance().createNavajo();
			generateConnectionError(n, 55555, "No route to host: " + exception.getMessage());

		} else if (exception instanceof java.net.SocketTimeoutException
				|| exception instanceof java.net.ConnectException
				|| exception instanceof java.net.NoRouteToHostException) {
			logger.warn("Connection problem: SocketTimeoutException exception to {}!", host, exception);
			n = NavajoFactory.getInstance().createNavajo();
			generateConnectionError(n, 55555, "Error on getting response data: " + exception.getMessage());
		} else if (exception instanceof javax.net.ssl.SSLHandshakeException) {
			logger.warn("Connection problem: SSLHandshakeException exception to {}!", host, exception);
			n = NavajoFactory.getInstance().createNavajo();
			generateConnectionError(n, 666666, "SSL fout " + exception.getMessage());
		}
		if (n != null) {
			return n;
		}

		logger.warn("Connection problem: Exception {} to {}!", exception.getMessage(), host, exception);
		throw new ClientException(-1, -1, exception.getMessage(), exception);

	}

	private Navajo readResponse(String service, boolean useCompression, HttpURLConnection con) throws IOException {
		// Check for errors.
		InputStream in = null;
		Navajo res = null;
		try {
			System.err.println("Response fields: "+con.getHeaderFields());
			ByteArrayOutputStream cachedout = new ByteArrayOutputStream();
			
			InputStream inr = con.getInputStream();
			InputStream inraw = null;
			copyResource(cachedout, inr);
			byte[] array = cachedout.toByteArray();
//--------------------
			String dumpedFileName = "response_"+service+UUID.randomUUID().toString();
			File dumpedFile = new File(dumpedFileName);
			dumpedFile.getParentFile().mkdirs();
			System.err.println("Dumped file at: "+dumpedFile.getAbsolutePath());
			FileOutputStream dumped = new FileOutputStream(dumpedFile);
			dumped.write(array);
			dumped.close();
//--------------------
			ByteArrayInputStream incached = new ByteArrayInputStream(array);
			if (con.getResponseCode() >= 400) {
				throw new IOException(readErrorStream(con));
			} else {
				if (useCompression) {
					if (forceGzip) {
						inraw = new GZIPInputStream(incached);
					} else {
						String responseEncoding = con.getHeaderField("Content-Encoding");
						if (useCompression && ("jzlib".equals(responseEncoding) || "deflate".equals(responseEncoding))) {
							
							inraw = new InflaterInputStream(incached);
						} else {
							inraw = incached;
						}
					}

				} else {
					inraw = incached;
				}
			}
			if (inraw != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				copyResource(baos, inraw);
				byte[] data = baos.toByteArray();
				inraw = new ByteArrayInputStream(data);
				res = NavajoFactory.getInstance().createNavajo(inraw);
			}
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
		return res;
	}

	private void postNavajo(Navajo inputNavajo, boolean useCompression, HttpURLConnection con)
			throws UnsupportedEncodingException, IOException {
		if (bearerToken != null) {
			con.setRequestProperty("Authorization", "Bearer " + bearerToken);
		} else if (useBasicAuth) {
			con.setRequestProperty("Authorization","Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
		}

		if (useCompression) {
			if (forceGzip) {
				con.setRequestProperty("Content-Encoding", "gzip");
				con.setRequestProperty("Accept-Encoding", "gzip");
			} else {
				con.setRequestProperty("Content-Encoding", "jzlib");
				con.setRequestProperty("Accept-Encoding", "jzlib");
			}
			// con.connect();

			BufferedWriter out = null;
			try {
				if (forceGzip) {
					out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(con.getOutputStream()), "UTF-8"));
				} else {
					out = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), "UTF-8"));
				}
				inputNavajo.write(out);
			} finally {
				if (out != null) {
					try {
						out.flush();
						out.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}
			}
		} else {
			// con.connect();
			con.setRequestProperty("noCompression", "true");
			BufferedWriter os = null;
			try {
				os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
				inputNavajo.write(os);
			} finally {
				if (os != null) {
					try {
						os.flush();
						os.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
				}
			}
		}
	}

	private void appendHeaderToHttp(HttpURLConnection con, Header header) {
		con.setRequestProperty("X-Navajo-RpcName", header.getRPCName());
		con.setRequestProperty("X-Navajo-RpcUser", header.getRPCUser());
		con.setRequestProperty("X-Navajo-Username", header.getRPCUser());
		con.setRequestProperty("X-Navajo-Password", header.getRPCPassword());
		con.setRequestProperty("X-Navajo-Service", header.getRPCName());
//		con.setRequestProperty("X-Navajo-Debug", "true");
		for (String key : httpHeaders.keySet()) {
			con.setRequestProperty(key, httpHeaders.get(key));
		}

	}

	private final String readErrorStream(final HttpURLConnection myCon) {
		try {
			if (myCon != null) {
				int respCode = myCon.getResponseCode();
				InputStream es = myCon.getErrorStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				copyResource(bos, es);
				bos.close();
				// String error = new String(bos.toByteArray());
				logger.info("Responsecode: " + respCode);
				es.close();
				return "HTTP Status error " + respCode;
			}
		} catch (IOException ioe) {
			logger.error("Error: " + ioe);
		}
		return null;
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {

		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		try {
			bin = new BufferedInputStream(in);
			bout = new BufferedOutputStream(out);
			byte[] buffer = new byte[1024];
			int read = -1;
			boolean ready = false;
			while (!ready) {

				read = bin.read(buffer);
				if (read > -1) {
					bout.write(buffer, 0, read);
				}

				if (read <= -1) {
					ready = true;
				}
			}
		} finally {
			try {
				if (bin != null) {
					bin.close();
				}
				if (bout != null) {
					bout.flush();
					bout.close();
				}

			} catch (IOException e) {

			}
		}

	}

	@Override
	public void setKeyStore(KeyStore keystore) {
		// Whateva

	}

	@Override
	public void setClientCertificate(String algorithm, String type, InputStream is, char[] password)
			throws IOException {
		// Whateva
	}

}
