package com.dexels.navajo.tipi.swing.pdf;

import javax.swing.JPanel;

import org.icepdf.ri.common.SwingController;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiPDFViewer extends TipiSwingDataComponentImpl {
    private static final long serialVersionUID = -4533671175260716894L;

    private JPanel viewerComponentPanel;

//    private Binary pdfBinary;

    private SwingController controller;

    public Object createContainer() {
        initUI();
        return viewerComponentPanel;
    }

    private void initUI() {
        // build a component controller
        controller = new SwingController();

        // Configuration

        NavajoSwingViewBuilder factory = new NavajoSwingViewBuilder(controller);
        viewerComponentPanel = factory.buildViewerPanel();
    }
    
    private void setPdfBinary(Binary pdfBinary) {
//        this.pdfBinary = pdfBinary;
        controller.openDocument(pdfBinary.getTempFileName(false));
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
        if (name.equals("pdf")) {
            runSyncInEventThread(new Runnable() {

                @Override
                public void run() {
                    setPdfBinary((Binary) object);
                }
            });
        }
    }
}
