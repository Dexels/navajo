package org.dexels.servlet.smtp;


import javax.servlet.ServletRequest;
import java.util.Enumeration;
import javax.servlet.ServletInputStream;
import java.util.Locale;
import java.io.BufferedReader;
import javax.servlet.RequestDispatcher;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpServletRequest implements ServletRequest {

    protected Message message;
    private SmtpInputStream input = null;
    private String content;

    public SmtpServletRequest() {
        input = new SmtpInputStream();
    }

    private String getMessageText(Message currentMessage) throws IOException, MessagingException {
        String text = currentMessage.getContentType();
        boolean attachmentOnly;

        if (currentMessage.isMimeType("text/*")) {
            text = (String) currentMessage.getContent();
        } else if (currentMessage.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) currentMessage.getContent();
            BodyPart firstPart = multipart.getBodyPart(0);

            if (firstPart.isMimeType("text/*")) {
                text = (String) firstPart.getContent();
                attachmentOnly = false;
            } else {
                text = "not a text message";
                attachmentOnly = true;
            }
        }
        System.out.println("Done");
        return text;
    }

    protected void setMessage(Message message) throws javax.mail.MessagingException, java.io.IOException {
        this.message = message;
        this.content = getMessageText(message);
        this.input.content = content;
    }

    public Object getAttribute(String name) {
        return null;
    }

    public Enumeration getAttributeNames() {
        throw new java.lang.UnsupportedOperationException("Method getAttributeNames() not yet implemented.");
    }

    public String getCharacterEncoding() {
        throw new java.lang.UnsupportedOperationException("Method getCharacterEncoding() not yet implemented.");
    }

    public int getContentLength() {
        if (content != null)
            return content.length();
        else
            return 0;
    }

    public String getContentType() {
        try {
            if (message != null)
                return message.getContentType();
            else
                return "";
        } catch (javax.mail.MessagingException me) {
            return "";
        }
    }

    public ServletInputStream getInputStream() throws IOException {
        return input;
    }

    public String getParameter(String name) {
        throw new java.lang.UnsupportedOperationException("Method getParameter() not yet implemented.");
    }

    // toevoeging J2EE_1.3
    public java.util.Map getParameterMap() {
        throw new java.lang.UnsupportedOperationException("Method getParameterMap() not yet implemented.");
    }

    public String getParameterMap(String name) {
        throw new java.lang.UnsupportedOperationException("Method getParameterMap(String) not yet implemented.");
    }

    // end toevoeging
    public Enumeration getParameterNames() {
        throw new java.lang.UnsupportedOperationException("Method getParameterNames() not yet implemented.");
    }

    public String[] getParameterValues(String name) {
        throw new java.lang.UnsupportedOperationException("Method getParameterValues() not yet implemented.");
    }

    public String getProtocol() {
        return "IMAP";
    }

    public String getScheme() {
        throw new java.lang.UnsupportedOperationException("Method getScheme() not yet implemented.");
    }

    public String getServerName() {
        throw new java.lang.UnsupportedOperationException("Method getServerName() not yet implemented.");
    }

    public int getServerPort() {
        throw new java.lang.UnsupportedOperationException("Method getServerPort() not yet implemented.");
    }

    public BufferedReader getReader() throws IOException {
        return new java.io.BufferedReader(new java.io.InputStreamReader(input));
    }

    public String getFrom() {
        return getRemoteAddr();
    }

    public String getSubject() {
        try {
            return message.getSubject();
        } catch (javax.mail.MessagingException me) {
            return "";
        }
    }

    public String getRemoteAddr() {
        try {
            Address[] address = message.getFrom();

            return Utils.getEmailAdress(address[0].toString());
        } catch (javax.mail.MessagingException me) {
            return "";
        }
    }

    public String getRemoteHost() {
        throw new java.lang.UnsupportedOperationException("Method getRemoteHost() not yet implemented.");
    }

    public void setAttribute(String name, Object o) {
        throw new java.lang.UnsupportedOperationException("Method setAttribute() not yet implemented.");
    }

    public void setCharacterEncoding(String name) {
        throw new java.lang.UnsupportedOperationException("Method setCharacterEncoding(String) not yet implemented.");
    }

    public void removeAttribute(String name) {
        throw new java.lang.UnsupportedOperationException("Method removeAttribute() not yet implemented.");
    }

    public Locale getLocale() {
        throw new java.lang.UnsupportedOperationException("Method getLocale() not yet implemented.");
    }

    public Enumeration getLocales() {
        throw new java.lang.UnsupportedOperationException("Method getLocales() not yet implemented.");
    }

    public boolean isSecure() {
        throw new java.lang.UnsupportedOperationException("Method isSecure() not yet implemented.");
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        throw new java.lang.UnsupportedOperationException("Method getRequestDispatcher() not yet implemented.");
    }

    public String getRealPath(String path) {
        throw new java.lang.UnsupportedOperationException("Method getRealPath() not yet implemented.");
    }
}
