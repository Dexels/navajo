package com.dexels.navajo.tipi.dev.ant.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.ant.AntRun;
import com.dexels.navajo.tipi.dev.ant.LoggingOutputStream;
import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.core.sign.SignComponent;

public class SignComponentImpl implements SignComponent {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(SignComponentImpl.class);
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.ant.impl.SignComponent#signJnlp(java.io.File, java.lang.String, java.lang.String, java.lang.String, java.io.File, java.io.File)
	 */
	@Override
	public void signJnlp(File jnlpFile,String profile, String alias, String storepass, File keystore, File baseDir) {
		Map<String,String> props = new HashMap<String, String>();
		try {
			Map<String,Class<?>> tasks = new HashMap<String,Class<?>>();
			tasks.put("signjar", org.apache.tools.ant.taskdefs.SignJar.class);
			props.put("storepass",storepass);
			props.put("alias", alias);
			props.put("profile", profile);
			props.put("keystore",keystore.getAbsolutePath());
			props.put("jnlpFile", jnlpFile.getAbsolutePath());
			Logger antlogger = LoggerFactory.getLogger("tipi.appstore.ant");
			PrintStream los = new PrintStream( new LoggingOutputStream(antlogger));
			AntRun.callAnt(getClass().getClassLoader().getResourceAsStream("ant/signjnlp.xml"), baseDir, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.ant.impl.SignComponent#signdependency(com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency, java.lang.String, java.lang.String, java.io.File, java.io.File)
	 */
	@Override
	public void signdependency(Dependency d, String alias, String storepass, File keystore, File repo) {
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
			AntRun.callAnt(getClass().getClassLoader().getResourceAsStream("ant/signsingle.xml"), repo, props,tasks,null,los);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
}
