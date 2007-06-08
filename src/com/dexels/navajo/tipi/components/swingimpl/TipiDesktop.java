package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import java.net.*;

import javax.swing.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import tipi.*;

public class TipiDesktop
    extends TipiSwingDataComponentImpl {
  public Object createContainer() {
    TipiSwingDesktop jp = new TipiSwingDesktop(this);
    jp.setDesktopManager(new DefaultDesktopManager());
    jp.setDragMode(TipiSwingDesktop.LIVE_DRAG_MODE);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return jp;
  }

  public void addToContainer(final Object c, final Object constraints) {
    final TipiDesktop td = this;
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().add( (Component) c, constraints);
        JInternalFrame tw = (JInternalFrame) c;
        tw.toFront();
        getSwingContainer().repaint();
      }
    });
  }

  public void removeFromContainer(final Object c) {
    if (c==null) {
      return;
    }
    runSyncInEventThread(new Runnable() {
      public void run() {
    	  if (c instanceof JInternalFrame) {
			((JInternalFrame)c).dispose();
    	  }
    	  getSwingContainer().remove( (Component) c);
        getSwingContainer().repaint();
      }
    });
  }

  public void setComponentValue(final String name, final Object value) {
    if ("logo".equals(name)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          if (value instanceof URL) {
              ( (TipiSwingDesktop) getContainer()).setImage(new ImageIcon(( (URL) value)).getImage());
          } else {
              if (value instanceof Binary) {
                byte[] data = ((Binary)value).getData();
                ImageIcon ii = new ImageIcon(data);
           
                ( (TipiSwingDesktop) getContainer()).setImage(ii.getImage());
                getSwingContainer().repaint();
                
            } else {
            	if (value==null) {
					System.err.println("Null-type resource ignored");
				} else {
	                System.err.println("Ignoring strange resource: "+value.getClass());
				}
            }
          }
        }
      });
      
    }
    if ("alignment".equals(name)) {
        ( (TipiSwingDesktop) getContainer()).setAlignment((String)value);
    }
    
    super.setComponentValue(name, value);
  }

  protected void addedToParent() {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (TipiSwingDesktop) getContainer()).revalidate();
      }
    });
//    ( (TipiSwingDesktop) getContainer()).paintImmediately(0, 0, 100, 100);
    super.addedToParent();
  }
}
