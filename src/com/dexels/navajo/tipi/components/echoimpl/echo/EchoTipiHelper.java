package com.dexels.navajo.tipi.components.echoimpl.echo;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.TipiComponent;
import nextapp.echo.*;
import nextapp.echo.event.*;
import com.dexels.navajo.tipi.*;
import echopoint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class EchoTipiHelper implements TipiHelper {
  public EchoTipiHelper() {
  }
  private TipiComponent myComponent = null;
  public void initHelper(TipiComponent tc) {
    myComponent = tc;
  }

  public void setComponentValue(String name, Object object) {
    if (!Component.class.isInstance(myComponent.getContainer())) {
      if (name.equals("visible")) {
        System.err.println("Sorry, should not assign a EchoHelper to a non-Echo component");
       ( (Container) myComponent.getContainer()).setVisible( ( (Boolean) object).booleanValue());
      }
      return;
    }
    Component c = (Component) myComponent.getContainer();
    if (name.equals("background")) {
      c.setBackground( (Color) object);
    }
    if (name.equals("foreground")) {
      c.setForeground( (Color) object);
    }
    if (name.equals("font")) {
      c.setFont( (Font) object);
    }

    if (name.equals("tooltip")) {
      if (ToolTipSupport.class.isInstance(c)) {
        ((ToolTipSupport)c).setToolTipText( (String) object);
      }
    }
//    if (name.equals("border")) {
//      c.setBorder( (Border) object);
//    }
    if (name.equals("visible")) {
      c.setVisible( ( (Boolean) object).booleanValue());
    }
    if (name.equals("enabled")) {
      c.setEnabled( ( (Boolean) object).booleanValue());
    }
    // SHOULD CALL myCOmponent.setComponentValue, I guess.
  }

  public Object getComponentValue(String name) {
    if (!Component.class.isInstance(myComponent.getContainer())) {
      System.err.println("Sorry, should not assign a EchoHelper to a non-Echo component");
      return null;
    }
    Component c = (Component) myComponent.getContainer();
    if (name.equals("tooltip")) {
      if (ToolTipSupport.class.isInstance(c)) {
        return ((ToolTipSupport)c).getToolTipText( );
      }
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
//    if (name.equals("border")) {
//      return c.getBorder();
//    }
    if (name.equals("enabled")) {
      return new Boolean(c.isEnabled());
    }
    return null;
  }

  public void deregisterEvent(TipiEvent e) {
    System.err.println("BEWARE..EVENT IS STILL CONNECTED TO THE COMPONENT!!");
  }

  public void registerEvent(final TipiEvent te) {
    Component c = (Component) myComponent.getContainer();
    if (c == null) {
      System.err.println("Cannot register swing event: Container is null!");
      return;
    }
    if (te.isTrigger("onActionPerformed", null)) {
//      System.err.println("\nAttempting to REGISTER: onActionPerformed!!\n\n\n\n");
//      MenuItem m;
      Button b;
      try {
        java.lang.reflect.Method m = c.getClass().getMethod("addActionListener", new Class[] {ActionListener.class});
        ActionListener bert = new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            try {
              myComponent.performTipiEvent("onActionPerformed", null, true);
            }
            catch (Throwable ex) {
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

//    void windowClosed(WindowEvent windowEvent);
//     void windowClosing(WindowEvent windowEvent);
//     void windowOpened(WindowEvent windowEvent);

    if (te.isTrigger("onWindowClosed", null)) {
      if (Window.class.isInstance(c)) {
        Window jj = (Window) c;
        jj.addWindowListener(new WindowAdapter() {
          public void onWindowClosing(WindowEvent e) {
            try {
              myComponent.performTipiEvent("onWindowClosed", null, true);
            }
            catch (TipiException ex) {
              ex.printStackTrace();
            }
          }
        });
      }
//      else if (JFrame.class.isInstance(c)) {
//        JFrame jj = (JFrame) c;
//        jj.addWindowListener(new WindowAdapter() {
//          public void windowClosed(WindowEvent e) {
//            try {
//              myComponent.performTipiEvent("onWindowClosed", e, te.isSync());
//            }
//            catch (TipiException ex) {
//              ex.printStackTrace();
//            }
//          }
//        });
//      }
//      else {
//        throw new RuntimeException("Can not fire onWindowClosed event from class: " + c.getClass());
//      }
    }

//    if (te.isTrigger("onMouseEntered", null)) {
//      c.addMouseListener(new MouseAdapter() {
//        public void mouseEntered(MouseEvent e) {
//          try {
//            myComponent.performTipiEvent("onMouseEntered", e, te.isSync());
//          }
//          catch (TipiException ex) {
//            ex.printStackTrace();
//          }
//        }
//      });
//    }
//    if (te.isTrigger("onMouseExited", null)) {
//      c.addMouseListener(new MouseAdapter() {
//        public void mouseExited(MouseEvent e) {
//          try {
//            myComponent.performTipiEvent("onMouseExited", e, te.isSync());
//          }
//          catch (TipiException ex) {
//            ex.printStackTrace();
//          }
//        }
//      });
//    }
  }



}
