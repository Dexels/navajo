package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
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

  public void showErrorDialog(String error) {
    final JFrame top = (JFrame) getContext().getDefaultTopLevel().getContainer();
    final String errorString = error;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JOptionPane.showMessageDialog(top, errorString, "Error", JOptionPane.ERROR_MESSAGE);
      }
    });
  }
//  public Object createContainer(){
//    return new JPanel();
//  }
}
