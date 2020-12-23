/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package navajocore;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.core.NavajoCoreAdapterLibrary;

import navajoextension.AbstractCoreExtension;

public class Version extends AbstractCoreExtension {

    private static BundleContext bundleContext;

    private static final Logger logger = LoggerFactory.getLogger(Version.class);

    @Override
    public void start(BundleContext bc) throws Exception {
        super.start(bc);
        setBundleContext(bc);
        logger.debug("Bundle context set in Navajo Version: {} hash: {}", osgiActive(), Version.class.hashCode());
        NavajoCoreAdapterLibrary library = new NavajoCoreAdapterLibrary();
        registerAll(library);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
        super.stop(arg0);
        setBundleContext(null);
    }

    public static void setBundleContext(BundleContext bundleContext) {
        Version.bundleContext = bundleContext;
    }

    public static BundleContext getDefaultBundleContext() {
        Bundle b = org.osgi.framework.FrameworkUtil.getBundle(Version.class);
        if (b != null) {
            return b.getBundleContext();
        }
        return bundleContext;
    }

}
