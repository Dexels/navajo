package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
//import nextapp.echo.*;
import echopoint.MenuBar;
//import echopoint.*;
import echopoint.Panel;
import nextapp.echo.*;
import echopoint.layout.*;
import echopoint.layout.GridLayoutManager.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiFrame extends TipiEchoDataComponentImpl {

  private ContentPane myContentPane = new ContentPane();

  private Panel myMainPanel = null;
  private Panel myPanel = null;
  private Panel myMenuPanel = null;

  public TipiFrame() {
  }

  public Object createContainer() {

    Window w = new Window();
    w.setContent(myContentPane);

    myPanel = new Panel();
    myMainPanel = new Panel();
    myMenuPanel = new Panel();

    GridLayoutManager manager = new GridLayoutManager(1,100);
    myPanel.setLayoutManager(manager);
    manager.setFullHeight(true);
    manager.setFullWidth(true);
    myPanel.add(myMenuPanel);
    manager.newLine();
    myPanel.add(myMainPanel);
    myContentPane.add(myPanel);
//    HourGlass h = new HourGlass();
//    w.getContent().add(h);
    return w;

  }

  /**
   * setComponentValue
   *
   * @param name String
   * @param object Object
   * @todo Implement this
   *   com.dexels.navajo.tipi.components.core.TipiComponentImpl method
   */
  protected void setComponentValue(String name, Object object) {
    Window w = (Window)getContainer();
    if ("title".equals(name)) {
      w.setTitle(""+object);
    }
    if ("w".equals(name)) {
      w.setWidth(((Integer)object).intValue());
    }
    if ("h".equals(name)) {
      w.setHeight(((Integer)object).intValue());
    }
  }


  public void addToContainer(Object c, Object constraints) {
    if (MenuBar.class.isInstance(c)) {

      GridLayoutManager manager = new GridLayoutManager(1,1);
      myMenuPanel.setLayoutManager(manager);
//      manager.setFullHeight(true);
      manager.setFullWidth(true);

      Label l = new Label(" \n ");
//      System.err.println("MENUBARRRRRRRRRRRRRRRRR");
//      HorizontalLayoutManager lm = new HorizontalLayoutManager();
//      lm.setInsets(new Insets(0,0,40,40));
//      myMenuPanel.setLayoutManager(lm);
      myMenuPanel.add((Component)c);
      myMenuPanel.add(l);

    } else {
      myMainPanel.add((Component)c);
//      myPanel.setHe
    }
  }


}
