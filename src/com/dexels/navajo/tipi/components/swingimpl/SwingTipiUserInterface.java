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
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x + r.x, (frmSize.height - dlgSize.height) / 2 + loc.y + r.y);
    dlg.setModal(true);
    dlg.show();
  }

}
