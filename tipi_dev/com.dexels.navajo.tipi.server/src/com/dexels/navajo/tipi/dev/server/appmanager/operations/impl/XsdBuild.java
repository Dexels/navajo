package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.ant.AntRun;
import com.dexels.navajo.tipi.dev.ant.LoggingOutputStream;
import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.core.projectbuilder.LocalJnlpBuilder;
import com.dexels.navajo.tipi.dev.core.projectbuilder.XsdBuilder;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatusImpl;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.UnsignJarTask;

public class XsdBuild extends BaseOperation implements AppStoreOperation {

	
	private final static Logger logger = LoggerFactory
			.getLogger(XsdBuild.class);
	
	public void xsd(String name) throws IOException {
		ApplicationStatus as = applications.get(name);
		build(as);
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
