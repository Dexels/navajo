package org.dexels.servlet.smtp;


import javax.servlet.*;
import java.util.Enumeration;
import java.net.*;
import java.io.InputStream;
import java.io.*;
import java.util.*;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpServletContext implements ServletContext {

    protected Hashtable contextParameters = null;
    private Hashtable attributes = null;
    protected String serverInfo = "";
    protected Hashtable servlets = null;
    protected int majorVersion = 0;
    protected int minorVersion = 1;

    public SmtpServletContext() {
        attributes = new Hashtable();
    }

    protected synchronized void setServlets(Hashtable servlets) {
        this.servlets = servlets;
    }

    public ServletContext getContext(String uripath) {
        throw new java.lang.UnsupportedOperationException("Method getContext() not yet implemented.");
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public String getMimeType(String file) {
        throw new java.lang.UnsupportedOperationException("Method getMimeType() not yet implemented.");
    }

    public URL getResource(String path) throws MalformedURLException {
        throw new java.lang.UnsupportedOperationException("Method getResource() not yet implemented.");
    }

    public InputStream getResourceAsStream(String path) {
        throw new java.lang.UnsupportedOperationException("Method getResourceAsStream() not yet implemented.");
    }

    // toevoeging J2EE_1.3
    public Set getResourcePaths(String path) {
        throw new java.lang.UnsupportedOperationException("Method getResourceAsStream() not yet implemented.");
    }

    // end toevoeging
    public RequestDispatcher getRequestDispatcher(String path) {
        throw new java.lang.UnsupportedOperationException("Method getRequestDispatcher() not yet implemented.");
    }

    public RequestDispatcher getNamedDispatcher(String name) {
        throw new java.lang.UnsupportedOperationException("Method getNamedDispatcher() not yet implemented.");
    }

    public Servlet getServlet(String name) throws ServletException {
        return (Servlet) servlets.get(name);
    }

    public Enumeration getServlets() {
        return servlets.elements();
    }

    public String getServletContextName() {
        throw new java.lang.UnsupportedOperationException("Method getServletContexName() not yet implemented.");
    }

    public Enumeration getServletNames() {
        return servlets.keys();
    }

    public void log(String msg) {
        throw new java.lang.UnsupportedOperationException("Method log() not yet implemented.");
    }

    public void log(Exception exception, String msg) {

        /** @todo: Implement this javax.servlet.ServletContext method*/
        throw new java.lang.UnsupportedOperationException("Method log() not yet implemented.");
    }

    public void log(String message, Throwable throwable) {

        /** @todo: Implement this javax.servlet.ServletContext method*/
        throw new java.lang.UnsupportedOperationException("Method log() not yet implemented.");
    }

    public String getRealPath(String path) {

        /** @todo: Implement this javax.servlet.ServletContext method*/
        throw new java.lang.UnsupportedOperationException("Method getRealPath() not yet implemented.");
    }

    public String getServerInfo() {
        return this.serverInfo;
    }

    public String getInitParameter(String name) {
        if (contextParameters != null)
            return (String) contextParameters.get(name);
        else
            return "";
    }

    public Enumeration getInitParameterNames() {
        return contextParameters.keys();
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Enumeration getAttributeNames() {
        return attributes.keys();
    }

    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }
}
