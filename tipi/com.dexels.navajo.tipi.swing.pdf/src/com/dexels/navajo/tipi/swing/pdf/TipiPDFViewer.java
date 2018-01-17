package com.dexels.navajo.tipi.swing.pdf;

import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;



public class TipiPDFViewer  extends TipiSwingDataComponentImpl {
    private static final long serialVersionUID = -4533671175260716894L;
    private JPanel viewerComponentPanel;

    private PDFViewerPane pdfViwer = null;


    public Object createContainer() {
        initUI();
        return viewerComponentPanel;
    }
    
    private void initUI() {
            // build a component controller
               SwingController controller = new SwingController();

               SwingViewBuilder factory = new SwingViewBuilder(controller);

               viewerComponentPanel = factory.buildViewerPanel();
            // add interactive mouse link annotation support via callback
               controller.getDocumentViewController().setAnnotationCallback(
                       new org.icepdf.ri.common.MyAnnotationCallback(
                               controller.getDocumentViewController()));

              

               URL url = getClass().getResource("/resources/navajo3.pdf");
//               try {
                controller.openDocument("/home/chris/git/navajo/tipi/com.dexels.navajo.tipi.swing.pdf/src/resources/navajo3.pdf");
//            } catch (URISyntaxException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
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
