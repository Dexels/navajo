package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import nextapp.echo.*;
import echopoint.layout.*;
import java.util.*;
import echopoint.layout.GridLayoutManager.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiGridLayout extends TipiLayoutImpl {

  private GridLayoutManager myLayout = null;

  public TipiGridLayout() {
  }
  protected void setValue(String name, TipiValue tv) {
    /**@todo Implement this com.dexels.navajo.tipi.internal.TipiLayout abstract method*/
  }
  protected Object parseConstraint(String text) {
    StringTokenizer st = new StringTokenizer(text,",");
    int x = Integer.parseInt(st.nextToken());
    int y = Integer.parseInt(st.nextToken());
    int w = Integer.parseInt(st.nextToken());
    int h = Integer.parseInt(st.nextToken());
    System.err.println("PARSED CONSTRAINT: "+x+","+y+","+w+","+h);
    return new CellConstraints(x,y,w+x-1,y+h-1);
  }

  public void createLayout() throws com.dexels.navajo.tipi.TipiException {
    myLayout = new GridLayoutManager(100,100);
    setLayout(myLayout);
  }

}
