package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingHelper
    implements TipiHelper {
  private TipiSwingComponent myComponent = null;

  private java.util.List<EventListener> myListeners = new ArrayList<EventListener>();
  public void initHelper(TipiComponent tc) {
    myComponent = (TipiSwingComponent)tc;
  }

  public void setComponentValue(String name, Object object) {
  }

  public Object getComponentValue(String name) {
    return null;
  }

  public void deregisterEvent(TipiEvent e) {
	    Component c = (Component) myComponent.getContainer();
	    if (c == null) {
	      System.err.println("Cannot deregister swing event: Container is null! Component class: "+myComponent.getClass());
	      return;
	    }
	    try {
		    for (EventListener el : myListeners) {
		    	if(el instanceof ActionListener) {
				      java.lang.reflect.Method m = c.getClass().getMethod("removeActionListener", new Class[] {ActionListener.class});
					  m.invoke(c, new Object[] {el});
		    	}
		    	if(el instanceof InternalFrameListener) {
				      java.lang.reflect.Method m = c.getClass().getMethod("removeInternalFrameListener", new Class[] {InternalFrameListener.class});
					  m.invoke(c, new Object[] {el});
		    	}		    
		    	if(el instanceof WindowListener) {
				      java.lang.reflect.Method m = c.getClass().getMethod("removeWindowListener", new Class[] {WindowListener.class});
					  m.invoke(c, new Object[] {el});
		    	}		    
		    	
		    	if(el instanceof MouseListener) {
				      java.lang.reflect.Method m = c.getClass().getMethod("removeMouseListener", new Class[] {MouseListener.class});
					  m.invoke(c, new Object[] {el});
		    	}		    
		    	
		    	
		    }
		} catch (Exception e1) {
			e1.printStackTrace();
		} 

	 }
 

  /**
   * Refactor this silly bugger. This is called from TipiComponent. Should be able to fix it using regular inheritance
   * 
   */
  public void registerEvent(final TipiEvent te) {
    Component c = (Component) myComponent.getContainer();
    if (c == null) {
      System.err.println("Cannot register swing event: Container is null!");
      return;
    }
    if (te.isTrigger("onActionPerformed", null)) {
      try {
        java.lang.reflect.Method m = c.getClass().getMethod("addActionListener", new Class[] {ActionListener.class});
        ActionListener bert = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              myComponent.performTipiEvent("onActionPerformed", null, te.isSync());
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
        };
        m.invoke(c, new Object[] {bert});
      }
      catch (Exception exe) {
        exe.printStackTrace();
      }
    }
    if (te.isTrigger("onWindowClosed", null)) {
      if (JInternalFrame.class.isInstance(c)) {
//        JInternalFrame jj = (JInternalFrame) c;
//        jj.addInternalFrameListener(new InternalFrameAdapter() {
//          public void internalFrameClosing(InternalFrameEvent e) {
//            try {
//              myComponent.performTipiEvent("onWindowClosed", null, te.isSync());
//            }
//            catch (TipiException ex) {
//              ex.printStackTrace();
//            }
//          }
//        });
      }
      else if (JFrame.class.isInstance(c)) {
        JFrame jj = (JFrame) c;
        jj.addWindowListener(new WindowAdapter() {
          public void windowClosed(WindowEvent e) {
            try {
              myComponent.performTipiEvent("onWindowClosed",null, te.isSync());
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
        });
      } else if (JApplet.class.isInstance(c)) {
          // do nothing.
          
      }
      else {
        throw new RuntimeException("Can not fire onWindowClosed event from class: " + c.getClass());
      }
    }
    if (te.isTrigger("onMouseEntered", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
          Map<String,Object> m = new HashMap<String,Object>();
          m.put("x",new Integer(e.getX()));
          m.put("y",new Integer(e.getY()));
          m.put("button",new Integer(e.getButton()));
          m.put("clickCount",new Integer(e.getClickCount()));
          try {
            myComponent.performTipiEvent("onMouseEntered", m, te.isSync());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.isTrigger("onMouseExited", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseExited(MouseEvent e) {
          try {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("x",new Integer(e.getX()));
            m.put("y",new Integer(e.getY()));
            myComponent.performTipiEvent("onMouseExited", m, te.isSync());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.isTrigger("onMouseClicked", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          try {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("x",new Integer(e.getX()));
            m.put("y",new Integer(e.getY()));
            m.put("button",new Integer(e.getButton()));
            m.put("clickCount",new Integer(e.getClickCount()));
            myComponent.performTipiEvent("onMouseClicked", m, te.isSync());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.isTrigger("onMousePressed", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
          try {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("x",new Integer(e.getX()));
            m.put("y",new Integer(e.getY()));
            m.put("button",new Integer(e.getButton()));
            myComponent.performTipiEvent("onMousePressed", m, te.isSync());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
    if (te.isTrigger("onMouseReleased", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent e) {
          try {
            Map<String,Object> m = new HashMap<String,Object>();
            m.put("x",new Integer(e.getX()));
            m.put("y",new Integer(e.getY()));
            m.put("button",new Integer(e.getButton()));
            myComponent.performTipiEvent("onMouseReleased", m, te.isSync());
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      });
    }
  }
}
