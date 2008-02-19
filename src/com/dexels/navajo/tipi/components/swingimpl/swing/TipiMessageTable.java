/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;

public class TipiMessageTable extends MessageTable {

    final TipiContext myContext;
    private Navajo columnSettings = null;
    public TipiMessageTable(TipiContext tc) {
        myContext = tc;
    }
    
    public synchronized void setMessage(Message m) {
      setSavePathJustChanged(true);
       if (columnPathString!=null) {
            loadColumnsNavajo();
        } 
       super.setMessage(m);
        

    }
//    public void createDefaultColumnsFromModel() {
    	// ignore
//    	System.err.println("Hoei!");
//    }
    public void createDefaultColumnsFromMessageModel() {
//    	createDefaultColumnsFromModel();
      }

    public void loadColumnsNavajo() {
        // TODO Auto-generated method stub
//        super.loadColumnsNavajo();
    	System.err.println("Changed: "+savePathJustChanged);
        if(columnPathString==null) {
            // ignoring, but should not happen at all, I think
            return;
        }
        if(savePathJustChanged) {
        	// flush if changed
        	columnSettings = null;
        }
        
        Navajo n = null;
        if(columnSettings==null) {
            try {
                n = myContext.getStorageManager().getStorageDocument(columnPathString);
            } catch (TipiException e) {
                e.printStackTrace();
                return;
            }
            columnSettings = n;
        	
        } else {
        	System.err.println("Got settings from cache");
        	n = columnSettings;
        }
        if (n!=null) {
 
            loadColumnsNavajo(n);
//            refreshColumnSizes();
            
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
        columnSettings = n;
//        super.saveColumnsNavajo();
    }
    
    

}
