

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;

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

    private Object requestHeader = null;

    public RequestHeader(Object requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestHeader(String name) {
      try {
        String methodName = "get" + ((name.charAt(0)+"").toUpperCase()) + name.substring(1);
        Class c = requestHeader.getClass();
        java.lang.reflect.Method m = c.getMethod(methodName, null);
        String result = (String) m.invoke(requestHeader, null);
        return result;
      } catch (Exception e) {
        e.printStackTrace();
        return "";
      }
    }

}
