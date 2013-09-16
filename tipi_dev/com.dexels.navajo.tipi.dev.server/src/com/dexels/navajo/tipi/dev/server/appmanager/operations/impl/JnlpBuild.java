package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.File;
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
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.UnsignJarTask;

public class JnlpBuild extends BaseOperation implements AppStoreOperation {

	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpBuild.class);
	
	public void build(String name) throws IOException {
		ApplicationStatus as = applications.get(name);
		if(as==null) {
			for (ApplicationStatus a: applications.values()) {
				build(a);
			}
			
		} else {
			build(as);
		}
	}
	
	public void build() throws IOException {
		for (ApplicationStatus a: applications.values()) {
			build(a);
		}
	}	
	
	@Override
	public void build(ApplicationStatus a) throws IOException {
		List<String> extraHeaders = new ArrayList<String>();
		extraHeaders.add("Permissions: all-permissions");
		extraHeaders.add("Codebase: *");
		
		File unsigned = new File(a.getAppFolder(), "unsigned");

		if(unsigned.exists()) {
			FileUtils.deleteDirectory(unsigned);
		}
		if (!unsigned.exists()) {
			unsigned.mkdirs();
		}
		
		File repo = new File(applicationManager.getStoreFolder(), "repo");
		File lib = new File(a.getAppFolder(),"lib");
		if(lib.exists()) {
			FileUtils.deleteDirectory(lib);
		}
		if(!lib.exists()) {
			lib.mkdirs();
		}
		
		for (Dependency dd : a.getDependencies()) {
			File localSigned = dd.getFilePathForDependency(repo);
			if(!localSigned.exists()) {
				UnsignJarTask.downloadDepencency(dd,repo, new File(unsigned.getAbsolutePath()),extraHeaders);
				signdependency(dd, a.getSettingString("sign_alias"),  a.getSettingString("sign_storepass"),  new File(a.getAppFolder(),a.getSettingString("sign_keystore")), repo);
			}
			FileUtils.copyFileToDirectory(localSigned, lib );
		}
		LocalJnlpBuilder jj = new LocalJnlpBuilder();
		jj.buildFromMaven(a.getSettingsBundle(),a.getDependencies(),a.getAppFolder(),a.getProfiles(),"");
//		signall(a);
	}
//
//	private void signall(ApplicationStatus a) {
//		Map<String,String> props = new HashMap<String, String>();
//		try {
//			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
//			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
//			tasks.put("p200ant", de.matthiasmann.p200ant.P200AntTask.class);
//			props.put("storepass", a.getSettingString("sign_storepass"));
//			props.put("alias", a.getSettingString("sign_alias"));
//			props.put("keystore", new File(a.getAppFolder(),a.getSettingString("sign_keystore")).getAbsolutePath());
//			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
//			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
//			AntRun.callAnt(JnlpBuild.class.getClassLoader().getResourceAsStream("ant/localsign.xml"), a.getAppFolder(), props,tasks,null,los);
//		} catch (IOException e) {
//			logger.error("Error: ", e);
//		}
//	}
	
	private void signdependency(Dependency d, String alias, String storepass, File keystore, File repo) {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			props.put("storepass",storepass);
			props.put("alias", alias);
			props.put("keystore",keystore.getAbsolutePath());
			File cachedFile = d.getFilePathForDependency(repo);
			props.put("jarfile", cachedFile.getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(JnlpBuild.class.getClassLoader().getResourceAsStream("ant/signsingle.xml"), repo, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}



}
