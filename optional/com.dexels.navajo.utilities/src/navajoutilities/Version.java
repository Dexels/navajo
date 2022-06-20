/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package navajoutilities;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.util.legacy.UtilitiesLibrary;


public class Version extends AbstractCoreExtension implements BundleActivator {

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		UtilitiesLibrary library = new UtilitiesLibrary();
		registerAll(library);

	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
		
	}
	
	@Override
	public void shutdown() {
		super.shutdown();
	}
	

	public static void main(String[] args) {
		
	}
}
