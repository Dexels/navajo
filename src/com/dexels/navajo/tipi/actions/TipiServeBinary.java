package com.dexels.navajo.tipi.actions;

import java.io.*;
import java.net.*;

import javax.servlet.http.Cookie;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.*;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.components.echoimpl.EchoTipiContext;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiServeBinary extends TipiAction {
    public TipiServeBinary() {
    }

    public void execute(TipiEvent e) {
        try {
        	 EchoTipiContext ee = (EchoTipiContext)myContext;
             File baseDir = ee.getDynamicResourceBaseDir();
            Operand binary = getEvaluatedParameter("binary", e);
            if(binary==null) {
            	binary = getEvaluatedParameter("value", e);
            }
            URL baseUrl = null;
            
            Operand baseUrlOperand = getEvaluatedParameter("baseUrl", e);
            if(baseUrlOperand!=null && baseUrlOperand.value!=null &&!"".equals(baseUrlOperand.value)) {
                baseUrl = new URL((String)baseUrlOperand.value);
            }

            Binary b = null;
            if (binary.value instanceof URL) {
            	URL u = (URL)binary.value;
            	InputStream is = u.openStream();
            	b = new Binary(is,false);
            	is.close();
			} else {
				if(binary.value instanceof Binary) {
					b = (Binary)binary.value;
				} else {
					System.err.println("Binary class: "+binary.value.getClass());
					System.err.println(">>>>>>>>>>>>>>>>\n"+binary.value);
					b = new Binary(new StringReader((String)binary.value));
				}
			}
            if(b==null) {
            	System.err.println("No binary found!");
            	myContext.showInfo("can not open binary property!", "info");
            	return;
            }
            String extension = b.getExtension();
            String random = new String(""+Math.random()).substring(2,7);
            File xx = new File(baseDir,"binary"+random+"."+extension);
            System.err.println("CREATING FILE: "+xx.getAbsolutePath());
            System.err.println("BINARY SIZE: "+b.getLength());
            FileOutputStream fos = new FileOutputStream(xx);
            b.write(fos);
            fos.flush();
            fos.close();
            URL result = null;
            if(baseUrl!=null) {
                result = new URL(baseUrl, xx.getName());
            } else {
                result = ee.getDynamicResourceBaseUrl(xx.getName());
            }
            
            System.err.println("Resulting url: "+result);
//            URL result = new URL(baseUrl.toString()+"/binary"+random+"."+extension);
            Command brc = new BrowserOpenWindowCommand(result.toString(),"reports"+random,"_blank");
            ApplicationInstance.getActive().enqueueCommand(brc);

        } catch (MalformedURLException e1) {
              e1.printStackTrace();
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }


}
