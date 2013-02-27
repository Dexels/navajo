package com.dexels.navajo.article.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.impl.BaseRuntimeImpl;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TestRuntimeImpl extends BaseRuntimeImpl {

	private final StringWriter writer = new StringWriter();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestRuntimeImpl.class);
	
	public TestRuntimeImpl(String articleName, XMLElement article) {
		super(articleName,article);
	}
	
	public TestRuntimeImpl(String articleName, File articleFile) throws IOException {
		super(articleName,articleFile);
	}

	@Override
	public String resolveArgument(String name) {
		return null;
	}

	@Override
	public void setMimeType(String mime) {
		logger.info("Setting mime to: "+mime);
	}
	
	@Override
	public String getPassword() {
		return "some_password";
	}
	@Override
	public String getUsername() {
		return "some_username";
	}

	@Override
	public Writer getOutputWriter() throws IOException {
		return writer;
	}
	
	public String getOutput() {
		return writer.toString();
	}

}
