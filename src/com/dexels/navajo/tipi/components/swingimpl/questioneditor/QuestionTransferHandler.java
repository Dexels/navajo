package com.dexels.navajo.tipi.components.swingimpl.questioneditor;

import javax.swing.TransferHandler;
import javax.swing.JComponent;
import java.awt.event.InputEvent;
import java.awt.datatransfer.Transferable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class QuestionTransferHandler extends TransferHandler {
  public QuestionTransferHandler() {
  }

  public void exportAsDrag(JComponent comp, InputEvent e, int action) {
  }

  public int getSourceActions(JComponent c) {
    return 0;
  }

  public boolean importData(JComponent comp, Transferable t) {
    return false;
  }
}
