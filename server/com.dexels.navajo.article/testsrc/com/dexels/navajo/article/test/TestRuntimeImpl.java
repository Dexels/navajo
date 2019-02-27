package com.dexels.navajo.article.test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.impl.BaseRuntimeImpl;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.script.api.Access;

public class TestRuntimeImpl extends BaseRuntimeImpl {

	private final StringWriter writer = new StringWriter();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestRuntimeImpl.class);
	
	public TestRuntimeImpl(String articleName, XMLElement article, String instance) {
		super(articleName,article,null,instance);
	}
	
	public TestRuntimeImpl(String articleName, File articleFile, String instance) throws IOException {
		super(articleName,articleFile,instance,null);
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

	@Override
	public void commit() throws IOException {
		//
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return Collections.emptyMap();
	}


	@Override
	public void setUsername(String username) {
		
	}

    @Override
    public void setAccess(Access a) {
        
    }

    @Override
    public Access getAccess() {
        return null;
    }

}
