package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiSwingDataComponentImpl {

  public Object createContainer() {
	  TipiSwingPanel myPanel = new TipiSwingPanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myPanel;
  }


  public void loadData(final Navajo n, final String method) throws TipiException {
      myNavajo = n;
      myMethod = method;
      System.err.println("Load data. Thread: "+Thread.currentThread().getName());
     runSyncInEventThread(new Runnable(){
        public void run() {
            try {
                TipiPanel.super.loadData(n,method);
            } catch (TipiException e) {
                e.printStackTrace();
            } catch (TipiBreakException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            
        }});
  }
//  
//  public void setComponentValue(String name, final Object value) {
//    if ("enabled".equals(name)) {
//      getSwingContainer().setEnabled(value.equals("true"));
//    }
//    if (name.equals("image")) {
//      runSyncInEventThread(new Runnable() {
//        public void run() {
//          ( (TipiSwingPanel) getContainer()).setImage(getIcon( (URL) value));
//        }
//      });
//      return;
//    }
//    if (name.equals("image_alignment")) {
//      runSyncInEventThread(new Runnable() {
//        public void run() {
//          ( (TipiSwingPanel) getContainer()).setImageAlignment((String)value);
//        }
//      });
//      return;
//    }
//
//    super.setComponentValue(name, value);
//  }

}
