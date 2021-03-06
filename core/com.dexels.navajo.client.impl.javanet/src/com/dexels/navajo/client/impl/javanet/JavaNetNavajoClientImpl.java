/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.impl.javanet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Map.Entry;
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

	private static final Logger logger = LoggerFactory.getLogger(JavaNetNavajoClientImpl.class);

	public static final int CONNECT_TIMEOUT = 10000;

	private boolean markDescriptions = System.getProperty("MARK_DESCRIPTIONS")!=null;

	@Override
	protected Navajo doTransaction(Navajo inputNavajo, boolean useCompression, int retries, int exceptionCount)
			throws ClientException {
		Navajo resultNavajo = null;
		
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
			
			if (bearerToken != null) {
                con.setRequestProperty("Authorization", "Bearer " + bearerToken);
            } else if (useBasicAuth) {
                con.setRequestProperty("Authorization","Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
            }
			
			appendHeaderToHttp(con, inputNavajo.getHeader());

			con.setRequestProperty("Connection", "Keep-Alive");

			postNavajo(inputNavajo, useCompression, con);
			resultNavajo = readResponse(useCompression, con);

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

	private Navajo readResponse(boolean useCompression, HttpURLConnection con) throws IOException {
		// Check for errors.
		Navajo res = null;
		InputStream inr = con.getInputStream();
		InputStream inraw = null;
		if (con.getResponseCode() >= 400) {
			throw new IOException(readErrorStream(con));
		} else {
			if (useCompression) {
				if (forceGzip) {
					inraw = new GZIPInputStream(inr);
				} else {
					String responseEncoding = con.getHeaderField("Content-Encoding");
					if (("jzlib".equals(responseEncoding) || "deflate".equals(responseEncoding))) {
						
						inraw = new InflaterInputStream(inr);
					} else {
						inraw = inr;
					}
				}

			} else {
				inraw = inr;
			}
		}
		if (inraw != null) {
			res = NavajoFactory.getInstance().createNavajo(inraw);
		}

		return res;
	}

	private void postNavajo(Navajo inputNavajo, boolean useCompression, HttpURLConnection con)
			throws IOException {
        if (useCompression) {
            if (forceGzip) {
                con.setRequestProperty("Content-Encoding", "gzip");
                con.setRequestProperty("Accept-Encoding", "gzip");
            } else {
                con.setChunkedStreamingMode(1024);
                con.setRequestProperty("Transfer-Encoding", "chunked");
                con.setRequestProperty("Content-Encoding", "jzlib");
                con.setRequestProperty("Accept-Encoding", "jzlib");
            }

			BufferedWriter out = null;
			try {
				if (forceGzip) {
					out = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(con.getOutputStream()), StandardCharsets.UTF_8));
				} else {
					out = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), StandardCharsets.UTF_8));
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
			con.setRequestProperty("noCompression", "true");
			try(BufferedWriter os =  new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), StandardCharsets.UTF_8))) {
				inputNavajo.write(os);
			}
		}
	}

	private void appendHeaderToHttp(HttpURLConnection con, Header header) {
		con.setRequestProperty("X-Navajo-Service", header.getRPCName());
		for (Entry<String,String> entry : httpHeaders.entrySet()) {
			con.setRequestProperty(entry.getKey(), entry.getValue());
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
				logger.info("Responsecode: {}", respCode);
				es.close();
				return "HTTP Status error " + respCode;
			}
		} catch (IOException ioe) {
			logger.error("Error: ", ioe);
		}
		return null;
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		try(BufferedInputStream bin = new BufferedInputStream(in); BufferedOutputStream bout = new BufferedOutputStream(out);) {
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
