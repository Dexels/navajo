package com.dexels.navajo.tipi.studio.components;

import java.awt.event.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import com.dexels.navajo.document.nanoimpl.*;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PreviewFrame extends JInternalFrame implements TopLevel{
  TipiContext c;

  public PreviewFrame() {
    this.getContentPane().setLayout(new BorderLayout());
    c = TipiContext.getInstance();
    c.setUIMode(TipiContext.UI_MODE_STUDIO);
    c.setToplevel(this);
  }


  public void setJMenuBar(JMenuBar j) {
    //
  }
  public void setTitle(String s) {
     super.setTitle(s);
  }
  public void setTipiMenubar(TipiMenubar tm) {
    //
  }

  public void load(URL u){
    try {
      c.parseURL(u);
      this.getContentPane().add(c.getTopScreen().getContainer(), BorderLayout.CENTER);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}