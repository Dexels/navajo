package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.core.*;
import java.util.*;
import echopoint.layout.GridLayoutManager.*;
import echopoint.layout.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoBorderLayout extends TipiLayoutImpl {
  public TipiEchoBorderLayout() {
  }
  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract method*/
  }
  public void createLayout() throws com.dexels.navajo.tipi.TipiException {
     myLayout = new GridLayoutManager(100,100);
     setLayout(myLayout);
   }
  protected Object parseConstraint(String text) {
    if ("north".equals(text)) {
      return(new CellConstraints(1,0));
    }
    if ("south".equals(text)) {
      return(new CellConstraints(1,2));
    }

    if ("west".equals(text)) {
      return(new CellConstraints(0,1));
    }
    if ("east".equals(text)) {
      return(new CellConstraints(2,1));
    }
    if ("center".equals(text)) {
      return(new CellConstraints(1,1));
    }
    return new CellConstraints(0,0);
  }

}
