package com.dexels.navajo.studio.wizard;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.awt.Frame;

public class ExampleWizard extends AbstractWizard {

  public ExampleWizard(Frame owner) {
    super(owner);
  }

  public void init() {
    System.out.println("init called()");
  }

  public String finish() {
    return "<expression name=\"2\"/>";
  }

}