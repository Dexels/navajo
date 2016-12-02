/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.net.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;


public class NavajoClientImpl extends NavajoClient implements ClientInterface, Serializable {

    private final static Logger logger = LoggerFactory.getLogger(NavajoClientImpl.class);

    private static final long serialVersionUID = -7848349362973607161L;
    public static final int CONNECT_TIMEOUT = 10000;
    private static final int SLEEPTIME_PER_EXCEPTION = 1500;

    private CloseableHttpClient httpclient;

    public NavajoClientImpl() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(0).build();
        httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    

    @Override
    protected Navajo doTransaction(Navajo d, boolean useCompression, int retries, int exceptionCount) throws ClientException {
        URI uri;
        Navajo n = null;
        
        try {
            uri = new URI(getCurrentHost());
        } catch (URISyntaxException e) {
           logger.error("Syntax exception!", e);
           throw new ClientException("Invalid host");
        }
        
        long timeStamp = System.currentTimeMillis();
        try {
            HttpPost httppost = new HttpPost(uri);
           
            appendHeaderToHttp(httppost, d.getHeader());

            NavajoRequestEntity reqEntity = new NavajoRequestEntity(d, useCompression, forceGzip);
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            if (bearerToken != null) {
            	httppost.setHeader("Authorization", "Bearer " + bearerToken);
            } else if (useBasicAuth) {
            	httppost.setHeader("Authorization", "Basic " + Base64.encodeBase64((username+":"+password).getBytes()));
            }
            
            try {
                if (response.getStatusLine().getStatusCode() >= 400) {
                    throw new IOException(response.getStatusLine().getReasonPhrase());
                }
                HttpEntity responseEntity = response.getEntity();
                n = NavajoFactory.getInstance().createNavajo(responseEntity.getContent());
            } finally {
                response.close();
            }
        } catch (Throwable t) {
            // Check if we should attempt a retry. 
            if (retryRequest(t, retries)) {
                try {
                    Thread.sleep((exceptionCount+1) * SLEEPTIME_PER_EXCEPTION);
                } catch (InterruptedException e) {
                }
                n = doTransaction(d, useCompression, --retries, ++exceptionCount);
            } else {
                n = handleException(t, getCurrentHost(),timeStamp);
            }
        } finally {
            // Don't close to allow connection reuse
            // httpclient.close();
        }
        if (n == null) {
            throw new ClientException(-1, -1, "Empty Navajo received");
        }
        return n;
    }
    

    
    public void setKeyStore(KeyStore keyStore) {
        if (socketFactory != null) {
            throw new RuntimeException("Not yet implemented: combination of custom keystore and client certificates is not yet implemented");
        }
        this.keyStore = keyStore;
    }

    
    
    private boolean retryRequest(Throwable t, int retries) {
        // First determine the type of exception. For some exceptions, we don't bother retrying!

        if (t == null) {
            throw new IllegalArgumentException("Exception parameter may not be null");
        }
        if (retries < 1) {
            return false;
        }

        if (t instanceof NoHttpResponseException) {
            // Retry if the server dropped connection on us
            return true;
        }
        if (t instanceof InterruptedIOException) {
            // Timeout
            return true;
        }
        if (t instanceof UnknownHostException) {
            return true;
        }
        if (t instanceof ConnectException) {
            // Connection refused
            return true;
        }
        if (t instanceof SSLHandshakeException) {
            return false;
        }

        // Some other exception occurred. Probably doesn't hurt to retry?
        return true;
    }


    protected Navajo handleException(Throwable exception, String host, long timeStamp) throws ClientException {
        if (!generateConditionErrors) {
            logger.error("Error: ", exception);
             throw new ClientException(-1, -1, exception.getMessage(), exception);
        }
        
        Navajo n = null;
        if (exception instanceof java.net.UnknownHostException | exception instanceof org.apache.http.conn.HttpHostConnectException) {
            logger.warn("Connection problem: UnknownHostException exception to {}!", host, exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 7777777, "Unknown host: " + exception.getMessage());
        } else if (exception instanceof java.net.NoRouteToHostException) {
            logger.warn("Connection problem: NoRouteToHostException exception to {}!", host, exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 55555, "No route to host: " + exception.getMessage());
        } else if (exception instanceof org.apache.http.NoHttpResponseException) {
            logger.warn("Connection problem: NoHttpResponseException exception to {}!  after {}ms", host, (System.currentTimeMillis() - timeStamp), exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 55555, "Error on getting response data: " + exception.getMessage());
        } else if (exception instanceof java.net.SocketTimeoutException) {
            logger.warn("Connection problem: SocketTimeoutException exception to {}! after {}ms", host, (System.currentTimeMillis() - timeStamp), exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 55555, "Error on getting response data: " + exception.getMessage());
        } else if (exception instanceof org.apache.http.conn.ConnectTimeoutException) {
            logger.warn("Connection problem: ConnectTimeoutException exception to {}! after {}ms", host, (System.currentTimeMillis() - timeStamp), exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 55555, "Connect time-out: " + exception.getMessage());
        } else if (exception instanceof javax.net.ssl.SSLHandshakeException) {
            logger.warn("Connection problem: SSLHandshakeException exception to {}!", host, exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 666666, "SSL fout " + exception.getMessage());
        } else if (exception instanceof java.net.SocketException) {
            logger.warn("Connection problem: SocketException {} exception to {}! ", exception.getMessage(), host, exception);
            n = NavajoFactory.getInstance().createNavajo();
            generateConnectionError(n, 55555, "SocketException: " + exception.getMessage());
        }
        
        logger.warn("Connection problem: Exception {} to {}!", exception.getMessage(), host, exception);
        throw new ClientException(-1, -1, exception.getMessage(), exception);
    }

    private void appendHeaderToHttp(HttpPost httppost, Header header) {
        httppost.setHeader("X-Navajo-RpcName", header.getRPCName());
        httppost.setHeader("X-Navajo-RpcUser", header.getRPCUser());
        for (String key : httpHeaders.keySet()) {
        	httppost.setHeader(key, httpHeaders.get(key));
        }

    }

  


    /**
     * set the SSL socket factory to use whenever an HTTPS call is made.
     * 
     * @param algorithm , the algorithm to use, for example: SunX509
     * @param type Type of the keystore, for example PKCS12 or JKS
     * @param source InputStream of the client certificate, supply null to reset the socketfactory to default
     * @param password the keystore password
     */
    @Override
    public void setClientCertificate(String algorithm, String keyStoreType, InputStream source, char[] password) throws IOException {
        if (keyStore != null) {
            throw new RuntimeException("Not yet implemented: combination of custom keystore and client certificates is not yet implemented");
        }
        if (source == null) {
            this.socketFactory = null;
            return;
        }
        SSLContext context;
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            try {
                keyStore.load(source, password);
            } finally {
                source.close();
            }
            keyManagerFactory.init(keyStore, password);
            context = SSLContext.getInstance("TLS");
            context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
            this.socketFactory = context.getSocketFactory();
            return;
		} catch (UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException
				| CertificateException e) {
			throw new IOException("Error loading certificate: ", e);
		}
    }



 
}
