package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.JLabel;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.tipi.swingclient.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BaseLabel extends JLabel implements Ghostable {
  ResourceBundle res;
  private String labelName = "";
  private boolean ghosted = false;
  private boolean enabled = true;

  public BaseLabel() {
    res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.TextLabels");
    this.setOpaque(false);
  }

  public void setLabel(String s) {
    labelName = s;
    if(s.equals("")){
      setText("");
    }else{
      setText(res.getString(s));
    }
  }
  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
    super.setEnabled(enabled && (!ghosted));
  }

  @Override
public void setEnabled(boolean e) {
    enabled = e;
    super.setEnabled(enabled && (!ghosted));
  }

}