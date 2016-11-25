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
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
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
import com.dexels.navajo.client.impl.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.document.Guid;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;



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

   
    public void setKeyStore(KeyStore keyStore) {
        if (socketFactory != null) {
            throw new RuntimeException("Not yet implemented: combination of custom keystore and client certificates is not yet implemented");
        }
        this.keyStore = keyStore;
    }

    

    @Override
    protected Navajo doTransaction(Navajo d, boolean useCompression, int retries, int exceptionCount) throws ClientException {
        URI uri;
        Navajo n = null;
        
        try {
            uri = new URI(getCurrentHost());
        } catch (URISyntaxException e) {
           logger.error("Syntax exception!", e);
           return n;
        }
        
        long timeStamp = System.currentTimeMillis();
        try {
            HttpPost httppost = new HttpPost(uri);
           
            appendHeaderToHttp(httppost, d.getHeader());

            NavajoRequestEntity reqEntity = new NavajoRequestEntity(d, useCompression, forceGzip);
            httppost.setEntity(reqEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            
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
            if (NavajoHttpRequestRetryHandler.retryRequest(t, retries)) {
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
    
    protected Navajo handleException(Throwable exception, String host, long timeStamp) throws ClientException {
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
        } else if (exception instanceof IOException) {
            logger.warn("Connection problem: IOException {} exception to {}! ", exception.getMessage(), host, exception);
            throw new ClientException(-1, -1, exception.getMessage(), exception);
        } else {
            logger.error("Error: ", exception);
        }
        return n;
    }

    private void generateConnectionError(Navajo n, int id, String description) {
        try {
            Message conditionError = NavajoFactory.getInstance().createMessage(n, "ConditionErrors", Message.MSG_TYPE_ARRAY);
            n.addMessage(conditionError);
            Message conditionErrorElt = NavajoFactory.getInstance().createMessage(n, "ConditionErrors");
            conditionError.addMessage(conditionErrorElt);
            Property p1 = NavajoFactory.getInstance().createProperty(n, "Id", Property.INTEGER_PROPERTY, id + "", 10, "Id", Property.DIR_OUT);
            Property p2 = NavajoFactory.getInstance().createProperty(n, "Description", Property.INTEGER_PROPERTY, description, 10, "Omschrijving",
                    Property.DIR_OUT);
            Property p3 = NavajoFactory.getInstance().createProperty(n, "FailedExpression", Property.INTEGER_PROPERTY, "", 10, "FailedExpression",
                    Property.DIR_OUT);
            Property p4 = NavajoFactory.getInstance().createProperty(n, "EvaluatedExpression", Property.INTEGER_PROPERTY, "", 10, "EvaluatedExpression",
                    Property.DIR_OUT);
            conditionErrorElt.addProperty(p1);
            conditionErrorElt.addProperty(p2);
            conditionErrorElt.addProperty(p3);
            conditionErrorElt.addProperty(p4);
        } catch (NavajoException ex) {
            logger.error("Error: ", ex);
        }
    }
  

  
    @Override
    protected final Navajo doSimpleSend(Navajo out, String method, long expirationInterval, int retries) throws ClientException {
        // NOTE: prefix persistence key with method, because same Navajo object
        // could be used as a request
        // for multiple methods!

        // ============ compared services ===================

        /**
         * Make sure that same Navajo is not used simultaneously.
         */
        synchronized (out) {
            // ====================================================

            Header header = out.getHeader();
            String callingService = null;
            if (header == null) {
                header = NavajoFactory.getInstance().createHeader(out, method, username, password, expirationInterval);
                out.addHeader(header);
            } else {
                callingService = header.getRPCName();
                header.setRPCName(method);
                header.setRPCUser(username);
                header.setRPCPassword(password);
                header.setExpirationInterval(expirationInterval);
            }
            // ALWAY SET REQUEST ID AT THIS POINT.
            if (header.getRequestId() != null && header.getRequestId().equals("42")) {
                System.err.println("ENCOUNTERED TEST!!!");
            } else {
                header.setRequestId(Guid.create());
            }
            String sessionToken = getSessionTokenProvider().getSessionToken();
            header.setHeaderAttribute("clientToken", sessionToken);
            header.setHeaderAttribute("clientInfo", getSystemInfoProvider().toString());
            // ========= Adding globalMessages

            long clientTime = 0;
            try {
                if (out.getHeader() != null) {
                    processPiggybackData(out.getHeader());
                }

                // ==================================================================
                // set the locale
                // ==============================================
                if (localeCode != null) {
                    out.getHeader().setHeaderAttribute("locale", localeCode);
                }
                // ==================================================================
                // set the sublocale
                // ==============================================
                if (subLocale != null) {
                    out.getHeader().setHeaderAttribute("sublocale", subLocale);
                }

                if (application != null) {
                    out.getHeader().setHeaderAttribute("application", application);
                }
                if (organization != null) {
                    out.getHeader().setHeaderAttribute("organization", organization);
                }

                Navajo n = null;

                long timeStamp = System.currentTimeMillis();

                n = doTransaction(out, allowCompression, retries, 0);
                
                if (n != null && n.getHeader() != null) {
                    n.getHeader().setHeaderAttribute("sourceScript", callingService);
                    clientTime = (System.currentTimeMillis() - timeStamp);
                    n.getHeader().setHeaderAttribute("clientTime", "" + clientTime);
                    String tot = n.getHeader().getHeaderAttribute("serverTime");                        
                    long totalTime = -1;
                    if (tot != null && !"".equals(tot)) {
                        totalTime = Long.parseLong(tot);
                        n.getHeader().setHeaderAttribute("transferTime", "" + (clientTime - totalTime));
                    }
                    Map<String, String> headerAttributes = n.getHeader().getHeaderAttributes();
                    Map<String, String> pbd = new HashMap<String, String>(headerAttributes);
                    pbd.put("type", "performanceStats");
                    pbd.put("service", method);
                    synchronized (piggyBackData) {
                        piggyBackData.add(pbd);
                    }
                   
                } else {
                    logger.info("Null header in input message");
                }
                
                return n;
            } catch (ClientException e) {
                throw e;
            } catch (Exception e) {
                logger.error("Error: ", e);
                throw new ClientException(-1, -1, e.getMessage(), e);
            }
        }
    }

    @SuppressWarnings("unused")
    private void appendHeaderToHttp(HttpURLConnection con, Header header) {
        con.setRequestProperty("rpcName", header.getRPCName());
        con.setRequestProperty("rpcUser", header.getRPCUser());
        Map<String, String> attrs = header.getHeaderAttributes();
        for (Entry<String, String> element : attrs.entrySet()) {
            if (element.getValue() != null) {
                con.setRequestProperty(element.getKey(), element.getValue());
            }
        }
    }

    private void appendHeaderToHttp(HttpPost httppost, Header header) {
        httppost.setHeader("rpcName", header.getRPCName());
        httppost.setHeader("rpcUser", header.getRPCUser());
        Map<String, String> attrs = header.getHeaderAttributes();
        for (Entry<String, String> element : attrs.entrySet()) {
            if (element.getValue() != null) {
                httppost.setHeader(element.getKey(), element.getValue());
            }
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
        } catch (UnrecoverableKeyException e) {
            throw new IOException("Error loading certificate: ", e);
        } catch (KeyManagementException e) {
            throw new IOException("Error loading certificate: ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new IOException("Error loading certificate: ", e);
        } catch (KeyStoreException e) {
            throw new IOException("Error loading certificate: ", e);
        } catch (CertificateException e) {
            throw new IOException("Error loading certificate: ", e);
        }
    }

    
    @Override
    public SessionTokenProvider getSessionTokenProvider() {
        if (sessionTokenProvider == null) {
            return SessionTokenFactory.getSessionTokenProvider();
        }
        return this.sessionTokenProvider;
    }
    
}
