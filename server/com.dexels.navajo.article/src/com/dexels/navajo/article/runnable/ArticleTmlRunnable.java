package com.dexels.navajo.article.runnable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.article.impl.ArticleBaseServlet;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AsyncRequest;
import com.dexels.navajo.script.api.RequestQueue;
import com.dexels.navajo.script.api.TmlRunnable;
import com.dexels.oauth.api.Client;

@Deprecated
public class ArticleTmlRunnable implements TmlRunnable{
    private final static Logger logger = LoggerFactory.getLogger(ArticleTmlRunnable.class);

    private final ArticleRuntime runtime;
    private final ArticleContext context;
    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;
    
    private final Continuation continuation;
    
    private Navajo requestNavajo;
    private RequestQueue requestQueue;

    private long scheduledAt;

    
    public ArticleTmlRunnable(HttpServletRequest req, HttpServletResponse resp, Client client, ArticleRuntime runtime, ArticleContext context) {
        this.httpRequest = req;
        this.httpResponse = resp;
        this.runtime = runtime;
        this.context = context;

        continuation = ContinuationSupport.getContinuation(httpRequest);
        continuation.setTimeout(5*60*1000L); // 5 minutes
        continuation.suspend(httpResponse);
        
        requestNavajo = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(requestNavajo, runtime.getArticleName(), runtime.getUsername(),"", -1);
        requestNavajo.addHeader(h);
        
        if (continuation.isExpired()) {
            logger.warn("Expired continuation!");
            abort("Internal server error");
        }
        if (!continuation.isInitial()) {
            logger.warn("Non-initial continuation!");
            abort("Internal server error");
        }
    }
    
    @Override
    public void run() {
        try {
            runInternal();
        } catch (Throwable t1) {
           fail(t1);
        } finally {
            continuation.complete();
            runtime.getAccess().setFinished();
            NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(runtime.getAccess()));

            if (getRequestQueue() != null) { // Check whether there is a request queue available.
                getRequestQueue().finished();
            }
        }
        

    }

    private void runInternal() throws APIException {
        try {
            runtime.getAccess().setQueueId(this.getRequestQueue().getId());
            runtime.getAccess().queueTime = (int) (System.currentTimeMillis() - scheduledAt);
            
            runtime.execute(context);
            
            runtime.getAccess().setExitCode(Access.EXIT_OK);
        } catch (NoJSONOutputException e) {
            httpResponse.setContentType(e.getMimeType());
            try {
                IOUtils.copy(e.getStream(), httpResponse.getOutputStream());
            } catch (IOException e1) {
                throw new APIException(e1.getMessage(), e1, APIErrorCode.InternalError);
            }
            return;

        } catch (APIException apiException) {
            if (apiException.getErrorCode() == APIErrorCode.InternalError) {
                logExceptionToAccess(runtime.getAccess(), apiException, createNavajoFromRequest(httpRequest));
            } else if (apiException.getErrorCode() == APIErrorCode.ConditionError) {
                runtime.getAccess().setExitCode(Access.EXIT_VALIDATION_ERR);
            }

            throw apiException;
        } catch (Throwable e) {
            logExceptionToAccess(runtime.getAccess(), e, createNavajoFromRequest(httpRequest));
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        }
    }

    private void logExceptionToAccess(Access a, Throwable e, Navajo navajo) {
        a.setExitCode(Access.EXIT_EXCEPTION);
        // Create a navajo of the input
        a.setInDoc(navajo);
        a.setException(e);
    }

    private Navajo createNavajoFromRequest(HttpServletRequest req) {
        Navajo navajo = NavajoFactory.getInstance().createNavajo();
        Message properties = NavajoFactory.getInstance().createMessage(navajo, "Input");
        navajo.addMessage(properties);
        for (Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            Property property = NavajoFactory.getInstance().createProperty(navajo, entry.getKey(), null, null, null);
            property.setType("string");

            if (entry.getValue() != null && entry.getValue().length > 0) {
                property.setValue(String.join("|", entry.getValue()));
            }
            properties.addProperty(property);
        }
        return navajo;
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void setCommitted(boolean b) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setScheduledAt(long scheduledAt) {
       this.scheduledAt = scheduledAt;

    }

    @Override
    public void endTransaction() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Navajo getInputNavajo() throws IOException {
        return requestNavajo;
    }

    @Override
    public void setResponseNavajo(Navajo n) {

    }

    @Override
    public Navajo getResponseNavajo() {
        return null;
    }

    @Override
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public void setRequestQueue(RequestQueue rq) {
        this.requestQueue = rq;

    }

    @Override
    public boolean isAborted() {
        return false;
    }

    @Override
    public void abort(String reason) {
        fail(new ServletException(reason));

    }

    private void fail(Throwable t1) {
        
        APIException exception = (t1 instanceof APIException) ? (APIException) t1
                : new APIException(t1.getMessage(), t1, APIErrorCode.InternalError);

        // If we get an internal error, we want to know about it in our logging system.
        if (exception.getErrorCode().getHttpStatusCode() >= 500) {
            logger.error("Error {}", t1);
        }

        try {
            ArticleBaseServlet.writeJSONErrorResponse(exception, httpResponse);
        } catch (Throwable t2) {
            logger.error("Failed to write JSON error response", t2);

            try {
                APIErrorCode internal = APIErrorCode.InternalError;
                httpResponse.sendError(internal.getHttpStatusCode(), internal.getDescription());
            } catch (Throwable t3) {
                // We've failed to return the error to the user. We cannot do anything anymore.
                logger.error("Failed to deliver error {}", t3);
            }
        } finally {
            continuation.complete();  
        }

    }
    
    @Override
    public AsyncRequest getRequest() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getNavajoInstance() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        if (name.equals("ip")) {
            return runtime.getAccess().getIpAddress();
        }
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<String> getAttributeNames() {
        return Collections.EMPTY_SET;
    }

    @Override
    public void writeOutput(Navajo inDoc, Navajo outDoc)
            throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException {

    }

}
