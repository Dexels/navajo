package com.dexels.navajo.server.embedded;

import java.io.IOException;

import org.eclipse.jetty.util.log.Logger;

public class EmbeddedLogger implements Logger {

	
	private Appendable output;

	public EmbeddedLogger(Appendable a) {
		this.output = a;
	}
	
	private void dump(String text, Throwable t) {
		System.err.println("EMBEDDED LOGGGGG: "+text);
		try {
			output.append(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void debug(Throwable t) {
		dump(null,t);
	}

	@Override
	public void debug(String text, Object... other) {
		System.err.println("DEBUG: ");
		dump(text,null);
	}

	@Override
	public void debug(String text, Throwable t) {
		dump(text,t);

	}

	@Override
	public Logger getLogger(String text) {
		return this;
	}

	@Override
	public String getName() {
		return "Navajo Embedded Logger";
	}

	@Override
	public void ignore(Throwable t) {

	}

	@Override
	public void info(Throwable t) {
		dump(null,t);

	}

	@Override
	public void info(String text, Object... arg1) {
		dump(text,null);

	}

	@Override
	public void info(String text, Throwable t) {
		dump(text,t);

	}

	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	@Override
	public void setDebugEnabled(boolean b) {

	}

	@Override
	public void warn(Throwable t) {
		dump(null,t);

	}

	@Override
	public void warn(String text, Object... arg1) {
		dump(text,null);
	}

	@Override
	public void warn(String text, Throwable t) {
		dump(text,t);
		
	}

}
