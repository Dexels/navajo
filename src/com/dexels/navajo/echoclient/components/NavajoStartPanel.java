package com.dexels.navajo.echoclient.components;

import nextapp.echo.*;
import nextapp.echo.Panel;
import echopoint.ComboBox;
import nextapp.echo.event.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import echopoint.*;
import echopoint.Label;

public class NavajoStartPanel extends Panel {
  private Label myLabel;
  private ListBox initBox;
  private PushButton startButton;
  private NavajoWindow myStartWindow = null;
  public NavajoStartPanel(NavajoWindow startWindow) {
    myStartWindow = startWindow;
//    <client-config config="server.xml" impl="indirect" password=""
//        server="spiritus/sportlink/knvb/servlet/Postman" username="ROOT"/>

    NavajoClientFactory.createDefaultClient();
    NavajoClientFactory.getClient().setServerUrl("spiritus/sportlink/knvb/servlet/Postman");
    NavajoClientFactory.getClient().setUsername("ROOT");
    NavajoClientFactory.getClient().setPassword("");

    myLabel = new Label("Select initial service: ");
    initBox = new ListBox(new String[]{"InitUpdateMember","InitUpdateClub","InitCompetition","InitSearchClubs","InitSearchMembers"});
    startButton = new PushButton("Go!");
    initBox.setVisibleRowCount(3);
    add(myLabel);
    add(initBox);
    add(startButton);
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String bval = (String)initBox.getSelectedValue();
        if (bval==null) {
          return;
        }
        Navajo result = null;
        try {
          result = NavajoClientFactory.getClient().doSimpleSend(NavajoFactory.
              getInstance().createNavajo(), bval);
        }
        catch (ClientException ex) {
          ex.printStackTrace();
          return;
        }
        myStartWindow.setNavajo(result);
      }
    });
  }


}
