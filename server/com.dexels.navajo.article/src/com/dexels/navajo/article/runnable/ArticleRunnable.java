package com.dexels.navajo.article.runnable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.APIErrorCode;
import com.dexels.navajo.article.APIException;
import com.dexels.navajo.article.ArticleContext;
import com.dexels.navajo.article.ArticleRuntime;
import com.dexels.navajo.article.NoJSONOutputException;
import com.dexels.navajo.article.impl.ArticleBaseServlet;
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

public class ArticleRunnable implements TmlRunnable{
    private final static Logger logger = LoggerFactory.getLogger(ArticleRunnable.class);


    final ArticleRuntime runtime;
    final ArticleContext context;
    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    
    public ArticleRunnable(HttpServletRequest req, HttpServletResponse resp, ArticleRuntime runtime, ArticleContext context) {
        this.httpRequest = req;
        this.httpResponse = resp;
        this.runtime = runtime;
        this.context = context;
    }
    
    @Override
    public void run() {
        Access a = getAccessObject();

        try {
            runInternal(a);
        } catch (Throwable t1) {
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
            }
        }

    }

    private void runInternal(Access a) throws APIException {
        try {
            runtime.execute(context);

            httpResponse.setContentType("application/json; charset=utf-8");
            a.setExitCode(Access.EXIT_OK);

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
                logExceptionToAccess(a, apiException, createNavajoFromRequest(httpRequest));
            } else if (apiException.getErrorCode() == APIErrorCode.ConditionError) {
                a.setExitCode(Access.EXIT_VALIDATION_ERR);
            }

            throw apiException;
        } catch (Throwable e) {
            logExceptionToAccess(a, e, createNavajoFromRequest(httpRequest));
            throw new APIException(e.getMessage(), e, APIErrorCode.InternalError);
        } finally {
            a.setFinished();
            NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(a));
        }

    }

    private void logExceptionToAccess(Access a, Throwable e, Navajo navajo) {
        a.setExitCode(Access.EXIT_EXCEPTION);
        // Create a navajo of the input
        a.setInDoc(navajo);
        a.setException(e);
    }

    private Access getAccessObject() {
        String ip = httpRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.equals("")) {
            ip = httpRequest.getRemoteAddr();
        }

        Access a = new Access(-1, -1, runtime.getUsername(), "article/" + runtime.getArticleName(), "", "", "", null, false, null);
        a.setTenant(runtime.getInstance());
        a.rpcPwd = runtime.getToken().getCode();
        a.created = new Date();
        a.ipAddress = ip;
        a.setClientDescription("article");
        a.setClientToken("Client id: " + runtime.getToken().getClientId());

        return a;
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setCommitted(boolean b) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setScheduledAt(long currentTimeMillis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void endTransaction() throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public Navajo getInputNavajo() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setResponseNavajo(Navajo n) {
        // TODO Auto-generated method stub

    }

    @Override
    public Navajo getResponseNavajo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RequestQueue getRequestQueue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRequestQueue(RequestQueue rq) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isAborted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void abort(String string) {
        // TODO Auto-generated method stub

    }

    @Override
    public AsyncRequest getRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNavajoInstance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<String> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void writeOutput(Navajo inDoc, Navajo outDoc)
            throws IOException, FileNotFoundException, UnsupportedEncodingException, NavajoException {
        // TODO Auto-generated method stub

    }

}
