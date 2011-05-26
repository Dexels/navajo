package com.dexels.navajo.server.embedded;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;

public class EmbeddedLogbackAppender extends OutputStreamAppender<ILoggingEvent> {

	private Appendable output;
	private final PipedOutputStream baos = new PipedOutputStream();
	private Reader r;

	public EmbeddedLogbackAppender(Appendable a) {
		super();
		try {
			PipedInputStream pis = new PipedInputStream(baos);
			r = new InputStreamReader(pis);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.output = a;
	}

	@Override
	protected void writeOut(ILoggingEvent event) throws IOException {
		try {
			super.writeOut(event);
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
			setOutputStream(baos);
		  super.start();
	  }
}
