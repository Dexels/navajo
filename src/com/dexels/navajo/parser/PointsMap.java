package com.dexels.navajo.parser;

import com.dexels.navajo.mapping.*;
import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.*;
import java.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class PointsMap implements Mappable {

  public Vector [] myPoints;
  public String myString;

  public PointsMap() {
  }
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException {
    myPoints = new Vector[2];
    myPoints[0] = new Vector();
    myPoints[0].add(new Integer(0));
    myPoints[0].add(new Integer(2));
    myPoints[1] = new Vector();
    myPoints[1].add(new Integer(10));
    myPoints[1].add(new Integer(20));
    this.myString = "albert";
  }

  public Vector [] getMyPoints() {
    return this.myPoints;
  }

  public String getMyString() {
    return this.myString;
  }
  public void store() throws MappableException, UserException {

  }
  public void kill() {

  }

}