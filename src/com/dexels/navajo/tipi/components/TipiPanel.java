package com.dexels.navajo.tipi.components;

import javax.swing.JPanel;
import javax.swing.border.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPanel extends JPanel {
  public TipiPanel() {
  }

  public void addBorder(){
    System.err.println("_----------------------------------_>> Addborder called");
    this.setBorder(new LineBorder(Color.red, 5));
    this.revalidate();
  }

//  public LayoutManager getTipiLayoutManager
}