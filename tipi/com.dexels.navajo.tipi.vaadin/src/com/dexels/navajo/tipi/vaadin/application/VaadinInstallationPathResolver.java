package com.dexels.navajo.tipi.vaadin.application;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.application.InstallationPathResolver;

public class VaadinInstallationPathResolver {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(VaadinInstallationPathResolver.class);
	
	public static List<String> getInstallationPath(ServletContext context) throws TipiException {
		String force = context.getInitParameter("forcedTipiPath");
		if(force!=null) {
			return InstallationPathResolver.parseContext(force);
		} else {
			try {
				String fullContext = context.getContextPath();
				return  InstallationPathResolver.getInstallationFromPath(fullContext);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
		}
		return null;
	}


}
