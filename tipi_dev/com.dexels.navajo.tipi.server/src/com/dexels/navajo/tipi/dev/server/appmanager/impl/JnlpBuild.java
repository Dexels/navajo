package com.dexels.navajo.tipi.dev.server.appmanager.impl;

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

public class JnlpBuild implements AppStoreOperation {

	
	private final static Logger logger = LoggerFactory
			.getLogger(JnlpBuild.class);
	
	
	@Override
	public void build(ApplicationStatus a) throws IOException {
		List<Dependency> dependencyList = new ArrayList<Dependency>();
//		dependencyList.clear();
		List<String> extraHeaders = new ArrayList<String>();
		extraHeaders.add("Permissions: all-permissions");
		extraHeaders.add("Codebase: *");
		
		String deps = a.getSettingString("dependencies");
		File unsigned = new File(a.getAppFolder(), "unsigned");

		if(unsigned.exists()) {
			FileUtils.deleteDirectory(unsigned);
		}
		if (!unsigned.exists()) {
			unsigned.mkdirs();
		}
		String[] d = deps.split(",");
		for (String dependency : d) {
			
			logger.info("Dependency: " + dependency);
			Dependency dd = new Dependency(dependency);
			dependencyList.add(dd);
		}
		for (Dependency dd : dependencyList) {
			UnsignJarTask.downloadDepencency(dd, new File(unsigned.getAbsolutePath()),extraHeaders);
		}
		LocalJnlpBuilder jj = new LocalJnlpBuilder();
		jj.buildFromMaven(a.getSettingsBundle(),dependencyList,a.getAppFolder(),a.getProfiles(),"");
		signall(a);
	}

	private void signall(ApplicationStatus a) {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
//			<taskdef name="p200ant" classname="de.matthiasmann.p200ant.P200AntTask"/>

			
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			tasks.put("p200ant", de.matthiasmann.p200ant.P200AntTask.class);
			props.put("storepass", a.getSettingString("sign_storepass"));
			props.put("alias", a.getSettingString("sign_alias"));
			props.put("keystore", new File(a.getAppFolder(),a.getSettingString("sign_keystore")).getAbsolutePath());
//			props.put("storepass", "sp0rtl1nk");
//			props.put("alias", "server");
//			props.put("keystore", new File(appFolder,"keystore/keystore.ks").getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(ApplicationStatusImpl.class.getClassLoader().getResourceAsStream("ant/localsign.xml"), a.getAppFolder(), props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}



}
