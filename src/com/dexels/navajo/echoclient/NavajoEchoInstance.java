package com.dexels.navajo.echoclient;

import nextapp.echo.*;
import com.dexels.navajo.echoclient.components.*;
import echopoint.*;
import nextapp.echo.event.*;
import java.util.*;


public class NavajoEchoInstance extends EchoInstance {
  private NavajoWindow myWindow;

  public NavajoEchoInstance() {
    myWindow = new NavajoWindow();
    myWindow.setStartPanel();
  }

  public Window init() {
    return myWindow;
  }


}