package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
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
public abstract class TipiSwingComponentImpl
    extends TipiComponentImpl implements TipiSwingComponent {
  protected TipiGradientPaint myPaint;
  protected TipiPopupMenu myPopupMenu = null;
  private boolean committedInUI;
  protected SwingTipiContext mySwingTipiContext;

  public void showPopup(MouseEvent e) {
  ( (JPopupMenu) myPopupMenu.getSwingContainer()).show(getSwingContainer(), e.getX(), e.getY());
  }


  public void setWaitCursor(boolean b) {
    Container cc =  getSwingContainer();
    if (cc!=null) {
      (cc).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
    }
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = getTipiComponent(i);
      if (TipiSwingComponent.class.isInstance(current)) {
        ((TipiSwingComponent)current).setWaitCursor(b);

      }
    }
  }

  public void setPaint(Paint p){
    this.myPaint = (TipiGradientPaint)p;
  }

  public TipiGradientPaint getPaint(){
    return myPaint;
  }


  public void setCursor(int cursorid) {
    if (getContainer() != null) {
      ( (Container) getContainer()).setCursor(Cursor.getPredefinedCursor(cursorid));
    }
  }
  public void setCursor(Cursor c) {
    if (getSwingContainer()!=null) {
      getSwingContainer().setCursor(c);
    }
  }

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

  public Container getSwingContainer() {
    return (Container) getContainer();
  }

  public Object getContainerLayout() {
    return getSwingContainer().getLayout();
  }


  public void setContainerLayout(Object layout) {
    ( (Container) getContainer()).setLayout( (LayoutManager) layout);
  }

  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().add( (Component) c, constraints);
      }
    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
      }
    });
  }



  public void print() {
    if (getSwingContainer()!=null) {
      PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob((Frame)myContext.getTopLevel(),"aap",null);
      Graphics g = pj.getGraphics();
      getSwingContainer().print(g);
    }
  }
  
  public void commitToUi() {
      super.commitToUi();
      committedInUI = true;
  }

  	public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this,te,executableParent,getSwingContainer(),exe);
	}
	
	protected void loadValues(final XMLElement values, final TipiEvent event) throws TipiException {
		runSyncInEventThread(new Runnable(){

			public void run() {
				try {
					TipiSwingComponentImpl.super.loadValues(values, event);
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}});
	}
  	
	public void loadStartValues(final XMLElement element) {
		runSyncInEventThread(new Runnable(){

			public void run() {
					TipiSwingComponentImpl.super.loadStartValues(element);
		
			}});
	}
	
	
	protected void doCallSetter(final Object component, final String propertyName, final Object param) {
		runSyncInEventThread(new Runnable(){
			public void run() {
				TipiSwingComponentImpl.super.doCallSetter(component,propertyName,param);
			}});
	}
	
	
}
