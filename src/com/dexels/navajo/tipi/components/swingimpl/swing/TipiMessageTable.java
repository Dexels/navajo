/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingclient.components.*;
import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class TipiMessageTable extends MessageTable implements TipiDndCapable {

    final TipiContext myContext;
	private final TipiDndManager myDndManager;
    
    public TipiMessageTable(TipiContext tc, TipiComponent component) {
        myContext = tc;
    	myDndManager = new TipiDndManager(this,component);
    }
    
    public synchronized void setMessage(Message m) {
       setSavePathJustChanged(true);
       if (columnPathString!=null) {
            loadColumnsNavajo();
        } 
       super.setMessage(m);
    }


    public void loadColumnsNavajo() {
        if(columnPathString==null) {
            // ignoring, but should not happen at all, I think
            return;
        }
        Navajo n;
        try {
            n = myContext.getStorageManager().getStorageDocument(columnPathString);
        } catch (TipiException e) {
            e.printStackTrace();
            return;
        }
        if (n!=null) {
            loadColumnsNavajo(n);
            
        } else {
        	createDefaultColumnsFromModel();
        }
    }



    public void saveColumnsNavajo() throws IOException, NavajoException {
        Navajo n = super.saveColumnDefNavajo();
        if (n!=null) {
            try {
                myContext.getStorageManager().setStorageDocument(columnPathString, n);
            } catch (TipiException e) {
                e.printStackTrace();
                throw new IOException("Errrorrrr saving columns. columnPath: "+columnPathString);
            }
        }
    }

	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
