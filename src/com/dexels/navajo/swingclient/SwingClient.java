package com.dexels.navajo.swingclient;


//import com.dexels.sportlink.client.swing.*;

public class SwingClient {

  private static UserInterface userInterface;

  static {
    setUserInterface(new DummyUserInterface());
  }

  public static UserInterface getUserInterface() {
    return userInterface;
  }

  public static void setUserInterface(UserInterface u) {
    userInterface = u;
  }

}