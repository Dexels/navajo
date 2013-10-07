package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.XsdBuilder;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class XsdBuild extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = -7219236999229829020L;
	private final static Logger logger = LoggerFactory
			.getLogger(XsdBuild.class);
	
	public void xsd(String name) throws IOException {
		ApplicationStatus as = applications.get(name);
		build(as);
	}
	
	public void xsd() throws IOException {
		for (ApplicationStatus a: applications.values()) {
			build(a);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String val = req.getParameter("app");
		if(val!=null) {
			xsd(val);
		} else {
			xsd();
		}
	}
	
	@Override
	public void build(ApplicationStatus a) throws IOException {
		XsdBuilder xsd = new XsdBuilder();
		File lib = new File(a.getAppFolder(),"lib");
		File[] jars = lib.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		if(jars==null || jars.length==0) {
			logger.warn("Can not write xsd: No jar files built.");
			return;
		}
		for (File file : jars) {
			xsd.addJar(file);
		}
		xsd.writeXsd(a.getAppFolder());
	}
}
