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
 /**
  * This is the base class for all pluggable script wizards.
  */

public class AbstractWizard extends javax.swing.JWindow implements WizardInterface {

  public AbstractWizard(Frame owner) {
    super(owner);
  }

  public String runWizard() {
    init();
    return finish();
  }

  public void init() {
    /**@todo: Implement this com.dexels.navajo.studio.wizard.WizardInterface method*/
    throw new java.lang.UnsupportedOperationException("Method init() not yet implemented.");
  }
  public String finish() {
    /**@todo: Implement this com.dexels.navajo.studio.wizard.WizardInterface method*/
    throw new java.lang.UnsupportedOperationException("Method finish() not yet implemented.");
  }

}