/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.sessiontoken.SessionTokenProvider;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoProvider;
import com.dexels.navajo.document.Guid;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

public class NavajoClient implements ClientInterface, Serializable {

    private final static Logger logger = LoggerFactory.getLogger(NavajoClient.class);

    private static final long serialVersionUID = -7848349362973607161L;
    public static final int CONNECT_TIMEOUT = 10000;
    private static final int SLEEPTIME_PER_EXCEPTION = 1500;

    private String username = null;
    private String password = null;
    private String[] serverUrls;
    private double[] serverLoads;
    private boolean useHttps = false;
    private int currentServerIndex;

    private final Random randomize = new Random(System.currentTimeMillis());
    // Warning: Not thread safe!
    private final Set<Map<String, String>> piggyBackData = new HashSet<Map<String, String>>();

    private String localeCode = null;
    private String subLocale;
    private String application;
    private boolean allowCompression = true;
    private boolean forceGzip = true;
    private SystemInfoProvider systemInfoProvider;
    private SessionTokenProvider sessionTokenProvider;
    private SSLSocketFactory socketFactory;
    private KeyStore keyStore;
    private String organization;

    private CloseableHttpClient httpclient;

    public NavajoClient() { 
        RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(0).build();
        httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
    }

    @Override
    public final void setUsername(String s) {
        username = s;
    }

    public void setKeyStore(KeyStore keyStore) {
        if (socketFactory != null) {
            throw new RuntimeException("Not yet implemented: combination of custom keystore and client certificates is not yet implemented");
        }
        this.keyStore = keyStore;
    }

    /**
     * Set the server URL
     * 
     * @param url String USE SET SERVERURLS
     */
    @Override
    public final void setServerUrl(String url) {
        serverUrls = new String[] { url };
        setServers(serverUrls);

    }

    @Override
    public final void setPassword(String pw) {
        password = pw;
    }

    @Override
    public final Navajo doSimpleSend(String method) throws ClientException {
        return doSimpleSend(NavajoFactory.getInstance().createNavajo(), method, 0);
    }

    @Override
    public final Navajo doSimpleSend(Navajo out, String method) throws ClientException {
        return doSimpleSend(out, method, 0);
    }

    @Override
    public final Navajo doSimpleSend(Navajo out, String method, Integer retries) throws ClientException {
        if (username == null) {
            throw new ClientException(1, 1, "No username set!");
        }
        if (password == null) {
            throw new ClientException(1, 1, "No password set!");
        }
        if (getCurrentHost() == null) {
            throw new ClientException(1, 1, "No host set!");
        }
        return doSimpleSend(out, method, -1, retries, false);

    }

    /**
     * Perform a synchronous webservice call
     * 
     * @param n Navajo
     * @param method String
     * @param v ConditionErrorHandler
     * @param expirationInterval long
     * @throws ClientException
     * @return Navajo
     */
    @Override
    public final Navajo doSimpleSend(Navajo n, String method, ConditionErrorHandler v, long expirationInterval) throws ClientException {
        if (v != null) {
            v.clearConditionErrors();
        }

        if (username == null) {
            throw new ClientException(1, 1, "No username set!");
        }
        if (password == null) {
            throw new ClientException(1, 1, "No password set!");
        }
        if (getCurrentHost() == null) {
            throw new ClientException(1, 1, "No host set!");
        }
        Navajo result = doSimpleSend(n, method, expirationInterval, 0, false);

        if (v != null) {
            checkValidation(result, v);
        }
        return result;
    }

    
    private Navajo doTransaction(Navajo d, boolean useCompression, boolean forcePreparseProxy, int retries, int exceptionCount)  throws Throwable {
        URI uri = new URI(getCurrentHost());
        Navajo n = null;
        try {
            HttpPost httppost = new HttpPost(uri);
           
            appendHeaderToHttp(httppost, d.getHeader());

            if (forcePreparseProxy) {
                httppost.setHeader("Navajo-Preparse", "true");
            }

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
                Thread.sleep((exceptionCount+1) * SLEEPTIME_PER_EXCEPTION);
                n = doTransaction(d, useCompression, forcePreparseProxy, --retries, ++exceptionCount);
            } else {
                throw t;
            }
        } finally {
            // Don't close to allow connection reuse
            // httpclient.close();
        }
        return n;
    
        
    }
    /**
     * Do a transation with the Navajo Server (name) using a Navajo Message Structure (TMS) compliant XML document.
     * 
     * @param name String
     * @param d Navajo
     * @param useCompression boolean
     * @throws URISyntaxException
     */

    private Navajo doTransaction(Navajo d, boolean useCompression, boolean forcePreparseProxy, int retries)
            throws Throwable {
        return doTransaction(d,  useCompression,  forcePreparseProxy, retries, 0);
    }

    @Override
    public boolean useHttps() {
        return useHttps;
    }

    @Override
    public void setHttps(boolean useHttps) {
        this.useHttps = useHttps;
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
    // navajo://frank:aap@192.0.0.1/InitUpdateMember

    // public final Navajo doUrlSend(Navajo out, String url) {
    // URLStreamHandler u;
    // }

    private final void generateConnectionError(Navajo n, int id, String description) {

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

    private final Navajo doSimpleSend(Navajo out, String method, long expirationInterval, int retries, boolean allowPreparseProxy) throws ClientException {
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

                // org.apache.http.NoHttpResponseException
                // org.apache.http.conn.ConnectTimeoutException
                try {
                    n = doTransaction(out, allowCompression, allowPreparseProxy, retries);
                    if (n == null) {
                        throw new Exception("Empty Navajo received");
                    }
                } catch (java.net.UnknownHostException | org.apache.http.conn.HttpHostConnectException uhe) {
                    logger.warn("Connection problem: UnknownHostException exception to {}!", getCurrentHost(), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 7777777, "Unknown host: " + uhe.getMessage());
                } catch (java.net.NoRouteToHostException uhe) {
                    logger.warn("Connection problem: NoRouteToHostException exception to {}!", getCurrentHost(), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 55555, "No route to host: " + uhe.getMessage());
                } catch (org.apache.http.NoHttpResponseException uhe) {
                    logger.warn("Connection problem: NoHttpResponseException exception to {}!  after {}ms", getCurrentHost(),  (System.currentTimeMillis()-timeStamp), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 55555, "Error on getting response data: " + uhe.getMessage());
                } catch (java.net.SocketTimeoutException uhe) {
                    logger.warn("Connection problem: SocketTimeoutException exception to {}! after {}ms", getCurrentHost(),  (System.currentTimeMillis()-timeStamp), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 55555, "Error on getting response data: " + uhe.getMessage());
                } catch (org.apache.http.conn.ConnectTimeoutException uhe) {
                    logger.warn("Connection problem: ConnectTimeoutException exception to {}! after {}ms",getCurrentHost(),  (System.currentTimeMillis()-timeStamp), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 55555, "Connect time-out: "+ uhe.getMessage());
                } catch (javax.net.ssl.SSLHandshakeException uhe) {
                    logger.warn("Connection problem: SSLHandshakeException exception to {}!", getCurrentHost(), uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 666666, "SSL fout " + uhe.getMessage());
                } catch (java.net.SocketException uhe) {
                    logger.warn("Connection problem: SocketException {} exception to {}! ", uhe.getMessage(),getCurrentHost(),  uhe);
                    n = NavajoFactory.getInstance().createNavajo();
                    generateConnectionError(n, 55555, "SocketException: " + uhe.getMessage());
                } catch (IOException uhe) {
                    logger.warn("Connection problem: IOException {} exception to {}! ", uhe.getMessage(), getCurrentHost(), uhe); 
                    throw uhe;
                } catch (Throwable r) {
                    logger.error("Error: ", r);
                    
                    
                } finally {
                    if (n != null && n.getHeader() != null) {
                        n.getHeader().setHeaderAttribute("sourceScript", callingService);
                        clientTime = (System.currentTimeMillis() - timeStamp);
                        n.getHeader().setHeaderAttribute("clientTime", "" + clientTime);
                        String tot = n.getHeader().getHeaderAttribute("serverTime");
                        String loadStr = n.getHeader().getHeaderAttribute("cpuload");
                        double load = -1.0;
                        if (loadStr != null) {
                            try {
                                load = Double.parseDouble(loadStr);
                                for (int x = 0; x < serverUrls.length; x++) {
                                    if (serverUrls[x].equals(getCurrentHost())) {
                                        serverLoads[x] = load;
                                        x = serverUrls.length + 1;
                                    }
                                }
                            } catch (Throwable t) {
                            }
                        }
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
                }
                return n;
            } catch (Exception e) {
                logger.error("Error: ", e);
                throw new ClientException(-1, -1, e.getMessage(), e);
            }
        }
    }

    /**
     * Add piggyback data to header.
     * 
     * @param header
     */
    private final void processPiggybackData(Header header) {

        synchronized (piggyBackData) {
            // Clear previous piggyback data.
            header.clearPiggybackData();
            for (Iterator<Map<String, String>> iter = piggyBackData.iterator(); iter.hasNext();) {
                Map<String, String> element = iter.next();
                header.addPiggyBackData(element);
            }
            // remove piggyback data.
            piggyBackData.clear();
        }

    }

    private final void checkValidation(Navajo result, ConditionErrorHandler v) {
        Message conditionErrors = result.getMessage("ConditionErrors");
        if (conditionErrors != null && v != null) {
            v.checkValidation(conditionErrors);
        }
    }

    @Override
    public void setServers(String[] servers) {
        serverUrls = servers;
        serverLoads = new double[serverUrls.length];
        if (servers.length > 0) {
            currentServerIndex = randomize.nextInt(servers.length);
        }
    }

    @Override
    public String getCurrentHost() {
        if (serverUrls != null && serverUrls.length > 0) {
            String currentServer = serverUrls[currentServerIndex];
            
            if (!currentServer.startsWith("http") && currentServer.length() > 0) {
                if (useHttps) {
                    return "https://" + currentServer;
                } else {
                    return "http://" + currentServer;
                }
            }
            return currentServer;
        }
        return null;
    }

    @Override
    /**
     * I think only used in testing
     */
    public void setCurrentHost(String host) {
        for (int i = 0; i < serverUrls.length; i++) {
            if (serverUrls[i].equals(host)) {
                currentServerIndex = i;
                logger.info("SET CURRENT SERVER TO: " + host + "(" + currentServerIndex + ")");
                break;
            }
        }
    }

    @Override
    public void setLocaleCode(String locale) {
        this.localeCode = locale;
    }

    @Override
    public String getLocaleCode() {
        return this.localeCode;
    }

    @Override
    public void setSubLocaleCode(String locale) {
        this.subLocale = locale;
    }

    @Override
    public String getSubLocaleCode() {
        return this.subLocale;
    }

    public String getApplication() {
        return application;
    }

    @Override
    public void setApplication(String application) {
        this.application = application;
    }

    @Override
    public void setOrganization(String organization) {
        this.organization = organization;

    }

    public String getOrganization() {
        return organization;
    }

    /**
     * Schedule a webservice @ a certain time. Note that this method does NOT return the response of the scheduled webservice. It contains a Navajo with the
     * status of the scheduling.
     * 
     * @out contains the request Navajo
     * @method defines the webservice
     * @schedule defines a timestamp of the format: HH:mm:ss dd-MM-yyyy. If null assume immediate execution.
     * 
     */
    @Override
    public Navajo doScheduledSend(Navajo out, String method, String schedule, String description, String clientId) throws ClientException {

        String triggerURL = null;

        if (schedule == null) {
            schedule = "now";
        }

        Header h = out.getHeader();
        if (h == null) {
            h = NavajoFactory.getInstance().createHeader(out, method, username, password, -1);
            out.addHeader(h);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        if (!schedule.equals("now")) {
            try {
                c.setTime(sdf.parse(schedule));
                triggerURL = "time:" + (c.get(Calendar.MONTH) + 1) + "|" + c.get(Calendar.DAY_OF_MONTH) + "|" + c.get(Calendar.HOUR_OF_DAY) + "|"
                        + c.get(Calendar.MINUTE) + "|*|" + c.get(Calendar.YEAR);
            } catch (ParseException e) {
                throw new ClientException(-1, -1, "Unknown schedule timestamp format: " + schedule);
            }
        } else {
            triggerURL = "time:" + schedule;
        }
        if (description != null) {
            h.setHeaderAttribute("description", description);
        }
        if (clientId != null) {
            h.setHeaderAttribute("clientid", clientId);
        }
        h.setHeaderAttribute("keeprequestresponse", "true");
        h.setSchedule(triggerURL);

        return doSimpleSend(out, method);
    }

   

    @Override
    public void setAllowCompression(boolean allowCompression) {
        this.allowCompression = allowCompression;
    }

    @Override
    public void setForceGzip(boolean forceGzip) {
        this.forceGzip = forceGzip;
    }

    @Override
    public SystemInfoProvider getSystemInfoProvider() {
        if (this.systemInfoProvider == null) {
            return SystemInfoFactory.getSystemInfo();
        }
        return systemInfoProvider;
    }

    @Override
    public void setSystemInfoProvider(SystemInfoProvider sip) {
        this.systemInfoProvider = sip;
    }

    @Override
    public SessionTokenProvider getSessionTokenProvider() {
        if (sessionTokenProvider == null) {
            return SessionTokenFactory.getSessionTokenProvider();
        }
        return this.sessionTokenProvider;
    }

    @Override
    public void setSessionTokenProvider(SessionTokenProvider stp) {
        this.sessionTokenProvider = stp;
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

}
