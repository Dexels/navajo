
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.*;
import com.dexels.navajo.*;

public class Operand {

  public String value;
  public String type;
  public String option;
  public Object oValue;

  public Operand(String value, String type, String option) {
    this.value = value;
    this.type = type;
    this.option = option;
  }

  public Operand(Object value, String type, String option) {
    this.oValue = value;
    this.type = type;
    this.option = option;
  }

}