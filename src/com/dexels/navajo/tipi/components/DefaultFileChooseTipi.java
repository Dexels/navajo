package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.impl.*;
import java.awt.Container;
import com.dexels.navajo.tipi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultFileChooseTipi extends DefaultTipi{
  final JTextField fileNameField = new JTextField();

  public DefaultFileChooseTipi() {
  }

  public Container createContainer() {
    final JPanel p = new JPanel();
    p.setLayout(new GridBagLayout());
    JButton selectButton = new JButton("Open");
    p.add(selectButton,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 2, 2));
    p.add(fileNameField,           new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

    selectButton.addActionListener(new ActionListener(){
       public void actionPerformed(ActionEvent e){
         JFileChooser fc = new JFileChooser("Open");
         int i = fc.showOpenDialog(p);
         if(i == fc.APPROVE_OPTION){
           File f = fc.getSelectedFile();
           fileNameField.setText(f.getAbsolutePath());
         }
       }
    });
    return p;
  }

  public Object getComponentValue(String name) {
//    System.err.println("Getting: " + name + " myFile=" + fileNameField.getText());
    if("file".equals(name)){
      return fileNameField.getText();
    }
    return super.getComponentValue(name);
  }


}