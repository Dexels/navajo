package com.dexels.navajo.tipi.components;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyPanel extends JPanel {
  private Component currentComponent = null;
  private int labelWidth = 0;
  private int valign = JLabel.CENTER;
  private int halign = JLabel.LEADING;
//  private int height
  private int propertyWidth = 0;

  private JLabel myLabel = null;
  BorderLayout borderLayout = new BorderLayout();

  public PropertyPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void doLayout() {
    super.doLayout();
  }

  public void setPropertyComponent(Component c) {
    if (currentComponent == c) {
      return;
    }
    if (currentComponent!= null) {
      remove(currentComponent);
    }
    currentComponent = c;
    add(currentComponent,BorderLayout.CENTER);
  }

  public void setLabel(String s) {
    if (myLabel==null) {
      myLabel= new JLabel(s);
//      myLabel.setPreferredSize(new Dimension(100,20));
      add(myLabel,BorderLayout.WEST);
    } else {
      myLabel.setText(s);
    }
    if (labelWidth!=0) {
      setLabelIndent(labelWidth);
    }
    myLabel.setHorizontalAlignment(halign);
    myLabel.setVerticalAlignment(valign);
  }

  public void hideLabel() {
    remove(myLabel);
    myLabel= null;
  }

  public void setVerticalLabelAlignment(int alignment) {
    valign = alignment;
    if (myLabel!=null) {
      myLabel.setVerticalAlignment(alignment);
    }
  }

  public void setHorizontalLabelAlignment(int alignment) {
    halign = alignment;
    if (myLabel!=null) {
      myLabel.setHorizontalAlignment(alignment);
    }
  }

  public void setSize(int x, int y) {
    myLabel.setPreferredSize(new Dimension(x,y));
  }

  public void setLabelIndent(int lindent) {
    labelWidth = lindent;
    if (myLabel==null) {
      return;
    }
    int height = myLabel.getPreferredSize().height;
    myLabel.setPreferredSize(new Dimension(lindent,height));
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout);
  }
}