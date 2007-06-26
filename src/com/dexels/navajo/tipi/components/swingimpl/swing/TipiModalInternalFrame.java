package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.beans.*;

public class TipiModalInternalFrame extends JInternalFrame {

  private JPanel glass;
private final JRootPane myRootPane;

public TipiModalInternalFrame(String title, JRootPane 
      rootPane, Component desktop, Component contentComponent, Dimension size) {
    super(title);
    myRootPane = rootPane;
    glass = new JPanel();
	glass.setOpaque(false);

    // Attach mouse listeners
    MouseInputAdapter adapter = 
      new MouseInputAdapter(){};
    glass.addMouseListener(adapter);
    glass.addMouseMotionListener(adapter);

    // Add in option pane
    getContentPane().add(contentComponent, BorderLayout.CENTER);

    
    // Define close behavior
//    PropertyChangeListener pcl = 
//        new PropertyChangeListener() {
//      public void propertyChange(PropertyChangeEvent 
//          event) {
//        if (isVisible() && 
//          (event.getPropertyName().equals(
//            JOptionPane.VALUE_PROPERTY) ||
//           event.getPropertyName().equals(
//            JOptionPane.INPUT_VALUE_PROPERTY))) {
//          try {
//            setClosed(true);
//          } catch (PropertyVetoException ignored) {
//          }
//          TipiModalInternalFrame.this.setVisible(false);
//          glass.setVisible(false);
//        }
//      }
//    };
//    pane.addPropertyChangeListener(pcl);

    // Change frame border
    putClientProperty("JInternalFrame.frameType",
      "optionDialog");

    // Size frame
//    Dimension size = getPreferredSize();
    Dimension rootSize = desktop.getSize();

    setBounds((rootSize.width - size.width) / 2,
              (rootSize.height - size.height) / 2,
               size.width, size.height); 
    desktop.validate(); 
    try {
      setSelected(true);
    } catch (PropertyVetoException ignored) {
    }

    // Add modal internal frame to glass pane
    glass.add(this);

    // Change glass pane to our panel
    rootPane.setGlassPane(glass);

    // Show glass pane, then modal dialog
    glass.setVisible(true);
  }

  public void setVisible(boolean value) {
	  System.err.println("SETTING VISIBLE: "+value);
    super.setVisible(value);
    if (value) {
      startModal();
    } else {
      stopModal();
      if(glass!=null) {
          glass.setVisible(false);
          glass = new JPanel();
      }
    }
  }

  private synchronized void startModal() {
//    try {
//      if (SwingUtilities.isEventDispatchThread()) {
//        EventQueue theQueue = 
//          getToolkit().getSystemEventQueue();
//        while (isVisible()) {
//          AWTEvent event = theQueue.getNextEvent();
//          Object source = event.getSource();
//          System.err.println("Source: "+source);
//          System.err.println("Event: "+event.toString());
//          if (event instanceof ActiveEvent) {
//            ((ActiveEvent)event).dispatch();
//          } else if (source instanceof Component) {
//            ((Component)source).dispatchEvent(
//              event);
//          } else if (source instanceof MenuComponent) {
//            ((MenuComponent)source).dispatchEvent(
//              event);
//          } else {
//            System.err.println(
//              "Unable to dispatch: " + event);
//          }
//        }
//      } else {
//        while (isVisible()) {
//          wait();
//        }
//      }
//    } catch (InterruptedException ignored) {
//    }
//    System.err.println("Exiting startmodal:");
  }

  private synchronized void stopModal() {
    notifyAll();
  }

  
  public static void main(String args[]) {
    final JFrame frame = new JFrame(
      "Modal Internal Frame");
    frame.setDefaultCloseOperation(
      JFrame.EXIT_ON_CLOSE);

    final JDesktopPane desktop = new JDesktopPane();

    ActionListener showModal = 
        new ActionListener() {
      Integer ZERO = new Integer(0);
      Integer ONE = new Integer(1);
      public void actionPerformed(ActionEvent e) {

        // Manually construct an input popup
        JOptionPane optionPane = new JOptionPane(
          "Print?", JOptionPane.QUESTION_MESSAGE, 
          JOptionPane.YES_NO_OPTION);

        // Construct a message internal frame popup
        JInternalFrame modal = 
          new TipiModalInternalFrame("Really Modal", 
          frame.getRootPane(), desktop, optionPane, new Dimension(200,200));

        modal.setVisible(true);

        Object value = optionPane.getValue();
        if (value.equals(ZERO)) {
          System.out.println("Selected Yes");
        } else if (value.equals(ONE)) {
          System.out.println("Selected No");
        } else {
          System.err.println("Input Error"); 
       }
      }
    };

    JInternalFrame internal = 
      new JInternalFrame("Opener");
    desktop.add(internal);

    JButton button = new JButton("Open");
    button.addActionListener(showModal);

    Container iContent = internal.getContentPane();
    iContent.add(button, BorderLayout.CENTER);
    internal.setBounds(25, 25, 200, 100);
    internal.setVisible(true);

    Container content = frame.getContentPane();
    content.add(desktop, BorderLayout.CENTER);
    frame.setSize(500, 300);
    frame.setVisible(true);
  }

public static void showInternalMessage(JRootPane rootPane, TipiSwingDesktop defaultDesktop, String title, String text, int poolSize) {
	// TODO Auto-generated method stub
	JOptionPane.showInternalMessageDialog(defaultDesktop,text,title,JOptionPane.INFORMATION_MESSAGE);
}
  
//public static void showInternalFrame(JRootPane root, JDesktopPane pane, JComponent contents, String title, String text, int type) {
//JOptionPane optionPane = new JOptionPane();
////JInternalFrame modal = optionPane.createInternalFrame();
//}

//public static void showInternalMessage(JRootPane root, JDesktopPane pane, String title, String text, int type) {
//  JOptionPane optionPane = new JOptionPane();
//  optionPane.setMessage(text);
//  optionPane.setMessageType(
//    type);
//  JInternalFrame modal = optionPane.
//    createInternalFrame(pane, title);
//
//  showInternalFrame(root, modal);
//
//  System.out.println("Returns immediately");  
//}

//private static void showInternalFrame(JRootPane root, JInternalFrame modal) {
//// create opaque glass pane
//  JPanel glass = new JPanel();
//  glass.setOpaque(false);
//
//  // Attach modal behavior to frame
//  modal.addInternalFrameListener(
//    new ModalAdapter(glass));
//
//  // Add modal internal frame to glass pane
//  glass.add(modal);
//
//  // Change glass pane to our panel
//  root.setGlassPane(glass);
//
//  // Show glass pane, then modal dialog
//  glass.setVisible(true);
//  modal.setVisible(true);
//}
}