package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import java.util.*;
import com.dexels.navajo.parser.*;

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
      Set ks = event.getEventKeySet();
      if (ks==null) {
        System.err.println("No keys found in event");
        return "";
      }
      Iterator it = event.getEventKeySet().iterator();
      while (it.hasNext()) {
        String current = (String)it.next();
//        System.err.println("KEY: "+current);
//        System.err.println("Value: "+event.getEventParameter(expression).getValue());
      }
      TipiValue o = event.getEventParameter(expression);
       if (o==null) {
         return null;
       }
       return o.getValue();
    }
    return null;
   }

}
