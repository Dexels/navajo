package com.dexels.navajo.server.embedded;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

public class EmbeddedLogbackAppender extends OutputStreamAppender<ILoggingEvent> {

	private Appendable output;
	private PipedOutputStream baos = new PipedOutputStream();
	private Reader r;

	public EmbeddedLogbackAppender(Appendable a) {
		super();
		createStreams();
		this.output = a;
	}

	private void createStreams() {
		baos = new PipedOutputStream();
		try {
			PipedInputStream pis = new PipedInputStream(baos);
			r = new InputStreamReader(pis);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void writeOut(ILoggingEvent event) throws IOException {
		try {
			try {
				super.writeOut(event);
			} catch (Throwable e) {
				System.err.println("Trouble logging, rebuilding streams");
				e.printStackTrace();
				createStreams();
				setOutputStream(baos);

			}
//			bos.flush();
			
			 char[] buf = new char[1024];
		        int numRead=0;
		        while(r.ready() ){
		        	numRead=r.read(buf);
		            String readData = String.valueOf(buf, 0, numRead);
		            output.append(readData);
		            
		        }
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}


	  @Override
	  public void start() {
		  BufferedOutputStream bomp = new BufferedOutputStream(baos,20000);
			setOutputStream(bomp);
		  super.start();
	  }
}
