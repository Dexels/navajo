package com.dexels.navajo.tipi.swing.pdf;

import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiPDFViewer extends TipiSwingDataComponentImpl {
    private static final long serialVersionUID = -4533671175260716894L;

    private JPanel viewerComponentPanel;

    public Object createContainer() {
        initUI();
        return viewerComponentPanel;
    }

    private void initUI() {
        // build a component controller
        SwingController controller = new SwingController();

        SwingViewBuilder factory = new SwingViewBuilder(controller);

        viewerComponentPanel = factory.buildViewerPanel();
        


        controller.openDocument("/home/chris/git/navajo/tipi/com.dexels.navajo.tipi.swing.pdf/src/resources/navajo3.pdf");
        //
    }

    @Override
    protected Object getComponentValue(String name) {

        return super.getComponentValue(name);

    }

    @Override
    protected void setComponentValue(String name, final Object object) {
        super.setComponentValue(name, object);

    }
}
