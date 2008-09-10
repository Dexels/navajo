package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.beans.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Need to refactor menus in internalframes. Now still uses the old mode Frank */
public final class TipiWindow
//    extends DefaultTipi {
    extends TipiSwingDataComponentImpl {
  private JInternalFrame myWindow;

  private JInternalFrame constructWindow() {
//	  	isDisposing = false;
	  	clearContainer();
	    myWindow = new TipiSwingWindow();
	    TipiHelper th = new TipiSwingHelper();
	    th.initHelper(this);
	    addHelper(th);
	    myWindow.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);  
	    myWindow.setResizable(true);
	    myWindow.setSize(100,40);
//	    JLabel label = new JLabel("Monkey");
//		myWindow.getLayeredPane().add(label, 40000);
//	    label.setBounds(10,10,50,20);
	    myWindow.addInternalFrameListener(new InternalFrameAdapter(){

			public void internalFrameClosed(InternalFrameEvent e) {
				if(myWindow!=null) {
					// will re-enter this event, so its a bit defensive
					JInternalFrame w = myWindow;
					myWindow = null;
					w.dispose();
					myContext.disposeTipiComponent(TipiWindow.this);
				}
			}

			public void internalFrameClosing(InternalFrameEvent e) {
				try {
					performTipiEvent("onWindowClosed", null, true);
					((JInternalFrame) e.getSource()).dispose();
				} catch (TipiException e1) {
					((JInternalFrame) e.getSource()).dispose();
					e1.printStackTrace();
				} catch (TipiBreakException e2) {
					System.err.println("Breakie breakie");
					if(e2.getType()==TipiBreakException.COMPONENT_DISPOSED) {
						// a component disposed event should still close the window
						((JInternalFrame) e.getSource()).dispose();
					}
				}
				
			}

			
		});

	    return myWindow;
  }
  
  public Object createContainer() {
	  return constructWindow();
  }
  
	public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this,te,executableParent,myWindow.getContentPane(),exe);
	}
 
  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {

    	  JInternalFrame internalFrame = ( (JInternalFrame) getContainer());
    	  
//    	  if (c instanceof LayeredPaneable && ((LayeredPaneable)c).wantsToBeInALayer() ) {
//    		  System.err.println("Adding to layerredpane");
//    		  internalFrame.getLayeredPane().add((Component)c, ((LayeredPaneable)c).getPreferredLayer());
//    		  ((Component)c).setBounds(10,200,600,30);
//    	  } else {
			internalFrame.getContentPane().add( (Component) c, constraints);
//		}
      }
    });
    SwingUtilities.invokeLater(new Runnable(){

		public void run() {
			 try {
				( (JInternalFrame) getContainer()).setSelected(true);
				( (JInternalFrame) getContainer()).requestFocus();
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}});
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().remove( (Component) c);
        ( (JInternalFrame) getContainer()).repaint();
 }
    });
  }

  public void setContainerLayout(final Object layout) {
    runSyncInEventThread(new Runnable() {
      public void run() {
    	  checkContainerInstance();
        ( (JInternalFrame) getContainer()).getContentPane().setLayout( (LayoutManager) layout);
      }
    });
  }
  
  public Container getSwingContainer() {
	  checkContainerInstance();
	  return super.getSwingContainer();
}
  private void checkContainerInstance() {
	  if (getContainer()==null) {
		setContainer(constructWindow());
	}
  }
  

  private final void doPerformMethod(String name, TipiComponentMethod compMeth) {
	  
		
    if (name.equals("iconify")) {
     	  checkContainerInstance();
     	 
    	try {
//        myWindow.setIcon(true);
        JInternalFrame jj = (JInternalFrame) getContainer();
        TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
        jj.setIcon(true);
        tt.getDesktopManager().iconifyFrame(jj);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (name.equals("maximize")) {
     	  checkContainerInstance();
     	 
      JInternalFrame jj = (JInternalFrame) getContainer();
      if (jj.isMaximum()) {
        return;
      }
      try {
        TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
        jj.setMaximum(true);
        tt.getDesktopManager().maximizeFrame(jj);
      }
      catch (Error ex1) {
        ex1.printStackTrace();
      }
      catch (Exception ex1) {
        ex1.printStackTrace();
      }
    }
    if (name.equals("restore")) {
     	  checkContainerInstance();
     	       JInternalFrame jj = (JInternalFrame) getContainer();
      if (!jj.isMaximum()) {
        System.err.println("Ignoring: Nothing to restore");
        return;
      }
      TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
      tt.getDesktopManager().minimizeFrame(jj);
      try {
        jj.setMaximum(false);
//        // This might give an exception.. don't worry.. can't help it.
      }
      catch (PropertyVetoException ex1) {
        ex1.printStackTrace();
      }
    }
    if (name.equals("toFront")) {
     	  checkContainerInstance();
     	 
     JInternalFrame jj = (JInternalFrame) getContainer();
      TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
      if ( tt==null) {
        return;
      }
      jj.toFront();
      if (tt.getDesktopManager()==null) {
        return;
      }
      tt.getDesktopManager().activateFrame(jj);
    }
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        doPerformMethod(name, compMeth);
      }
    });
  }

  public boolean isReusable() {
    return true;
  }

  
  
  public void disposeComponent() {
	  runSyncInEventThread(new Runnable(){

		public void run() {
			JInternalFrame jj = (JInternalFrame) getContainer();
		      if (jj!=null) {
		       	  jj.dispose();
		       	 Container parent = jj.getParent();
		    	  if (parent!=null) {
		        	  parent.remove(jj);
		    	  }
		      }
		      
		      clearContainer();
		      myWindow = null;
		}});
		 TipiWindow.super.disposeComponent();
			  
}

public void reUse() {
//    if (myParent!=null) {
//      myParent.addToContainer();
//    }
//    myWindow.setVisible(true);
  }
}
