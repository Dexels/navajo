package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.swingclient.*;
import javax.swing.JFrame;
import javax.swing.JDialog;
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
//    d.setLocationRelativeTo(getMainFrame());
//    d.pack();
    showCenteredDialog(d);
  }

  public void showCenteredDialog(JDialog dlg) {
    Dimension dlgSize = dlg.getSize();
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

    if (dlgSize.width>Toolkit.getDefaultToolkit().getScreenSize().width) {
      dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
      dlg.setSize(dlgSize);
   }

    dlg.setModal(true);
    dlg.show();
  }

  public boolean showQuestionDialog(String s) {
    int response = JOptionPane.showConfirmDialog( (Component) myContext.getTopLevel(), s,"",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
    return response==0;
  }

  public void showInfoDialog(String s) {
    int response = JOptionPane.showConfirmDialog( (Component) myContext.getTopLevel(), s,"",JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE);
  }

  public boolean areYouSure() {
    return showQuestionDialog("Are you sure?");
  }

}
