package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class EventParser extends BaseTipiParser {
  public EventParser() {
  }
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    if (event!=null) {
      Iterator it = event.getEventKeySet().iterator();
      while (it.hasNext()) {
        String current = (String)it.next();
        System.err.println("KEY: "+current);
        System.err.println("Value: "+event.getEventParameter(expression).getValue());
      }
    }
    return event.getEventParameter(expression).getValue();
  }

}
