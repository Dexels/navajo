package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiSwingDataComponentImpl
    extends TipiDataComponentImpl
    implements TipiSwingComponent {
  protected TipiPopupMenu myPopupMenu = null;
  protected TipiGradientPaint myPaint;
//  private String myHeader = null;
//  private String myFooter = null;
//  private double scale = 0;
//  private int pageCount = -1;
  private int currentPage = -1;


  protected SwingTipiContext mySwingTipiContext;
  
//  private boolean committedInUI;


  
  public void initContainer() {
	  mySwingTipiContext = (SwingTipiContext)myContext;
	  if (getContainer() == null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          setContainer(createContainer());
        }
      });
    }
  }

  public void setWaitCursor(boolean b) {
    Container cc = getSwingContainer();
    if (cc != null) {
       (cc).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (TipiSwingComponent.class.isInstance(current)) {
         ( (TipiSwingComponent) current).setWaitCursor(b);
      }
    }
  }

  public void addToContainer(final Object c, final Object constraints) {
      boolean noPrefSizes = false;
	try {
		noPrefSizes = "true".equals(System
				.getProperty("com.dexels.navajo.swingclient.NoPreferredSizes"));
	} catch (SecurityException e) {
		// assume false;
	}      
//	if (noPrefSizes) {
//          if (getContainer() instanceof JComponent) {
//              JComponent cc = (JComponent)getContainer();
//              LayoutManager m = cc.getLayout();
//              if (m instanceof GridBagLayout) {
//                cc.setPreferredSize(new Dimension(0,0));
//            }
//          }
//      }

    try {
    	runSyncInEventThread(new Runnable(){

			public void run() {
				getSwingContainer().add( (Component) c, constraints);
			}});
		} catch (Throwable e) {
		throw new RuntimeException("Illegal constraint while adding object: "+c+" to component: "+
				getPath()+" with constraint: "+constraints);
	}
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
  }

  public void setContainerLayout(final Object layout) {
    runSyncInEventThread(new Runnable() {
      public void run() {
         ( (Container) getContainer()).setLayout( (LayoutManager) layout);
      }
    });
  }

  public void setCursor(Cursor c) {
    if (getSwingContainer() != null) {
      getSwingContainer().setCursor(c);
    }
  }

  protected Object getComponentValue(String name) {
    if (name != null) {
      if (name.equals("currentPage")) {
        return new Integer(currentPage);
      }
    }
    return super.getComponentValue(name);
  }

  public void replaceLayout(TipiLayout tl) {
    super.replaceLayout(tl);
    ( (Container) getContainer()).repaint(); if (JComponent.class.isInstance(getContainer())) {
       ( (JComponent) getContainer()).revalidate();
    }
  }

  public void showPopup(MouseEvent e) {
     ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
  }

  protected void doLayout() {
    if (getContainer() != null) {
      if (JComponent.class.isInstance(getContainer())) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            ( (JComponent) getContainer()).revalidate(); ( (JComponent) getContainer()).repaint(); getContext().debugLog("data    ", "Exiting doLayout in tipi: " + getId());
          }
        });
      }
    }
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public void refreshLayout() {
    List<TipiComponent> elementList = new ArrayList<TipiComponent>();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    for (int i = 0; i < elementList.size(); i++) {
      final TipiComponent current = elementList.get(i);
      if (current.isVisibleElement()) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            addToContainer(current.getContainer(), current.getConstraints());
          }
        });
      }
    }
  }

  public void runSyncInEventThread(Runnable r) {
    if (SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }
      catch (InvocationTargetException ex) {
        throw new RuntimeException(ex);
      }
      catch (InterruptedException ex) {
      }
    }
  }
  public void runAsyncInEventThread(Runnable r) {
	    if (SwingUtilities.isEventDispatchThread()) {
	      r.run();
	    }
	    else {
	        SwingUtilities.invokeLater(r);
	      }
	    }
  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
     super.performComponentMethod(name, compMeth, event);
  }

 
  
  public void setPaint(Paint p){
    this.myPaint = (TipiGradientPaint)p;
  }

  public TipiGradientPaint getPaint(){
    return myPaint;
  }

  public void commitToUi() {
      super.commitToUi();
   }

	public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this,te,executableParent,getSwingContainer(),exe);
	}

@Override
public void loadData(final Navajo n, final String method) throws TipiException, TipiBreakException {
  	if (getContainer()!=null && getContainer() instanceof JComponent) {
			//SwingUtilities.invokeAndWait 
			runSyncInEventThread(new Runnable(){

				public void run() {
					try {
						TipiSwingDataComponentImpl.super.loadData(n, method);
					} catch (TipiException e) {
						e.printStackTrace();
					} catch (TipiBreakException e) {
						e.printStackTrace();
					}

//				JComponent jc = (JComponent)getContainer();
//				Animator animator = new Animator(1500);
//			    System.err.println("Animating: "+getContainer().getClass());
//			    ScreenTransition transition = new ScreenTransition(jc,new TransitionTarget(){
//
//					public void setupNextScreen() {
//						try {
//							TipiSwingDataComponentImpl.super.loadData(n, method);
//						} catch (TipiException e) {
//							e.printStackTrace();
//						} catch (TipiBreakException e) {
//							e.printStackTrace();
//						}
//					}}, animator);
//			    animator.setAcceleration(.5f);  // Accelerate for first 20%
//			       animator.setDeceleration(.5f);  // Decelerate for last 40%
//			    transition.start();			
				    }});
	
	} else {
		super.loadData(n, method);
	}
	
	
}
protected void loadValues(final XMLElement values, final TipiEvent event) throws TipiException {
	runSyncInEventThread(new Runnable(){

		public void run() {
			try {
				TipiSwingDataComponentImpl.super.loadValues(values, event);
			} catch (TipiException e) {
				e.printStackTrace();
			}
		}});
}
public void loadStartValues(final XMLElement element) {
	runSyncInEventThread(new Runnable(){

		public void run() {
				TipiSwingDataComponentImpl.super.loadStartValues(element);

		}});
}
protected void doCallSetter(final Object component, final String propertyName, final Object param) {
	runSyncInEventThread(new Runnable(){
		public void run() {
			TipiSwingDataComponentImpl.super.doCallSetter(component,propertyName,param);
		}});
}

}
