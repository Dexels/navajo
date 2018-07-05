package com.dexels.navajo.entity.continuations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.NavajoDoneException;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.navajo.server.DispatcherInterface;

public class EntityContinuationRunner implements TmlRunnable {
    private final static Logger logger = LoggerFactory.getLogger(EntityContinuationRunner.class);
 
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
    private String contentEncoding;
    private String acceptEncoding;

    public EntityContinuationRunner(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        continuation = ContinuationSupport.getContinuation(request);
        continuation.setTimeout(5*60*1000L); // 5 minutes
        continuation.suspend(response);
        
        if (continuation.isExpired()) {
            logger.warn("Expired continuation!");
            abort("Internal server error");
        }
        if (!continuation.isInitial()) {
            logger.warn("Non-initial continuation!");
            abort("Internal server error");
        }
        String entity = "invalid";
        if (request.getPathInfo() != null) 
        	entity = request.getPathInfo().substring(1);
        requestNavajo = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(requestNavajo, entity, null,"", -1);
        requestNavajo.addHeader(h);

        attributes = new HashMap<String, Object>();

        contentEncoding = request.getHeader("Content-Encoding");
        acceptEncoding = request.getHeader("Accept-Encoding");
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
        continuation.complete();
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
        if (response.getHeader("Cache-Control") != null && !response.getHeader("Cache-Control").contains("private")
                && response.getHeader("Cache-Control").contains("public")) {
            response.setHeader("Cache-Control", response.getHeader("Cache-Control").replace("public", "private"));
        } else if (response.getHeader("Cache-Control") == null) {
            response.setHeader("Cache-Control", "private");
        } else if (!response.getHeader("Cache-Control").contains("private")) {
            response.setHeader("Cache-Control", response.getHeader("Cache-Control") + ", private");
        }

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
        
        
        try (OutputStream out = getOutputStream(acceptEncoding, response.getOutputStream())) {
            if (outputFormat.equals("json")) {
                response.setHeader("content-type", "application/json");
                Writer w = new OutputStreamWriter(out);
                JSONTML json = JSONTMLFactory.getInstance();
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
                Writer w = new OutputStreamWriter(out);
                NavajoLaszloConverter.writeBirtXml(responseNavajo,w );
                w.close();
            } else {
                response.setHeader("content-type", "text/xml");
                responseNavajo.write(out);
            }
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
    
    private static OutputStream getOutputStreamByEncoding(String encoding, OutputStream source) throws IOException {
        if(AsyncRequest.COMPRESS_JZLIB.equals(encoding)) {
            return new DeflaterOutputStream(source);
        } else if(AsyncRequest.COMPRESS_GZIP.equals(encoding)) {
            return new java.util.zip.GZIPOutputStream(source);
        }
        // unsupported:
        return null;
    }
    private  OutputStream getOutputStream(String acceptEncoding, OutputStream source) throws IOException {
        if(acceptEncoding == null) {
            return source;
        } else {
            List<String> accepted = Arrays.asList(acceptEncoding.split(","));
            for (String encoding : accepted) {
                OutputStream o = getOutputStreamByEncoding(encoding, source);
                if(o!=null) {
                    response.setHeader("Content-Encoding", encoding);
                    return o;
                }
            }
            return source;
        }

    }

    public InputStream getRequestInputStream() throws IOException {
        if (contentEncoding != null && contentEncoding.equals(AsyncRequest.COMPRESS_JZLIB)) {
            return new InflaterInputStream(request.getInputStream());
        } else if (contentEncoding != null && contentEncoding.equals(AsyncRequest.COMPRESS_GZIP)) {
            return new java.util.zip.GZIPInputStream(request.getInputStream());
        }
        return request.getInputStream();
    }

}
