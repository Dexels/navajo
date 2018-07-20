package com.dexels.navajo.adapter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dexels.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.ConditionErrorException;

public class RESTAdapter extends NavajoMap {
    private final static Logger logger = LoggerFactory.getLogger(RESTAdapter.class);
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_READ_TIMEOUT = 60 * 1000; // 1 min
    
    private JSONTML json;
    
    public boolean removeTopMessage = false;
    public String url;
    public String method;
    public int responseCode;
    public String responseMessage;
    public String topMessage = "Response";
    public String parameterName = null;
    public String parameterValue = null;
    public String headerKey = null;
    public String headerValue = null;
    
    private int connectTimeOut = DEFAULT_CONNECT_TIMEOUT;
    private int readTimeOut = DEFAULT_READ_TIMEOUT;
    
    private int messagesPerRequest;
    private String rawResult;
    private String dateFormat;
    
    protected List<String> parameters = new ArrayList<String>();
    protected Map<String, String> headers = new HashMap<String, String>();
    private String textContent;
    private boolean jsonResponse = true;

    
    public RESTAdapter() {
        json = JSONTMLFactory.getInstance();
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public void setRemoveTopMessage(boolean removeTopMessage) {
        this.removeTopMessage = removeTopMessage;
    }

    public void setUrl(String url) {
        this.url = url.trim();
    }
    
    
    public void setTextContent(String s) {
        textContent = s;
    }
    public String getTextContent() {
        return textContent;
    }
    

    private void addParameter() {
        parameters.add(parameterName + "=" + parameterValue);
        parameterName = null;
        parameterValue = null;
    }

    public void setParameterName(String name) {
        parameterName = name;
        if (parameterValue != null) {
            addParameter();
        }
    }

    public void setParameterValue(String value) {
        parameterValue = value;
        if (parameterName != null) {
            addParameter();
        }
    }

    private void addHeader() {
        headers.put(headerKey, headerValue);
        headerKey = null;
        headerValue = null;
    }

    public void setHeaderKey(String key) {
        headerKey = key;
        if (headerValue != null) {
            addHeader();
        }
    }

    public void setHeaderValue(String value) {
        headerValue = value;
        if (headerKey != null) {
            addHeader();
        }
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    @Deprecated
    public void setDateformat(String format) {
        logger.warn("Deprecated dateFormat!");
        dateFormat = format;
    }

    public void setMessagesPerRequest(int count) {
        this.messagesPerRequest = count;
    }

    
    public void setJsonResponse(boolean jsonResponse) {
        this.jsonResponse  = jsonResponse;
    }

    @Override
    public void setDoSend(String method) throws UserException, ConditionErrorException, SystemException {

        if (messagesPerRequest < 1 || useCurrentMessages == null) {
            setDoSend(method, prepareOutDoc());
            serviceCalled = true;
        } else {
            String[] messages = useCurrentMessages.split(",");
            Navajo copy = outDoc.copy();
            Navajo indoc = null;
            if (inDoc == null) {
                indoc = NavajoFactory.getInstance().createNavajo();
            } else {
                indoc  = inDoc.copy();
            }
            
            for (String msgName : messages) {
                Message msg = access.getOutputDoc().getMessage(msgName);

                if (msg != null && msg.isArrayMessage()) {
                    
                    Message outMsg = NavajoFactory.getInstance().createMessage(outDoc, msgName);
                    outMsg.setType("array");
                    int counter = 0;
                    int totalCounter = 0;
                    for (Message element : msg.getElements()) {
                        outMsg.addElement(element);
                        counter++;
                        totalCounter++;
                        if (counter >= messagesPerRequest || totalCounter >= msg.getArraySize()) {
                            outDoc.addMessage(outMsg);
                            setDoSend(method, outDoc);
                            indoc.merge(inDoc);
                            
                            // Going to clear data 
                            outDoc = copy.copy();
                            outMsg = NavajoFactory.getInstance().createMessage(outDoc, msgName);
                            outMsg.setType("array");
                            counter = 0;
                        }
                    }
                    inDoc = indoc.copy();
                } else {
                    throw new UserException(2, "Message "+msgName+ "not found or not array message!");
                }

            }
            serviceCalled = true;
        }
    }

    @Override
    public void setMethod(String method) {
        this.method = method.trim();
    }

    public void setDoSend(String url, Navajo od) throws UserException, ConditionErrorException, SystemException {
        this.url = url.trim();
        
        if (dateFormat != null && !dateFormat.equals("")) {
            json.setDateFormat(new SimpleDateFormat(dateFormat));
        }
       
        Writer w = new StringWriter();
        Binary bContent = new Binary();
        if (textContent == null) {
            // Remove globals and parms message
            if (od.getMessage("__globals__") != null)  od.removeMessage("__globals__");

            if (od.getMessage(Message.MSG_PARAMETERS_BLOCK) != null) od.removeMessage(Message.MSG_PARAMETERS_BLOCK);
            if (od.getMessage(Message.MSG_TOKEN_BLOCK) != null) od.removeMessage(Message.MSG_TOKEN_BLOCK);
            if (od.getMessage(Message.MSG_AAA_BLOCK) != null) od.removeMessage(Message.MSG_AAA_BLOCK);
            
            
            try {
                json.format(od, w, true);
                
                bContent.getOutputStream().write(w.toString().getBytes("UTF-8"));
            } catch (Exception e) {
                logger.error("Exception on parsing input navajo as JSON! Not performing REST call!");
                throw new UserException(e.getMessage(), e);
            }
        } else {
            try {
                bContent.getOutputStream().write(textContent.toString().getBytes("UTF-8"));
            } catch (IOException e) {
                logger.error("IOException on writing textcontent! Not performing REST call!");
                throw new UserException(e.getMessage(), e);
            }
        }
       
        HTTPMap http = new HTTPMap();

        setupHttpMap(http, bContent);
        http.setDoSend(true);
        Binary result = http.getResult();

        responseCode = http.getResponseCode();
        responseMessage = http.getResponseMessage();
        try {
            if (result == null) {
                throw new UserException(-1, "Null result");
            }
            
            
            if (responseCode >= 300) {
                logger.warn("Got a non-200 response code: {}!", responseCode);
                if (breakOnException) {
                    throw new UserException(responseCode, responseMessage);
                }
            }
            rawResult = new String(result.getData());
            if (jsonResponse) {
                if (http.getResponseContentType() != null && http.getResponseContentType().contains("application/json")) {
                    try {
                        inDoc = json.parse(result.getDataAsStream(), topMessage);
                    } catch (Throwable t) {
                        logger.warn("Unable to parse response as JSON!", t);
                        if (breakOnException) {
                            throw t;
                        }
                    }
                } else if (http.getResponseContentType() == null ) {
                    logger.info("No response content type - creating empty navajo as response");
                    inDoc = NavajoFactory.getInstance().createNavajo();
                } else {
                    logger.warn("Unexpected output content type: {}", http.getResponseContentType());
                    if ( breakOnException) {
                        throw new UserException(-1, "Unexpected content type: " + http.getResponseContentType());
                    }
                }
            } else {
                logger.debug("Non-json response - creating empty Navajo");
                inDoc = NavajoFactory.getInstance().createNavajo();
            }
        } catch (Throwable e) {
            logger.error("Exception on getting response", e);
            if (breakOnException) {
                logger.warn("Raw response data: {}", rawResult);
                throw new UserException(e.getMessage(), e);
            } else {
                logger.warn("Exception on getting response, but breakOnException was set. Continuing!");
            }
        }
        if (inDoc == null) {
            logger.warn("No indoc - creating empty one");
            inDoc = NavajoFactory.getInstance().createNavajo();
        }
    }

    private void setupHttpMap(HTTPMap http, Binary content) throws UserException {
        try {
            http.load(access);
        } catch (MappableException e) {
            throw new UserException(e.getMessage(), e);
        }
        String fullUrl = url;
        for (int i = 0; i < parameters.size(); i++) {
            if (i == 0) {
                fullUrl += "?";
            } else {
                fullUrl += "&";
            }
            fullUrl += parameters.get(i);
        }

        for (String key : headers.keySet()) {
            http.setHeaderKey(key);
            http.setHeaderValue(headers.get(key));
        }

        http.setUrl(fullUrl);
        http.setHeaderKey("Accept");
        http.setHeaderValue("application/json");
        http.setMethod(method);

        if (method.equals("POST") || method.equals("PUT")) {
            http.setContent(content);
            http.setContentType("application/json");
            http.setContentLength(content.getLength());
        }
        http.trustAll();
        if (username != null && password != null) {
            // Use HTTP Basic auth - should only be used over HTTPS!
            String authString = username + ":" + password;
            byte[] bytes = authString.getBytes(Charset.forName("UTF-8"));
            String encoded = Base64.encode(bytes, 0, bytes.length, 0, "");
            http.setHeaderKey("Authorization");
            http.setHeaderValue("Basic " + encoded);
        }
        http.setReadTimeOut(readTimeOut);
        http.setConnectTimeOut(connectTimeOut);
        
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getDateformat() {
        return dateFormat;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public int messagesPerRequest() {
        return messagesPerRequest;
    }

    /**
     * @return Returns the result of the REST call, without attempting to make
     *         sensible data of it again (e.g. parse as JSON). Can be useful for
     *         error handling
     */
    public String getRawResult() {
        return rawResult;
    }
}
