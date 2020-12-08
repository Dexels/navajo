/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
