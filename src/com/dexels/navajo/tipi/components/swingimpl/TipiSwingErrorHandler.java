package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.swingclient.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 * @deprecated
 */
@Deprecated
public class TipiSwingErrorHandler
    extends BaseTipiErrorHandler {
  public TipiSwingErrorHandler() {
    //setContainer(createContainer);
  }

  public void showError() {
    TipiContext c = getContext();
    if (c != null) {
      showErrorDialog(getErrorMessage());
    }
    else {
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showError(String text) {
    TipiContext c = getContext();
    if (c != null) {
      showErrorDialog(text);
    }
    else {
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showError(Exception e) {
    TipiContext c = getContext();
    if (c != null) {
      showErrorDialog(e.getMessage());
    }
    else {
      System.err.println("DefaultTipiErrorHandler, context not set!! ");
    }
  }

  public void showErrorDialog(final String error) {
    RootPaneContainer toplevel = ((SwingTipiContext)getContext()).getTopDialog();

    if (toplevel instanceof JFrame) {
      final JFrame top = (JFrame) toplevel;
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          JOptionPane.showMessageDialog(top, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
      });
      return;
    }
    
    SwingClient.getUserInterface().addErrorDialog(error);


  }
//  public Object createContainer(){
//    return new JPanel();
//  }
}
