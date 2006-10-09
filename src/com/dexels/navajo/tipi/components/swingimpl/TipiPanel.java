package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.document.*;

import java.awt.print.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiSwingDataComponentImpl implements Pageable, Printable {


  protected TipiSwingPanel myPanel = null;

  public Object createContainer() {
    myPanel = new TipiSwingPanel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myPanel;
  }

  protected ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void loadData(final Navajo n, final TipiContext tc, final String method, final String server) throws TipiException {
      myNavajo = n;
      myMethod = method;  
     runASyncInEventThread(new Runnable(){
        public void run() {
            try {
                doLoadData(n,tc,method,server);
            } catch (TipiException e) {
                e.printStackTrace();
            } catch (TipiBreakException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }});
  }
  // Hack. dont know how to do this directly
  private void doLoadData(Navajo n, TipiContext tc, String method,String server)  throws TipiException, TipiBreakException {
      super.loadData(n, tc, method,server);
  }
      
  public void setComponentValue(String name, final Object value) {
    if ("enabled".equals(name)) {
      getSwingContainer().setEnabled(value.equals("true"));
    }
    if (name.equals("image")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingPanel) getContainer()).setImage(getIcon( (URL) value));
        }
      });
      return;
    }
    if (name.equals("image_alignment")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingPanel) getContainer()).setImageAlignment((String)value);
        }
      });
      return;
    }

    super.setComponentValue(name, value);
  }

}
