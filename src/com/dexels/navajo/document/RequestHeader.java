

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;


import javax.servlet.http.HttpServletRequest;
//import com.dexels.navajo.util.Util;
import java.util.*;


public class RequestHeader {

    public static final String HTTP_AUTH_TYPE = "auth-type";
    public static final String HTTP_CHAR_ENCODING = "char-encoding";
    public static final String HTTP_CONTENT_TYPE = "content-type";
    public static final String HTTP_METHOD = "method";
    public static final String HTTP_PATH_INFO = "path-info";
    public static final String HTTP_PATH_TRANSLATED = "path-translated";
    public static final String HTTP_PROTOCOL = "protocol";
    public static final String HTTP_QUERY_STRING = "query-string";
    public static final String HTTP_REMOTE_ADDR = "remote-address";
    public static final String HTTP_REMOTE_HOST = "remote-host";
    public static final String HTTP_REMOTE_USER = "remote-user";
    public static final String HTTP_REQUEST_URI = "request-uri";
    public static final String HTTP_SCHEME = "scheme";
    public static final String HTTP_SERVER_NAME = "server-name";
    public static final String HTTP_SERVER_PORT = "server-port";
    public static final String HTTP_SERVLET_PATH = "servlet-path";

    private Hashtable allHeaders = null;

    public RequestHeader(HttpServletRequest request) {
        allHeaders = getRequestInfo(request);
    }

    public String getHeader(String name) {

        String value = "";

        if (allHeaders.get(name) != null) {
            value = (String) allHeaders.get(name);
        }
        return value;
    }

    private Hashtable getRequestInfo(HttpServletRequest request) {

        Hashtable headers = new Hashtable();

        Enumeration names = request.getHeaderNames();

        if (request.getAuthType() != null)
            headers.put(RequestHeader.HTTP_AUTH_TYPE, request.getAuthType());
        if (request.getCharacterEncoding() != null)
            headers.put(RequestHeader.HTTP_CHAR_ENCODING, request.getCharacterEncoding());
        if (request.getContentType() != null)
            headers.put(RequestHeader.HTTP_CONTENT_TYPE, request.getContentType());
        if (request.getMethod() != null)
            headers.put(RequestHeader.HTTP_METHOD, request.getMethod());
        if (request.getPathInfo() != null)
            headers.put(RequestHeader.HTTP_PATH_INFO, request.getPathInfo());
        if (request.getPathTranslated() != null)
            headers.put(RequestHeader.HTTP_PATH_TRANSLATED, request.getPathTranslated());
        if (request.getProtocol() != null)
            headers.put(RequestHeader.HTTP_PROTOCOL, request.getProtocol());
        if (request.getQueryString() != null)
            headers.put(RequestHeader.HTTP_QUERY_STRING, request.getQueryString());
        if (request.getRemoteAddr() != null)
            headers.put(RequestHeader.HTTP_REMOTE_ADDR, request.getRemoteAddr());
        if (request.getRemoteHost() != null)
            headers.put(RequestHeader.HTTP_REMOTE_HOST, request.getRemoteHost());
        if (request.getRemoteUser() != null)
            headers.put(RequestHeader.HTTP_REMOTE_USER, request.getRemoteUser());
        if (request.getRequestURI() != null)
            headers.put(RequestHeader.HTTP_REQUEST_URI, request.getRequestURI());
        if (request.getScheme() != null)
            headers.put(RequestHeader.HTTP_SCHEME, request.getScheme());
        if (request.getServerName() != null)
            headers.put(RequestHeader.HTTP_SERVER_NAME, request.getServerName());

        headers.put(RequestHeader.HTTP_SERVER_PORT, request.getServerPort() + "");

        if (request.getServletPath() != null)
            headers.put(RequestHeader.HTTP_SERVLET_PATH, request.getServletPath());

//        Util.debugLog("Leaving requestheader");
        return headers;
    }

}
