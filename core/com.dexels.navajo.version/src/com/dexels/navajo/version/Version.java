/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.version;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Version extends AbstractVersion {
	private ServiceRegistration<MBeanServer> registration = null;

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
		String inject = System.getProperty("navajo.inject.mbeanserver");
		if(inject!=null) {
			registration = context.registerService(MBeanServer.class, mbeanServer,null);
		}

	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		if (registration != null) {
			registration.unregister();
		}
		context = null;

	}

}
