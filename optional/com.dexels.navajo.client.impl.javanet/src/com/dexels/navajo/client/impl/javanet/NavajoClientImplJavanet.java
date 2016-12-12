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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.jcraft.jzlib.DeflaterOutputStream;
import com.jcraft.jzlib.GZIPInputStream;
import com.jcraft.jzlib.GZIPOutputStream;
import com.jcraft.jzlib.InflaterInputStream;

public class NavajoClientImplJavanet extends NavajoClient implements ClientInterface, Serializable {

	private static final long serialVersionUID = 4279069306367565223L;

	private final static Logger logger = LoggerFactory.getLogger(NavajoClientImplJavanet.class);

	public static final int CONNECT_TIMEOUT = 10000;

	@Override
	protected Navajo doTransaction(Navajo inputNavajo, boolean useCompression, int retries, int exceptionCount)
			throws ClientException {
		Navajo resultNavajo = null;
		try {
			URL url = new URL(getCurrentHost());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(CONNECT_TIMEOUT);

			// Omit fancy http stuff for now
			con.setDoOutput(true);

			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-type", "text/xml; charset=UTF-8");
			appendHeaderToHttp(con, inputNavajo.getHeader());

			con.setRequestProperty("Connection", "Keep-Alive");

			if (!forceGzip) {
				con.setChunkedStreamingMode(1024);
				con.setRequestProperty("Transfer-Encoding", "chunked");
			}

			postNavajo(inputNavajo, useCompression, con);

			resultNavajo = readResponse(useCompression, con);
		} catch (IOException e) {
			throw new ClientException(-1, -1, e.getMessage(), e);
		}

		return resultNavajo;
	}

	private Navajo readResponse(boolean useCompression,  HttpURLConnection con) throws IOException {
		// Check for errors.
		InputStream in = null;
		Navajo res = null;
		try {
			InputStream inraw = null;
			if (con.getResponseCode() >= 400) {
				throw new IOException(readErrorStream(con));
			} else {
				if (useCompression) {
					if (forceGzip) {
						inraw = new GZIPInputStream(con.getInputStream());
					} else {
						inraw = new InflaterInputStream(con.getInputStream());
					}

				} else {
					inraw = in;
				}
			}
			if (inraw != null) {
				res = NavajoFactory.getInstance().createNavajo(inraw);
			}
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
			// con.disconnect();
		}
		return res;
	}

	private void postNavajo(Navajo inputNavajo, boolean useCompression, HttpURLConnection con)
			throws UnsupportedEncodingException, IOException {
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
					out = new BufferedWriter(
							new OutputStreamWriter(new GZIPOutputStream(con.getOutputStream()), "UTF-8"));
				} else {
					out = new BufferedWriter(
							new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), "UTF-8"));
				}
				inputNavajo.write(out);
			} finally {
				if (out != null) {
					try {
						// out.flush();
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
		BufferedOutputStream bout= null;
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
