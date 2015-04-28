package com.dexels.navajo.logclient.transport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DeflaterOutputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.logclient.NavajoLogAppender;

public class LogShipper {

    private final static Logger logger = LoggerFactory.getLogger(NavajoLogAppender.class);
    public static final int CONNECT_TIMEOUT = 5000;

    private String navajoLoggerURL;
    private boolean useHttps = false;
    private SSLSocketFactory socketFactory;
    private KeyStore keyStore;
    private boolean useCompression = true;

    public void setRemoteLoggerURL(String loggerURL) {
        navajoLoggerURL = loggerURL;
    }

    public void ship(String logObject) throws IOException {
        if (logObject == null || logObject.equals("")) {
            return;
        }
        URL url;
        // useCompression = false;

        if (useHttps) {
            url = new URL("https://" + navajoLoggerURL);
        } else {
            url = new URL("http://" + navajoLoggerURL);
        }
        HttpURLConnection con = null;
        con = (HttpURLConnection) url.openConnection();
        if (useHttps) {
            HttpsURLConnection httpsCon = (HttpsURLConnection) con;
            if (socketFactory != null) {
                httpsCon.setSSLSocketFactory(socketFactory);
            }
            if (keyStore != null) {
                try {
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);
                    httpsCon.setSSLSocketFactory(context.getSocketFactory());
                } catch (NoSuchAlgorithmException e) {
                    logger.error("Error: ", e);
                } catch (KeyStoreException e) {
                    logger.error("Error: ", e);
                } catch (KeyManagementException e) {
                    logger.error("Error: ", e);
                }
            }
        }
        con.setRequestMethod("POST");
        con.setConnectTimeout(CONNECT_TIMEOUT);

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-type", "text/json; charset=UTF-8");
        con.setRequestProperty("Connection", "Keep-Alive");

        try {
            java.lang.reflect.Method chunked = con.getClass().getMethod("setChunkedStreamingMode", new Class[] { int.class });
            // skip it for GAE

            chunked.invoke(con, new Object[] { new Integer(1024) });
            con.setRequestProperty("Transfer-Encoding", "chunked");

        } catch (SecurityException e) {
        } catch (Throwable e) {
            logger.error("Error: ", e);
            logger.warn("setChunkedStreamingMode does not exist, upgrade to java 1.5+");
        }
        if (useCompression) {

            con.setRequestProperty("Content-Encoding", "jzlib");
            con.setRequestProperty("Accept-Encoding", "jzlib");
            BufferedWriter out = null;
            try {

                out = new BufferedWriter(new OutputStreamWriter(new DeflaterOutputStream(con.getOutputStream()), "UTF-8"));
                out.write(logObject);
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
                // logger.info("Using no compression!");

                os = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
                os.write(logObject);

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
}
