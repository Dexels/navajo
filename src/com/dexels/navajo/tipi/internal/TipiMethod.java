package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import java.util.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiMethod
    implements TipiExecutable {

  private String myMethodName = null;
  private TipiComponent myComponent = null;
  private final ArrayList myExecutables = new ArrayList();
  public TipiMethod() {
  }


  public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException {
    myComponent = tc;
    if (elm.getName().equals("event")) {
      String stringType = (String) elm.getAttribute("type");
      myMethodName = stringType;
      Vector temp = elm.getChildren();
      for (int i = 0; i < temp.size(); i++) {
        XMLElement current = (XMLElement) temp.get(i);
        if (current.getName().equals("condition")) {
          TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(myComponent);
          ta.loadConditionStyle(current, myComponent);
          myExecutables.add(ta);
        }
        if (current.getName().equals("block")) {
          TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(myComponent);
          ta.load(current, myComponent);
          myExecutables.add(ta);
        }
        if (current.getName().equals("action")) {
          TipiAction ta = context.instantiateTipiAction(current, myComponent);
          myExecutables.add(ta);
        }
      }
    }
  }


  public TipiComponent getComponent() {
    return null;
  }

  public TipiExecutable getExecutableChild(int index) {
    return null;
  }

  public int getExecutableChildCount() {
    return 0;
  }

  public void performAction(TipiEvent te) throws TipiBreakException, TipiException {
  }

  public XMLElement store() {
    return null;
  }
}
