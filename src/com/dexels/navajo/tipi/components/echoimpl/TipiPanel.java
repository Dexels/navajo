package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import echopoint.Panel;
import nextapp.echo.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiPanel extends TipiEchoDataComponentImpl {
  Grid grid = new Grid();
  public TipiPanel() {
  }
  public Object createContainer() {
    Panel p = new Panel();
//    p.add(grid);
    return p;
  }

//  public void addToContainer(Object o, Object contraints){
//
//  }
//
//  public void setContainerLayout(Object l){
//
//  }

}
