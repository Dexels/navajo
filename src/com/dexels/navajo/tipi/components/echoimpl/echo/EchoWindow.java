package com.dexels.navajo.tipi.components.echoimpl.echo;

import echopoint.ScrollablePanel;
import echopoint.*;
import nextapp.echo.Component;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class EchoWindow extends DialogPanel {
//  TitleBar myTitleBar = new TitleBar("No title");
  private String myTitle = null;
  private Panel myContentPanel = new Panel();

  public EchoWindow() {
//    add(myTitleBar);
    add(myContentPanel);
  }

  public void addToContent(Component c) {
    myContentPanel.add(c);
  }

  public Panel getContentPanel() {
    return myContentPanel;
  }

  public void setTitle(String title) {
//    myTitle = title;
//    myTitleBar.setText(title);
    getTitleBar().setText(title);
  }

  public String getTitle() {
    return getTitleBar().getText();
  }

}
