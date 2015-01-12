package com.dexels.navajo.article.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ErrorCatchHttpResponseWrapper extends HttpServletResponseWrapper {


	  protected HttpServletResponse origResponse = null;
	  protected OutputStream realOutputStream = null;
	  protected ServletOutputStream stream = null;
//	  protected PrintWriter writer = null;
	 
	  Class<? extends OutputStream> outputStreamClass;
	 
	private boolean getWriterCalled = false;
	private boolean getOutputStreamCalled =false;

	private PrintWriter writer = null;
	private final StringWriter buffer = new StringWriter();
	private int errorCode = 0;
	private String errorMsg;
	
	public ErrorCatchHttpResponseWrapper(HttpServletResponse response) {
	    super(response);
	    origResponse = response;
	  }

	  public void writeBufferToStream(OutputStream os) {
		  
	  }
	  
	  @Override
	  public int getStatus() {
		  if(errorCode==0) {
			  return super.getStatus();
		  }
		  return errorCode;
	  }

	  public String getErrorMessage() {
		  return errorMsg;
	  }
	  public ServletOutputStream getOutputStream() throws IOException {
		    if (getWriterCalled ) {
		      throw new IllegalStateException("getWriter already called");
		    }

		    getOutputStreamCalled  = true;
		    return super.getOutputStream();
		  }

		  public PrintWriter getWriter() throws IOException {
		    if (writer != null) {
		      return writer;
		    }
		    if (getOutputStreamCalled) {
		      throw new IllegalStateException("getOutputStream already called");
		    }
		    getWriterCalled = true;
		    writer = new PrintWriter(buffer);
		    return writer;
		  }

	  public String getBufferContent() {
		  return buffer.toString();
		  
	  }
	  public void finishResponse() {
	    try {
	      if (writer != null) {
	        writer.close();
	      } else {
	        if (stream != null) {
	          stream.close();
	        }
	      }
	    } catch (IOException e) {}
	  }
	 
	  @Override
	  public void flushBuffer() throws IOException {
	    stream.flush();
	  }
	 
	  @Override
	  public void setContentLength(int length) {}
	 
	  /**
	   * Gets the underlying instance of the output stream.
	   * @return
	   */
	  public OutputStream getRealOutputStream() {
	    return realOutputStream;
	  }

	@Override
	public void sendError(int sc, String msg) throws IOException {
		this.errorCode = sc;
		this.errorMsg = msg;
	}

	@Override
	public void sendError(int sc) throws IOException {
		this.errorCode = sc;
	}
	  
	  
}
