/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.vaadin.actions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.actions.base.TipiVaadinActionImpl;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.URIHandler;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiOpenBinary extends TipiVaadinActionImpl {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
    protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        final Binary b  = (Binary) getEvaluatedParameterValue("binary", event);
//			
			  URIHandler uriHandler = new URIHandler() {
			        public DownloadStream handleURI(URL context,
			                                        String relativeUri) {
			            // Do something here
			            System.out.println("handleURI=" + relativeUri);

			            // Should be null unless providing dynamic data.
			            return new DownloadStream(b.getDataAsStream(), b.guessContentType(), "download"+b.getExtension());
			        }
			    };
			    getVaadinApplication().getMainWindow().addURIHandler(uriHandler);
    }

//    private void showWindow(StreamResource resource) {
//    	Window window = new Window();
//        ((VerticalLayout) window.getContent()).setSizeFull();
//        window.setResizable(true);
//        window.setWidth("800");
//        window.setHeight("600");
//        window.center();
//        Embedded e = new Embedded();
//        e.setSizeFull();
//        e.setType(Embedded.TYPE_BROWSER);
//        resource.setMIMEType("text/html");
//        e.setSource(resource);
//        window.addComponent(e);
//        getVaadinApplication().getMainWindow().addWindow(window);
//    }
}
