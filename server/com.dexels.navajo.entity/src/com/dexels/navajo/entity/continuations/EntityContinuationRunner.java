package com.dexels.navajo.entity.continuations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.DispatcherInterface;

public class EntityContinuationRunner implements TmlRunnable {
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final static Logger logger = LoggerFactory.getLogger(EntityContinuationRunner.class);
    private final static String QUEUE_NAME = "normalPool";

    private final Continuation continuation;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private Navajo requestNavajo = null;
    private Navajo responseNavajo = null;

    private boolean isAborted = false;
    private boolean committed = false;
    private long startedAt = -1;
    private String instance;
    private String outputFormat;
    private String outputEtag;

    private RequestQueue myQueue;
    private final Map<String, Object> attributes;

    private EntityDispatcher entityDispatcher;
    private DispatcherInterface dispatcher;

    public EntityContinuationRunner(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        continuation = ContinuationSupport.getContinuation(request);
        continuation.setTimeout(10000000);
        continuation.suspend();

        attributes = new HashMap<String, Object>();
        attributes.put("queueName", QUEUE_NAME);
    }

    public void setEntityDispatcher(EntityDispatcher ed) {
        this.entityDispatcher = ed;
    }

    public DispatcherInterface getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherInterface dispatcher) {
        this.dispatcher = dispatcher;
    }

    public HttpServletRequest getHttpRequest() {
        return request;
    }

    public HttpServletResponse getHttpResponse() {
        return response;
    }

    @Override
    public void run() {
        try {
            this.startedAt = System.currentTimeMillis();
            execute();
        } catch (NavajoDoneException e) {
            logger.info("NavajoDoneException caught. This thread fired a continuation. Another thread will finish it in the future.");
        } catch (Exception e) {
            fail(e);
        }
    }

    private void execute() throws IOException {
        try {
            entityDispatcher.run(this);
        } finally {
            endTransaction();
        }
    }

    public void suspendContinuation() {
        continuation.suspend();
    }

    private void fail(Exception e) {
        try {
            response.sendError(500, e.getMessage());
        } catch (IOException e1) {
            logger.error("Error: ", e1);
        }
    }

    public long getStartedAt() {
        return startedAt;
    }

    @Override
    public boolean isCommitted() {
        return committed;
    }

    @Override
    public void setCommitted(boolean b) {
        this.committed = b;

    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;

    }

    public void setOutputEtag(String etag) {
        this.outputEtag = etag;

    }

    @Override
    public void setScheduledAt(long scheduledAt) {
        // Not supported
    }

    @Override
    public void endTransaction() throws IOException {
        // Only end-points are allowed to cache - no servers in between
        response.setHeader("Cache-Control", "private");

        writeOutput();
        continuation.complete();

        if (getRequestQueue() != null) { // Check whether there is a request queue available.
            getRequestQueue().finished();
        }

    }

    private void writeOutput() throws IOException {
        if (responseNavajo == null) {
            fail(new ServletException("No output found"));
            return;
        }

        if (responseNavajo.getMessage("errors") != null) {
            String status = responseNavajo.getMessage("errors").getProperty("Status").toString();
            if (status.equals("304")) {
                // No content
                logger.debug("Returning HTTP code 304 - not modified");
                return;
            }
            response.setHeader("Connection", "close");
        }
        if (outputEtag != null) {
            response.setHeader("etag", outputEtag);
        }
        if (outputFormat.equals("json")) {
            response.setHeader("content-type", "application/json");
            Writer w = new OutputStreamWriter(response.getOutputStream());
            JSONTML json = JSONTMLFactory.getInstance();
            json.setDateFormat(DATEFORMAT);
            try {
                json.format(responseNavajo, w, true);
            } catch (Exception e) {
                logger.error("Error in writing entity output in JSON!", e);
                fail(new ServletException("Error producing output"));
                return;
            }
            w.close();
        } else if (outputFormat.equals("birt")) {
            response.setHeader("content-type", "text/xml");
            NavajoLaszloConverter.writeBirtXml(responseNavajo, response.getWriter());
        } else {
            response.setHeader("content-type", "text/xml");
            responseNavajo.write(response.getOutputStream());
        }
    }

    @Override
    public Navajo getInputNavajo() throws IOException {
        return requestNavajo;
    }

    @Override
    public void setResponseNavajo(Navajo n) {
        this.responseNavajo = n;

    }

    @Override
    public Navajo getResponseNavajo() {
        return responseNavajo;
    }

    @Override
    public RequestQueue getRequestQueue() {
        return myQueue;
    }

    @Override
    public void setRequestQueue(RequestQueue queue) {
        this.myQueue = queue;
    }

    @Override
    public boolean isAborted() {
        return isAborted;
    }

    @Override
    public void abort(String reason) {
        isAborted = true;
        fail(new ServletException(reason));

    }

    @Override
    public AsyncRequest getRequest() {
        throw new UnsupportedOperationException("EntityRunnable does not support getRequest");
    }

    @Override
    public String getUrl() {
        // Not supported
        return null;
    }

    @Override
    public String getNavajoInstance() {
        return this.instance;
    }

    public void setNavajoInstance(String instance) {
        this.instance = instance;
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    @Override
    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(attributes.keySet());
    }

    @Override
    public void writeOutput(Navajo inDoc, Navajo outDoc) throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException {
        throw new UnsupportedOperationException("EntityRunnable does not support writeOutput");

    }

}
