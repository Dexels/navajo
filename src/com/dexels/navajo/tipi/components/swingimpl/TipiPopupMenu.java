package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiPopupMenu
    extends TipiSwingComponentImpl {
  private JPopupMenu myMenu;
//  public void removeFromContainer(Object c) {
//    myMenu.remove( (Component) c);
//  }
  public Object createContainer() {
    myMenu = new JPopupMenu();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myMenu;
  }
//  public Object getContainer() {
//    return myMenu;
//  }

//  public void addToContainer(Component menu, Object item) {
//    myMenu.add(menu);
//  }
}
