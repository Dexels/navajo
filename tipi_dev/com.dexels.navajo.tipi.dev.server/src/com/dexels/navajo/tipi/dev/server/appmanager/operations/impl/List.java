package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

import org.apache.felix.service.command.CommandSession;

public class List extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	private final static Logger logger = LoggerFactory
			.getLogger(List.class);
	
	public void list(CommandSession session ) throws IOException {
		writeListToJsonArray(session.getConsole());
	}
	
	public void writeListToJsonArray(OutputStream os) throws IOException {  

		final ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(os,applications);

	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		writeListToJsonArray(resp.getOutputStream());
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {
		File lib = new File(a.getAppFolder(),"lib");
		if(lib.exists()) {
			FileUtils.deleteQuietly(lib);
		}
		File xsd = new File(a.getAppFolder(),"xsd");
		if(xsd.exists()) {
			FileUtils.deleteQuietly(xsd);
		}
		File digest = new File(a.getAppFolder(),"resource/remotedigest.properties");
		if(digest.exists()) {
			FileUtils.deleteQuietly(digest);
		}
		
		File[] jnlps = a.getAppFolder().listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jnlp");
			}
		});		
		for (File file : jnlps) {
			FileUtils.deleteQuietly(file);
		}
	}
}
