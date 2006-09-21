package com.dexels.navajo.tipi.actions;

import java.io.*;
import java.net.*;

import nextapp.echo2.app.*;
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

    public static void main(String[] args) {
    }
    public void execute(TipiEvent e) {
        try {
              File baseDir = ((EchoTipiContext)myContext).getDynamicResourceBaseDir();
            Operand binary = getEvaluatedParameter("binary", e);
            Operand baseUrlOperand = getEvaluatedParameter("baseUrl", e);
            URL baseUrl = new URL((String)baseUrlOperand.value);
            System.err.println(">>>>>>>>>> BASEURL::"+baseUrl);
            Binary b = null;
            if (binary.value instanceof URL) {
            	URL u = (URL)binary.value;
            	InputStream is = u.openStream();
            	b = new Binary(is,false);
            	is.close();
			} else {
				b = (Binary)binary.value;
			}
            //            System.err.println("baseUrl: "+baseUrl);
//            System.err.println("baseDir: "+baseDir);
            if (b==null) {
				System.err.println("Can not evaluate binary!");
			}
            String extension = b.getExtension();
            String random = new String(""+Math.random()).substring(2,7);
            File xx = new File(baseDir,"binary"+random+"."+extension);
            FileOutputStream fos = new FileOutputStream(xx);
            b.write(fos);
            fos.flush();
            fos.close();
            URL result = new URL(baseUrl.toString()+"/binary"+random+"."+extension);
            System.err.println("ResultURL: "+result.toString());
            Command brc = new BrowserOpenWindowCommand(result.toString(),"reports"+random,null);
            ApplicationInstance.getActive().enqueueCommand(brc);
//            ((EchoTipiContext)myContext).getServerContext().enqueueCommand(brc);
        } catch (MalformedURLException e1) {
              e1.printStackTrace();
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

}
