package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.JDialog;
import java.awt.HeadlessException;
import java.awt.Container;
import javax.swing.JMenuBar;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingDialog extends JDialog
    implements TipiSwingFrame {

  public TipiSwingDialog(JFrame f) {
    super(f);
  }

  public TipiSwingDialog(JDialog f) {
    super(f);
  }

  public void setExtendedState(int state) {
//    System.err.println("Ignoring setIconImage in TipiSwingDialog. This should not happen. Check classdef.xml");
  }

  public int getExtendedState() {
//    System.err.println("Ignoring setIconImage in TipiSwingDialog. This should not happen. Check classdef.xml");
    return 0;
  }

   public void setIconImage(ImageIcon i) {
//    System.err.println("Ignoring setIconImage in TipiSwingDialog. This should not happen. Check classdef.xml");
  }
}
