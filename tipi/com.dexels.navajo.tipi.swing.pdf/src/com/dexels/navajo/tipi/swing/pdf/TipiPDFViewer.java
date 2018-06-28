package com.dexels.navajo.tipi.swing.pdf;

import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;

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

        // Configuration

        NavajoSwingViewBuilder factory = new NavajoSwingViewBuilder(controller);
        viewerComponentPanel = factory.buildViewerPanel();

        controller.openDocument("/home/chris/Documents/220401884.pdf");
        
    }


    @Override
    protected Object getComponentValue(String name) {
        if (name != null) {
          


            return super.getComponentValue(name);
        } else {
            return null;
        }
        

    }

    @Override
    protected void setComponentValue(String name, final Object object) {
        super.setComponentValue(name, object);

    }
}
