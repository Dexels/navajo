package com.dexels.navajo.tipi.components.swingimpl;

import java.beans.*;
import java.net.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;
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
  private String myMenuBar = "";
  private String myTitle;
  
  private boolean iconifiable = false;
  private boolean closable = false;
  private boolean visible = false;
  private boolean maximizable = false;
  private boolean resizable = false;
  private String title = "";
  private Color backgroundColor = null;
  private Rectangle myBounds;

  private boolean isDisposing = false;
  
  private JInternalFrame constructWindow() {
	  	isDisposing = false;
	  	clearContainer();
	    myWindow = new JInternalFrame();
	    TipiHelper th = new TipiSwingHelper();
	    th.initHelper(this);
	    addHelper(th);
//  	  	System.err.println("INSTANTIATING WINDOW!");
//  	  	myWindow.addInternalFrameListener(new InternalFrameAdapter() {
//	      public void internalFrameClosing(InternalFrameEvent l) {
//	      }
//
//		public void internalFrameClosed(InternalFrameEvent e) {
//			System.err.println("Shobiedoewa! ");
//
//	    	  JInternalFrame jjj = (JInternalFrame) e.getSource();
//
//	    	  JInternalFrame jj = (JInternalFrame) getContainer();
//	    	      if (jjj!=null) {
//	    	    	  System.err.println("Source found. Disposing");
//	    	    	  Container parent = jjj.getParent();
//	    	    	  if (parent!=null) {
//	    	        	  parent.remove(jjj);
//	    	    	  }
//	    	    	  if (!isDisposing) {
//		    	    	  isDisposing = true;
//		    	    	  System.err.println("DISPOSING!");
//		    	    	  try {
//							jjj.setClosed(true);
//						} catch (PropertyVetoException e1) {
//								e1.printStackTrace();
//						}
//		    	    	  jjj.dispose();
//	    	    	  }
//	    	      }	 else {
//	    	    	  System.err.println("No source, but container found");
//	    	      }
//	    	      myWindow = null;
//	    	      clearContainer();
//			super.internalFrameClosed(e);
//		}
//	    });
	    myWindow.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);  
//	    if (myBounds!=null) {
//	    	myWindow.setBounds(myBounds);
//		}
//	    if (backgroundColor!=null) {
//			myWindow.setBackground(backgroundColor);
//		}
//	    myWindow.setIconifiable(iconifiable);
//	    myWindow.setClosable(closable);
//	    myWindow.setMaximizable(maximizable);
//	    myWindow.setResizable(resizable);
//	    if (getTipiParent()!=null) {
//		    getTipiParent().addToContainer(myWindow, null);
//		}
//	    clearContainer();
	    myWindow.setVisible(true);
	    return myWindow;
  }
  
  public Object createContainer() {
	  return constructWindow();
  }

  public Object getComponentValue(String name) {
    if ("visible".equals(name)) {
    	if (myWindow==null) {
			return new Boolean(false);
		}
      return new Boolean(myWindow.isVisible());
    }
//    if("title".equals(name)){
//      return myWindow.getTitle();
//    }
    Rectangle r = myWindow.getBounds();
    if (name.equals("x")) {
      return new Integer(r.x);
    }
    if (name.equals("y")) {
      return new Integer(r.y);
    }
    if (name.equals("w")) {
      return new Integer(r.width);
    }
    if (name.equals("h")) {
      return new Integer(r.height);
    }
    if (name.equals("iconifiable")) {
      return new Boolean(myWindow.isIconifiable());
    }
    if (name.equals("maximizable")) {
      return new Boolean(myWindow.isMaximizable());
    }
    if (name.equals("closable")) {
      return new Boolean(myWindow.isClosable());
    }
    if (name.equals("resizable")) {
      return new Boolean(myWindow.isResizable());
    }
    if (name.equals("title")) {
      return myWindow.getTitle();
     }

    return super.getComponentValue(name);
  }


//  private final void myWindow_internalFrameClosed(InternalFrameEvent l) {
//
//	  myWindow = null;
//	  clearContainer();
//  }

  public void addToContainer(final Object c, final Object constraints) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().add( (Component) c, constraints);
//        ((SwingTipiContext)myContext).addTopLevel(c);
      }
    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (JInternalFrame) getContainer()).getContentPane().remove( (Component) c);
//        ((SwingTipiContext)myContext).removeTopLevel(c);
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

  public final void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
    if (object==null) {
      System.err.println("Null object. Name = "+name);
    } else {
      //System.err.println("Class: "+object.getClass()+" name: "+name);
    }
//    final JInternalFrame jj = (JInternalFrame) getContainer();
 	  checkContainerInstance();
 	 
    runSyncInEventThread(new Runnable() {
 
	public void run() {
        if (name.equals("iconifiable")) {
      	  checkContainerInstance();
          boolean b = ( (Boolean) object).booleanValue();
          iconifiable = b;
          ((JInternalFrame) getContainer()).setIconifiable(b);
        }
        if (name.equals("background")) {
      	  checkContainerInstance();
        	backgroundColor = (Color) object;
        	((JInternalFrame) getContainer()).setBackground(backgroundColor );
        }
        if (name.equals("maximizable")) {
      	  checkContainerInstance();
          maximizable = ( (Boolean) object).booleanValue();
          ((JInternalFrame) getContainer()).setMaximizable(maximizable);
        }
        if (name.equals("closable")) {
      	  checkContainerInstance();
          closable = ( (Boolean) object).booleanValue();
          ((JInternalFrame) getContainer()).setClosable(closable);
        }
        if (name.equals("resizable")) {
      	  checkContainerInstance();
          resizable = ( (Boolean) object).booleanValue();
          ((JInternalFrame) getContainer()).setResizable(resizable);
        }
        if (name.equals("selected")) {
          boolean b = ( (Boolean) object).booleanValue();
          try {
        	  checkContainerInstance();
        	  ((JInternalFrame) getContainer()).setSelected(b);
          }
          catch (PropertyVetoException ex) {
            System.err.println("Tried to select a window, but someone did not agree");
            ex.printStackTrace();
          }
          // hihihiihi
          if (name.equals("visible")) {
        	  ((JInternalFrame) getContainer()).invalidate();
        	  checkContainerInstance();
        	  ((JInternalFrame) getContainer()).setVisible( ( (Boolean) object).booleanValue());
          }
        }
        final Rectangle r = getBounds();

      if (name.equals("x")) {
          r.x = ( (Integer) object).intValue();
        }
        if (name.equals("y")) {
          r.y = ( (Integer) object).intValue();
        }
        if (name.equals("w")) {
          r.width = ( (Integer) object).intValue();
        }
        if (name.equals("h")) {
          r.height = ( (Integer) object).intValue();
        }
        if (name.equals("title")) {
          myTitle = object.toString();
          setTitle(myTitle);
        }
        if (name.equals("icon")) {
            if (object instanceof URL) {
                setIcon(getIcon( (URL) object));
            }
        }
        myBounds = r;
        setBounds(r);
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
  
  private ImageIcon getIcon(final URL u) {
    return new ImageIcon(u);
  }

  protected void setTitle(final String s) {
    myWindow.setTitle(s);
  }

  protected void setBounds(final Rectangle r) {
//	  System.err.println("Setting bounds: "+r);
	  myWindow.setBounds(r);
  }

  protected Rectangle getBounds() {
    return myWindow.getBounds();
  }

  protected void setIcon(final ImageIcon ic) {
    myWindow.setFrameIcon(ic);
  }

  protected void setJMenuBar(JMenuBar ic) {
    myWindow.setJMenuBar(ic);
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
        System.err.println("Ignoring: Nothing to maximize");
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
      if (jj==null || tt==null) {
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
//	  System.err.println("Disposing tipi window:");
      JInternalFrame jj = (JInternalFrame) getContainer();
      if (jj!=null) {
//    	  System.err.println("Internal frame found. Disposing....");
       	  jj.dispose();
       	 Container parent = jj.getParent();
    	  if (parent!=null) {
        	  parent.remove(jj);
    	  }
      }
      
      clearContainer();
      myWindow = null;
	 super.disposeComponent();
}

public void reUse() {
//    if (myParent!=null) {
//      myParent.addToContainer();
//    }
//    myWindow.setVisible(true);
  }
}
