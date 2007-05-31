/*
 * Created on May 18, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.io.*;

import org.w3c.dom.*;

import echopointng.image.*;

import nextapp.echo2.app.util.*;
import nextapp.echo2.webcontainer.*;
import nextapp.echo2.webrender.*;
import nextapp.echo2.webrender.output.*;

public class NavajoServerDelayMessage extends ServerDelayMessage {

    private static int BUFFER_SIZE = 4096;
//    inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));

    private static String IMAGE_RESOURCE_NAME = "com/dexels/navajo/tipi/components/echoimpl/ShadowOverlay.png";
    
    private static Service IMAGE_SERVICE = new ImageService();
    static {
        WebRenderServlet.getServiceRegistry().add(IMAGE_SERVICE);
    }
    
    private static class ImageService implements Service {

        private static final String SERVICE_ID = Uid.generateUidString();
        
        /**
         * @see nextapp.echo2.webrender.Service#getId()
         */
        public String getId() {
            return SERVICE_ID;
        }

        /**
         * @see nextapp.echo2.webrender.Service#getVersion()
         */
        public int getVersion() {
            return 0;
        }

        /**
         * @see nextapp.echo2.webrender.Service#service(nextapp.echo2.webrender.Connection)
         */
        public void service(Connection conn) throws IOException {
            conn.setContentType(ContentType.IMAGE_PNG);
            OutputStream out = conn.getOutputStream();

            InputStream in = null;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            
            try {
                in = getClass().getClassLoader().getResource(IMAGE_RESOURCE_NAME).openStream();
                if (in == null) {
                    throw new IllegalArgumentException("Specified resource does not exist: " + IMAGE_RESOURCE_NAME + ".");
                }
                do {
                    bytesRead = in.read(buffer);
                    if (bytesRead > 0) {
                        out.write(buffer, 0, bytesRead);
                    }
                } while (bytesRead > 0);
            } finally {
                if (in != null) { try { in.close(); } catch (IOException ex) { } } 
            }
        }
    }
    
    private Element messageElement;
    
    public NavajoServerDelayMessage(ContainerContext containerContext, String messageText) {
        XmlDocument xmlDocument = new XmlDocument("div", null, null, null);
        Document document = xmlDocument.getDocument();
        Element divElement = document.getDocumentElement();
        divElement.setAttribute("id", ELEMENT_ID_MESSAGE);
        divElement.setAttribute("style", "position:absolute;top:0px;left:0px;width:100%;height:100%;cursor:wait;"
                + "margin:0px;padding:0px;visibility:hidden;z-index:10000;");

        Element tableElement = document.createElement("table");
        tableElement.setAttribute("style", "width:100%;height:100%;border:0px;padding:0px;");
        divElement.appendChild(tableElement);
        
        Element tbodyElement = document.createElement("tbody");
        tableElement.appendChild(tbodyElement);
        
        Element trElement = document.createElement("tr");
        tbodyElement.appendChild(trElement);
        
        Element tdElement = document.createElement("td");
        tdElement.setAttribute("style", "width:100%;height:100%;");
        tdElement.setAttribute("valign", "middle");
        tdElement.setAttribute("align", "center");
        trElement.appendChild(tdElement);
        
        Element longDivElement = document.createElement("div");
        longDivElement.setAttribute("id", ELEMENT_ID_LONG_MESSAGE);
        String longDivStyleText = "color:#4f4f4f;width:277px;padding-top:120px;height:156px;"
                + "font-family:verdana,arial,helvetica,sans-serif;font-size:14pt;font-weight:bold;font-style:italic;"
                + "text-align:center;";
//        if (containerContext.getClientProperties().getBoolean(ClientProperties.PROPRIETARY_IE_PNG_ALPHA_FILTER_REQUIRED)) {
//            // Use Internet Explorer PNG filter hack to achieve transparency.
//            longDivStyleText += "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" 
//                    + containerContext.getServiceUri(IMAGE_SERVICE) + "', sizingMethod='scale')";
//        } else {
//   s         longDivStyleText += "background-image:url(" + containerContext.getServiceUri(IMAGE_SERVICE) + ");";
//        }
        longDivElement.setAttribute("style", longDivStyleText);

        longDivElement.appendChild(document.createTextNode(messageText));
        tdElement.appendChild(longDivElement);
        
        messageElement = divElement;
    }
    
    /**
     * @see nextapp.echo2.webrender.ServerDelayMessage#getMessage()
     */
    public Element getMessage() {
        return messageElement;
    }

}
