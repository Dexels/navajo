package com.dexels.navajo.tipi.components.echoimpl.echo;

import echopoint.DialogPanel;
import echopoint.*;
import nextapp.echo.*;
import nextapp.echo.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiOptionPane extends DialogPanel {
  private PushButton myButton = new PushButton();
  private Label myLabel = new Label();
  public TipiOptionPane() {
    final TipiOptionPane me = this;
    myButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        me.close();
      }
    });
    myButton.setText("Close");
    super.add(myLabel);
    super.add(myButton);
  }

  public void setText(String text) {
    myLabel.setText(text);
//    super.setV
  }

}
