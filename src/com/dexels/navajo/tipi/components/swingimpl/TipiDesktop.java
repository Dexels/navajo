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

//	public void setVisible(final boolean flag) {
//		Runnable doIt = new Runnable(){
//
//			public void run() {
//				Animator animator = new Animator(500);
//			    System.err.println("Animating: TipiSwingWindow");
//			    ScreenTransition transition = new ScreenTransition(TipiSwingWindow.this,new TransitionTarget(){
//
//					public void setupNextScreen() {
//						TipiSwingWindow.super.setVisible(flag);
//					}}, animator);
//			    animator.setAcceleration(.5f);  // Accelerate for first 20%
//			       animator.setDeceleration(.5f);  // Decelerate for last 40%
//			    transition.start();			
//			    }};
//	//	SwingUtilities.invokeAndWait(doIt);
//			    doIt.run();
//	
//	
//}
  
  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
    	  
    	  System.err.println("Adding to desktop: "+c);
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
    	  System.err.println("Removing from desktop: "+c);
    	  if (c instanceof JInternalFrame) {
			((JInternalFrame)c).dispose();
    	  }
    	  getSwingContainer().remove( (Component) c);
        getSwingContainer().repaint();
      }
    });
  }

//  public void setComponentValue(final String name, final Object value) {
//    if ("logo".equals(name)) {
//      runSyncInEventThread(new Runnable() {
//        public void run() {
//          if (value instanceof URL) {
//              ( (TipiSwingDesktop) getContainer()).setImage(new ImageIcon(( (URL) value)).getImage());
//          } else {
//              if (value instanceof Binary) {
//                byte[] data = ((Binary)value).getData();
//                ImageIcon ii = new ImageIcon(data);
//           
//                ( (TipiSwingDesktop) getContainer()).setImage(ii.getImage());
//                getSwingContainer().repaint();
//                
//            } else {
//            	if (value==null) {
//					System.err.println("Null-type resource ignored");
//				} else {
//	                System.err.println("Ignoring strange resource: "+value.getClass());
//				}
//            }
//          }
//        }
//      });
//      
//    }
//    if ("alignment".equals(name)) {
//        ( (TipiSwingDesktop) getContainer()).setAlignment((String)value);
//    }
//    
//    super.setComponentValue(name, value);
//  }

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
