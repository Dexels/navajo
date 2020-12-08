package com.dexels.navajo.client.async.jetty.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpProxy;
import org.eclipse.jetty.client.ProxyConfiguration;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.NavajoResponseCallback;
import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.script.api.TmlRunnable;


public class AsyncClientImpl implements ManualAsyncClient {

    static {
        AsyncClientFactory.setInstance(AsyncClientImpl.class);
    }

    private static final Logger logger = LoggerFactory.getLogger(AsyncClientImpl.class);

    private static final int MAX_RESULT_SIZE = 64 * 1024 * 1024;  // 64 MB

    private HttpClient httpClient;

    private String name;

    private String server;

    private String username;

    private String password;

    private boolean useHttps;

    private boolean closeAfterUse;

    private int actualCalls;

    public AsyncClientImpl() throws Exception {

        httpClient = new HttpClient(new SslContextFactory.Client());
        httpClient.setMaxConnectionsPerDestination(100);
        configureProxy(httpClient);
        httpClient.start();
    }

    private void configureProxy(HttpClient httpClient) {

        String host = System.getenv("httpProxyHost");
        String port = System.getenv("httpProxyPort");
        if (host == null || port == null) {
            return;
        }

        ProxyConfiguration proxyConfig = httpClient.getProxyConfiguration();
        HttpProxy proxy = new HttpProxy(host, Integer.parseInt(port));
        proxyConfig.getProxies().add(proxy);
    }

    public void activate(Map<String, Object> settings) {

        String serverString = (String) settings.get("server");
        if (serverString == null) {
            serverString = (String) settings.get("url");
        }

        setName((String) settings.get("name"));
        setServer(serverString);
        setUsername((String) settings.get("username"));
        setPassword((String) settings.get("password"));

        useHttps = false;
        closeAfterUse = false;
        actualCalls = 0;
    }

    public void deactivate() {
        close();
    }

    @Override
    public void callService(Navajo input, String service, final NavajoResponseHandler continuation) throws IOException {

        if (input == null) {
            input = NavajoFactory.getInstance().createNavajo();
        } else {
            input = input.copy();
        }
        input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));
        callService(server, input, continuation, null);
    }

    @Override
    public Navajo callService(final Navajo input, final String service) throws IOException {

        final Object semaphore = new Object();
        final Set<Navajo> result = new HashSet<>();

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
            throw new IOException("Error calling remote navajo: " + server, nrh.getCaughtException());
        }

        return result.iterator().next();
    }

    @Override
    public void callService(String url, String username, String password, Navajo input, String service,
            final NavajoResponseHandler continuation, Integer timeout) throws IOException {

        logger.info("Calling remote navajo async for url: {} ", url);

        if (input == null) {
            input = NavajoFactory.getInstance().createNavajo();
        } else {
            input = input.copy();
        }
        input.addHeader(NavajoFactory.getInstance().createHeader(input, service, username, password, -1));
        callService(url, input, continuation, timeout);
    }

    // Only used from Rhino
    @Override
    public void callService(Access inputAccess, Navajo input, final String service, final TmlRunnable onSuccess,
            final TmlRunnable onFail, final NavajoResponseCallback navajoResponseCallback) throws IOException {

        final Access currentAccess = inputAccess.cloneWithoutNavajos();

        if (input == null) {
            input = NavajoFactory.getInstance().createNavajo();
        }
        currentAccess.setInDoc(input);

        Header header = input.getHeader();
        if (header == null) {
            header = NavajoFactory.getInstance().createHeader(input, service, currentAccess.rpcUser,
                    currentAccess.rpcUser, -1);
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
                if (onSuccess != null) {
                    onSuccess.setResponseNavajo(n);
                    if (navajoResponseCallback != null) {
                        navajoResponseCallback.responseReceived(n);
                    }
                    setActualCalls(getActualCalls() - 1);
                    SchedulerRegistry.submit(onSuccess, false);
                }
            }

            @Override
            public synchronized void onFail(Throwable t) throws IOException {
                caughtException = t;
                logger.warn("Error: ", caughtException);
                setActualCalls(getActualCalls() - 1);
                try {
                    if (onFail != null) {
                        SchedulerRegistry.submit(onFail, false);
                    }
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

    private void callService(final String url, Navajo input, final NavajoResponseHandler continuation, Integer timeout)
            throws IOException {

        logger.info("Calling service: {} at {} ", input.getHeader().getRPCName(), url);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        input.write(baos);
        final byte[] byteArray = baos.toByteArray();

        httpClient
            .newRequest(url)
            .method(HttpMethod.POST)
            .header("Accept-Encoding", null)
            .timeout(40, TimeUnit.SECONDS)
            .idleTimeout(20, TimeUnit.SECONDS)
            .content(new BytesContentProvider(byteArray), "text/xml; charset=utf-8")
            .onRequestFailure((request, throwable) -> {
                logger.error("Request failed: HTTP call to: " + url + " failed: {}", throwable);
                if (continuation != null) {
                    try {
                        continuation.onFail(throwable);
                    } catch (IOException exc) {
                        logger.error("Error: ", exc);
                    }
                }
                if (closeAfterUse) {
                    close();
                }
            }).onResponseFailure((response, throwable) -> {
                logger.error("Response failed: HTTP call to: " + url + " failed: {}", throwable);
                if (continuation != null) {
                    try {
                        continuation.onFail(throwable);
                    } catch (IOException exc) {
                        logger.error("Error: ", exc);
                    }
                }
                if (closeAfterUse) {
                    close();
                }
            }).send(new BufferingResponseListener(MAX_RESULT_SIZE) {

                @Override
                public void onComplete(Result result) {
                    try {
                        Navajo response = NavajoFactory.getInstance().createNavajo(getContentAsInputStream());
                        if (continuation != null) {
                            continuation.onResponse(response);
                        }
                    } catch (UnsupportedOperationException exc) {
                        logger.error("Error: ", exc);
                    } finally {
                        if (closeAfterUse) {
                            close();
                        }
                        setActualCalls(getActualCalls() - 1);
                    }
                }

            });
    }

    @Override
    public void close() {

        try {
            httpClient.stop();
        } catch (Exception exc) {
            if (!(exc.getCause() instanceof InterruptedException)) {
                logger.error("Error shutting down httpclient: ", exc);
            }
        }
    }

    @Override
    public String getServer() {
        return server;
    }

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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean useHttps() {
        return useHttps;
    }

    @Override
    public void setHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }

    @Override
    public void setCloseAfterUse(boolean closeAfterUse) {
        this.closeAfterUse = closeAfterUse;
    }

    private synchronized int getActualCalls() {
        return actualCalls;
    }

    private synchronized void setActualCalls(int actualCalls) {

        logger.debug("Calls now: {}", actualCalls);
        this.actualCalls = actualCalls;
    }

    @Override
    public void setClientCertificate(String algorithm, String keyStoreType, InputStream source, char[] password)
            throws IOException {}

}
