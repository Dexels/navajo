package com.dexels.navajo.server.logback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.dexels.navajo.server.api.NavajoServerContext;


public class LogbackConfigurator {

	
//	private final static Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	private NavajoServerContext navajoServerContext;

	
	public void setNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = navajoServerContext;
	}
	
	public void clearNavajoContext(NavajoServerContext navajoServerContext) {
		this.navajoServerContext = null;
	}

	
	
	public void activate() {
		try {
			String path = navajoServerContext.getInstallationPath();
//			Map<String,String> props = navajoServerContext.getDispatcher().getNavajoConfig().getProperties();
//			System.err.println("props: "+props);
			File install = new File(path);
			File joranFile = new File(install,"config/logback.xml");
			if(!joranFile.exists()) {
				return; //
			}
//			PropertyDefiner propertyDefiner = new NavajoInstallationPropertyDefiner(path);

			FileInputStream joranFis = null;
			try {
				 joranFis = new FileInputStream(joranFile);
				 loadLogbackConfig(joranFis,path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if(joranFis!=null) {
					try {
						joranFis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	private void loadLogbackConfig(InputStream is, String path) {
		LoggerContext lc =(LoggerContext)LoggerFactory.getILoggerFactory();
	      lc.reset(); 

		StatusPrinter.print(lc);
//		MDC.put("rootPath", path);
	    try {
		  lc.putProperty("rootPath", path);
		  JoranConfigurator configurator = new JoranConfigurator();
	      configurator.setContext(lc);
	      
	      // the context was probably already configured by default configuration 
	      // rules
	      configurator.doConfigure(is);
	      StatusPrinter.print(lc);
	      
	    } catch (JoranException je) {
	       je.printStackTrace();
	    }

	}
	
	public void deactivate() {
		System.err.println("deactivating");
	}
	
//	public void comm() {
//	    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//	    FileAppender fa = new FileAppender();
//	    RollingFileAppender rfAppender = new RollingFileAppender();
//	    rfAppender.setContext(loggerContext);
//	    rfAppender.setFile("testFile.log");
//	    FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
//	    rollingPolicy.setContext(loggerContext);
//	    // rolling policies need to know their parent
//	    // it's one of the rare cases, where a sub-component knows about its parent
//	    rollingPolicy.setParent(rfAppender);
//	    rollingPolicy.setFileNamePattern("testFile.%i.log.zip");
//	    rollingPolicy.start();
//
//	    SizeBasedTriggeringPolicy triggeringPolicy = new ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy();
//	    triggeringPolicy.setMaxFileSize("5MB");
//	    triggeringPolicy.start();
//
//	    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//	    encoder.setContext(loggerContext);
//	    encoder.setPattern("%-4relative [%thread] %-5level %logger{35} - %msg%n");
//	    encoder.start();
//
//	    rfAppender.setEncoder(encoder);
//	    rfAppender.setRollingPolicy(rollingPolicy);
//	    rfAppender.setTriggeringPolicy(triggeringPolicy);
//
//	    rfAppender.start();
//
//	    Logger logbackLogger = loggerContext.getLogger("Main");
//	    logbackLogger.addAppender(rfAppender);
//
//	    StatusPrinter.print(loggerContext);
//
//	    logbackLogger.debug("hello");
//	}

}
