package com.dexels.navajo.studio.wizard;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface WizardInterface {

  /**
   * Initializations can be made in the init() method.
   */

  public void init();


  /**
   * The finish() methods return the generated script fragment as a result of the Wizard actions.
   * The fragment can be included in the main script.
   */
  public String finish();
}