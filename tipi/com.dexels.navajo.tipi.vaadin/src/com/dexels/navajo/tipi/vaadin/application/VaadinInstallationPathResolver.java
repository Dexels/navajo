package com.dexels.navajo.tipi.vaadin.application;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.application.InstallationPathResolver;

public class VaadinInstallationPathResolver {
	public static List<String> getInstallationPath(ServletContext context) throws TipiException {
		String force = context.getInitParameter("forcedTipiPath");
		if(force!=null) {
			return InstallationPathResolver.parseContext(force);
		} else {
			try {
				String fullContext = context.getContextPath();
				return  InstallationPathResolver.getInstallationFromPath(fullContext);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


}
