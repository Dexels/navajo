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

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiDesktop
    extends TipiSwingDataComponentImpl {
  public Object createContainer() {
    TipiSwingDesktop jp = new TipiSwingDesktop();
    SwingTipiContext c = (SwingTipiContext)myContext;
	// register as default desktop, to create modal dialogs as modal internalframes
    c.setDefaultDesktop(jp);
   
    jp.setDesktopManager(new DefaultDesktopManager());
    jp.setDragMode(TipiSwingDesktop.LIVE_DRAG_MODE);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return jp;
  }


	public int getHeight() {
		return  getSwingContainer().getHeight();
	}

	public int getWidth() {
		return  getSwingContainer().getWidth();
	}

  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
    	  
    	 getSwingContainer().add( (Component) c, constraints);
			if(c instanceof JInternalFrame) {
				JInternalFrame tw = (JInternalFrame) c;
		        tw.toFront();
			} else {
				((Component)c).setBounds(10,10,100,100);
			}
			
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
//    	  System.err.println("Removing from desktop: "+c);
    	  if (c instanceof JInternalFrame) {
			((JInternalFrame)c).dispose();
    	  }
    	  getSwingContainer().remove( (Component) c);
        getSwingContainer().repaint();
      }
    });
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
