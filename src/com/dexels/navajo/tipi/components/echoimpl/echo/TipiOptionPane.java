package com.dexels.navajo.tipi.components.echoimpl.echo;

import echopoint.DialogPanel;
import echopoint.PushButton;
import nextapp.echo.*;
import nextapp.echo.event.*;
import com.dexels.navajo.tipi.*;
import echopoint.Label;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiOptionPane  {

  public static void showQuestion(final Window parent, String text, String title, String yes, String no) throws TipiBreakException{
    final DialogPanel myPanel = new DialogPanel();
    PushButton yesButton = new PushButton();
    PushButton noButton = new PushButton();

    Label myLabel = new Label();
    myLabel.setText(text);
    myPanel.getTitleBar().setText(title);
    yesButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        myPanel.close();
        parent.getContent().remove(myPanel);
      }
    });
    yesButton.setText(yes);
    noButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        myPanel.close();
        parent.getContent().remove(myPanel);
      }
    });
    noButton.setText(no);
    myPanel.add(myLabel);
    myPanel.add(yesButton);
    myPanel.add(noButton);
    parent.getContent().add(myPanel);
    myPanel.setVisible(true);
  }

  public static void showInfo(final Window parent, String text, String title, String closeText) {
    final DialogPanel myPanel = new DialogPanel();
    PushButton myButton = new PushButton();
    Label myLabel = new Label();
    myLabel.setText(text);
    myPanel.getTitleBar().setText(title);
    myButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        myPanel.close();
        parent.getContent().remove(myPanel);
      }
    });
    myButton.setText(closeText);
    myPanel.add(myLabel);
    myPanel.add(myButton);
    parent.getContent().add(myPanel);
    myPanel.setVisible(true);
  }

}
