package org.dexels.servlet.smtp;


import javax.servlet.ServletResponse;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import java.io.PrintWriter;
import java.io.*;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpServletResponse implements ServletResponse {

    private SmtpOutputStream output = null;
    private String recipient = "";
    private String host = "";
    private String from = "";
    private String contentType = "text/plain";

    public SmtpServletResponse() {
        output = new SmtpOutputStream();
    }

    public String getCharacterEncoding() {
        throw new java.lang.UnsupportedOperationException("Method getCharacterEncoding() not yet implemented.");
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    public PrintWriter getWriter() throws IOException {
        return new java.io.PrintWriter(new java.io.OutputStreamWriter(output));
    }

    public void setFrom(String from) {
        this.from = from;
    }

    protected String getFrom() {
        return this.from;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    protected String getRecipient() {
        return this.recipient;
    }

    public void setHost(String host) {
        this.host = host;
    }

    protected String getHost() {
        return this.host;
    }

    protected String getSubject() {
        return this.output.getSubject();
    }

    public void setSubject(String subject) {
        output.setSubject(subject);
    }

    public void setContentLength(int len) {
        throw new java.lang.UnsupportedOperationException("Method setContentLength() not yet implemented.");
    }

    public void setContentType(String type) {
        this.contentType = type;
    }

    protected String getContentType() {
        return this.contentType;
    }

    public void setBufferSize(int size) {
        throw new java.lang.UnsupportedOperationException("Method setBufferSize() not yet implemented.");
    }

    public int getBufferSize() {
        throw new java.lang.UnsupportedOperationException("Method getBufferSize() not yet implemented.");
    }

    public void flushBuffer() throws IOException {
        throw new java.lang.UnsupportedOperationException("Method flushBuffer() not yet implemented.");
    }

    public boolean isCommitted() {
        throw new java.lang.UnsupportedOperationException("Method isCommitted() not yet implemented.");
    }

    public void reset() {
        throw new java.lang.UnsupportedOperationException("Method reset() not yet implemented.");
    }

    // toevoeging J2EE_1.3
    public void resetBuffer() {
        throw new java.lang.UnsupportedOperationException("Method resetBuffer() not yet implemented.");
    }

    // end toevoeging
    public void setLocale(Locale loc) {
        throw new java.lang.UnsupportedOperationException("Method setLocale() not yet implemented.");
    }

    public Locale getLocale() {
        throw new java.lang.UnsupportedOperationException("Method getLocale() not yet implemented.");
    }
}
