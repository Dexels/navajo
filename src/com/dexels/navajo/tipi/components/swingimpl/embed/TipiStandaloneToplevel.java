package com.dexels.navajo.tipi.components.swingimpl.embed;

import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;
import com.dexels.navajo.tipi.internal.TipiLayout;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiStandaloneToplevel extends TipiPanel
    implements RootPaneContainer {

  private final JPanel myPanel = new JPanel();
  private final BorderLayout myLayout = new BorderLayout();

  public TipiStandaloneToplevel() {
    myPanel.setLayout(myLayout);
    super.setName("init");
    setId("init");
    initContainer();
  }

  public void addToContainer(Object c, Object constraints) {
    System.err.println("Adding to toplevel: "+c.getClass()+ " -- "+c.hashCode());
//    myPanel.removeAll();
    ((Component)c).setVisible(true);
    myPanel.add((Component)c,BorderLayout.CENTER);
  }

  public void setLayout(TipiLayout tl) {
    // no way jose
  }

  public Object getContainerLayout() {
    return myLayout;
  }

  public void setContainerLayout(Object o) {
    // no way jose
  }

  public Object createContainer() {
    return myPanel;
  }

  public Component getGlassPane() {
    return null;
  }

  public void setGlassPane(Component glassPane) {
  }

  public Container getContentPane() {
    return myPanel;
  }

  public void setContentPane(Container contentPane) {
  }

  public JLayeredPane getLayeredPane() {
    return null;
  }

  public void setLayeredPane(JLayeredPane layeredPane) {
  }

  public JRootPane getRootPane() {
    return null;
  }
}
