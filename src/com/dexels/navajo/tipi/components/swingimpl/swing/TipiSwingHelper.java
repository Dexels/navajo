package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import java.util.*;
import com.dexels.navajo.tipi.components.swingimpl.parsers.*;
import com.dexels.navajo.tipi.components.swingimpl.*;

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
  public void initHelper(TipiComponent tc) {
    myComponent = (TipiSwingComponent)tc;
  }

  public void setComponentValue(String name, Object object) {
    if (!JComponent.class.isInstance(myComponent.getContainer())) {
      if (name.equals("visible")) {
        ( (Container) myComponent.getContainer()).setVisible( ( (Boolean) object).booleanValue());
      }
      return;
    }
    JComponent c = (JComponent) myComponent.getContainer();
    if (name.equals("background")) {
      if(TipiGradientPaint.class.isInstance(object)){
        TipiGradientPaint tgp = (TipiGradientPaint)object;
        myComponent.setPaint(tgp);
      }else{
        c.setBackground( (Color) object);
      }
    }
    if (name.equals("foreground")) {
      c.setForeground( (Color) object);
    }
    if (name.equals("font")) {
      c.setFont( (Font) object);
    }
    if (name.equals("tooltip")) {
      c.setToolTipText( (String) object);
    }
    if (name.equals("border")) {
   
      c.setBorder( (Border) object);
    }
    if (name.equals("visible")) {
      c.setVisible( ( (Boolean) object).booleanValue());
    }
    if (name.equals("enabled")) {
    	if (object instanceof String) {
    	      c.setEnabled( object.equals("true"));
		} else {
		      c.setEnabled( ( (Boolean) object).booleanValue());
		}
    }
    // SHOULD CALL myCOmponent.setComponentValue, I guess.
  }

  public Object getComponentValue(String name) {
    if (!JComponent.class.isInstance(myComponent.getContainer())) {
      System.err.println("Sorry, only use JComponent decendants. No awt stuff. Ignoring. Class: "+myComponent.getContainer().getClass());
      return null;
    }
    JComponent c = (JComponent) myComponent.getContainer();
    if (name.equals("tooltip")) {
      return c.getToolTipText();
    }
    if (name.equals("visible")) {
      return new Boolean( ( (Component) myComponent.getContainer()).isVisible());
    }
    if (name.equals("background")) {
      return c.getBackground();
    }
    if (name.equals("foreground")) {
      return c.getForeground();
    }
    if (name.equals("font")) {
      return c.getFont();
    }
    if (name.equals("border")) {
      return c.getBorder();
    }
    if (name.equals("enabled")) {
      return new Boolean(c.isEnabled());
    }
    return null;
  }

  public void deregisterEvent(TipiEvent e) {
  }

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
        JInternalFrame jj = (JInternalFrame) c;
        jj.addInternalFrameListener(new InternalFrameAdapter() {
          public void internalFrameClosing(InternalFrameEvent e) {
            try {
              myComponent.performTipiEvent("onWindowClosed", null, te.isSync());
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
        });
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
          JApplet jj = (JApplet) c;
          // do nothing.
          
      }
      else {
        throw new RuntimeException("Can not fire onWindowClosed event from class: " + c.getClass());
      }
    }
    if (te.isTrigger("onMouseEntered", null)) {
      c.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
          Map m = new HashMap();
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
            Map m = new HashMap();
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
            Map m = new HashMap();
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
            Map m = new HashMap();
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
            Map m = new HashMap();
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
