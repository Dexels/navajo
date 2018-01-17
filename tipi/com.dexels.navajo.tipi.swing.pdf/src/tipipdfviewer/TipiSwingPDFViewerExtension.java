package tipipdfviewer;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

public class TipiSwingPDFViewerExtension extends TipiAbstractXMLExtension implements TipiExtension {

    private static final long serialVersionUID = 5536599543809742009L;

    @Override
    public void initialize(TipiContext tc) {
        // Do nothing
        System.out.println("hier");
        
    }

    @Override
    public void start(BundleContext context) throws Exception {
        registerTipiExtension(context);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        deregisterTipiExtension(context);
    }

}
