package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.swingclient.*;
import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SwingTipiUserInterface extends DummyUserInterface{
  private final SwingTipiContext myContext;

  /**
   * This is an extra offset to make sure the bottom will not be behind the status bar
   * Not nice.
   */
  private final int startBarHeight = 26;


  public SwingTipiUserInterface(SwingTipiContext s) {
    myContext = s;
  }

  public JFrame getMainFrame() {
    return (JFrame)myContext.getTopLevel();
  }

  public void addDialog(JDialog d) {
    d.setLocationRelativeTo(getMainFrame());
    d.pack();
    showCenteredDialog(d);
  }

  public void showCenteredDialog(JDialog dlg) {
    Dimension dlgSize = dlg.getPreferredSize();
    Rectangle r = getMainFrame().getBounds();
    Dimension frmSize = new Dimension(r.width, r.height);
    Point loc = getMainFrame().getLocation();
    int x =  Math.max(0, (frmSize.width - dlgSize.width) / 2 + loc.x + r.x);
    int y = Math.max(0, (frmSize.height - dlgSize.height) / 2 + loc.y + r.y);
    dlg.setLocation(x, y);


    if (dlgSize.height>(Toolkit.getDefaultToolkit().getScreenSize().height-startBarHeight)) {
      dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height-startBarHeight;
      dlg.setSize(dlgSize);
    }

//   GraphicsConfiguration gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
//    if (dlgSize.height>gd.getBounds().height) {
//      dlgSize.height = gd.getBounds().height;
//      dlg.setSize(dlgSize);
//    }

    if (dlgSize.width>Toolkit.getDefaultToolkit().getScreenSize().width) {
      dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
      dlg.setSize(dlgSize);
   }

    dlg.setModal(true);
    dlg.show();
  }

}
