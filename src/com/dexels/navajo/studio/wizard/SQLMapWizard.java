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
  * This wizard is intended to be used for working with SQLMap mappable objects.
  * It should simplify constructing SQLMap script fragments by providing
  * access to a specified datasource (as specifief in sqlmap.xml datasources section).
  * Entering a query and automatically creating properties for all column names and
  * setting the values simultaneously.
  *
  * Step 1
  * First the user enters a query.
  * The wizard automatically detects the number of parameters in the query and will generate
  * inputfields for each of them (allowing the user also to choose from the incoming BPFL).
  *
  * Step 2
  * Default values can be set in the parameter fields for testing purposes (such that the query can
  * actually get executed and the wizard can continue with next step.
  *
  * Step 3
  * The wizard allows for setting alternative property names and disabling certain
  * columns. Furthermore the user can overide the default expression $getColumnValue('[column]')
  * by its own expression (for manipulating purposes).
  *
  * Step 4
  * After specifying everything the corresponding script fragment can be generated.
  *
  */

public class SQLMapWizard extends AbstractWizard {

  public SQLMapWizard(Frame owner) {
    super(owner);
  }
}