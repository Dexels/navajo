package com.dexels.navajo.tipi.components.swingimpl;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFileChooser
    extends TipiSwingComponentImpl {
  final JTextField fileNameField = new JTextField();
  JButton selectButton = new JButton("Browse");

  private String defaultDir = null;
  private String selectionMode = "all";

  public TipiFileChooser() {
  }
  public void setWaitCursor(boolean b) {
    super.setWaitCursor(b);
    fileNameField.setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));
    selectButton.setCursor(Cursor.getPredefinedCursor(b?Cursor.WAIT_CURSOR:Cursor.DEFAULT_CURSOR));
  }

  public Object createContainer() {
    final JPanel p = new JPanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    p.setLayout(new GridBagLayout());
//    JButton selectButton = new JButton("Browse");
    selectButton.setMargin(new Insets(0,0,0,0));
    p.add(selectButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 2, 2));
    p.add(fileNameField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    selectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
//        if (defaultDir==null) {
//          defaultDir = System.getProperties("user.dir");
//        }
        JFileChooser fc = new JFileChooser(defaultDir);
        if ("all".equals(selectionMode)) {
          fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        }
        if ("files".equals(selectionMode)) {
          fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        if ("dirs".equals(selectionMode)) {
          fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        int i = fc.showOpenDialog(p);
        if (i == fc.APPROVE_OPTION) {
          File f = fc.getSelectedFile();
          fileNameField.setText(f.getAbsolutePath());
        }
      }
    });
    return p;
  }

  public Object getComponentValue(String name) {
    if ("file".equals(name)) {
      return fileNameField.getText();
    }
    return super.getComponentValue(name);
  }

  public void setComponentValue(final String name, final Object object) {
    if ("file".equals(name)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          fileNameField.setText( (String) object);
          return;
        }
      });
    }
    if ("defaultdir".equals(name)) {
      defaultDir = object.toString();
    }

    if ("selectionMode".equals(name)) {
      selectionMode = object.toString();
    }

    super.setComponentValue(name, object);
  }
}
