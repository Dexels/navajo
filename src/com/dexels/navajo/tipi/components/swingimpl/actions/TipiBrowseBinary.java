/*
 * Created on Jun 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipiBrowseBinary extends TipiAction {

    /*
     * (non-Javadoc)
     *
     * @see com.dexels.navajo.tipi.internal.TipiAction#execute(com.dexels.navajo.tipi.internal.TipiEvent)
     */
	private File f = null;
	private int result = 0;
	
	protected void execute(TipiEvent event) throws TipiBreakException, TipiException {
        Operand value  = getEvaluatedParameter("property", event);
         if (value==null) {
            throw new TipiException("TipiBrowseBinary: no value supplied");
        }
        if (value.value==null) {
            throw new TipiException("TipiBrowseBinary: null value supplied");
        }
        if (!(value.value instanceof Property)) {
            throw new TipiException("TipiOpenBinary: Type of value is not Property, but: "+value.value.getClass());
        }
        final Property pp = (Property)value.value;
        if(!pp.getType().equals(Property.BINARY_PROPERTY)) {
            throw new TipiException("TipiOpenBinary: Property is not type binary , but: "+pp.getType());
        }
        myComponent.runSyncInEventThread(new Runnable(){

			public void run() {
		        JFileChooser jf = new JFileChooser(System.getProperty("user.home"));
		        if(pp.getSubType("description")!=null) {
		        	File file = new File(pp.getSubType("description"));
					jf.setSelectedFile(file);
		        }
		        result = jf.showOpenDialog((Component) myContext.getTopLevel());
		        f = jf.getSelectedFile();
			}});
        
        if(result!=JFileChooser.APPROVE_OPTION) {
        	throw new TipiBreakException(TipiBreakException.USER_BREAK);
        }
        
        try {
			Binary b = new Binary(f);
			pp.setAnyValue(b);
			String currentSubtype = pp.getSubType();
			System.err.println("Setting type to: "+f.getPath());
			if(currentSubtype!=null && !"".equals(currentSubtype)) {
				// beware, maybe already present?
				pp.setSubType(currentSubtype+","+"description="+f.getName());
			} else {
				pp.setSubType("description="+f.getName());
			}
			System.err.println("Sub: "+pp.getSubType());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) throws Exception {
        File f = File.createTempFile("tipi_", ""+".pdf");
//        URL u = f.toURL();
        DefaultBrowser.displayURL(f.getAbsolutePath());

    }
}
