package com.dexels.navajo.echoclient.components;

import nextapp.echo.Window;
import com.dexels.navajo.document.*;
import java.util.*;
import echopoint.*;
import nextapp.echo.*;
import nextapp.echo.event.*;
import com.dexels.navajo.client.*;
import echopoint.Label;

public class NavajoWindow extends Window {

  public void setStartPanel() {
    ContentPane p = new ContentPane();
    setContent(p);
    p.add(new NavajoStartPanel(this));
  }

  public void setNavajo(Navajo n) {
    ContentPane p = new ContentPane();
    setContent(p);
    setDefaultCloseOperation(Window.DISPOSE_ON_CLOSE);
    Label l = new Label("Navajo window");
    p.add(l);

    setWidth(640);
    setHeight(480);
    try {
      setMessages(p,n);
      setMethods(p,n);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    PushButton pb = new PushButton();
    pb.setText("Start");
    p.add(pb);
    pb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setStartPanel();
      }
    });

  }

  private void setMethods(ContentPane p, final Navajo n) {
    ArrayList methods = n.getAllMethods();
    for (int i = 0; i < methods.size(); i++) {
      Method m = (Method)methods.get(i);
//      System.err.println("Adding method: "+m.getName());
      PushButton pb = new PushButton();
      pb.setText(m.getName());
      pb.setActionCommand(m.getDescription());
      p.add(pb);
      pb.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          PushButton source = (PushButton)e.getSource();
          Navajo reply = null;
          try {
            System.err.println("SENDING MOR METHOD: "+source.getText());
             reply = NavajoClientFactory.getClient().doSimpleSend(n,
                source.getText());
          }
          catch (ClientException ex) {
            ex.printStackTrace();
          }
          setNavajo(reply);
        }
      });
    }

  }

  private void setMessages(ContentPane p, final Navajo n) throws NavajoException {
    ArrayList msgs = n.getAllMessages();
    for (int i = 0; i < msgs.size(); i++) {
      Message m = (Message)msgs.get(i);
      MessagePanel mp = new MessagePanel();
      mp.setMessage(m);
      p.add(mp);
    }

  }


}
