package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiScreen extends DefaultTipi {

  public DefaultTipiScreen() {
  }

  public void load(XMLElement definition, TipiContext context) throws TipiException {
    String elmName = definition.getName();
//    if(!elmName.equals("screen")){
//      throw new TipiException("Screen node not found!, found " + elmName + " instead.");
//    }
    String type = (String)definition.getAttribute("type");
    if (type.equals("desktop")) {
      setContainer(new JDesktopPane());
    } else {
      setContainer(new TipiPanel());
    }
    super.load(definition,context);



    String menubar = (String)definition.getAttribute("menubar");
    if (menubar!=null) {
      XMLElement xe = context.getTipiMenubarDefinition(menubar);
      TipiMenubar tm = context.createTipiMenubar();
      tm.load(xe,context);
      context.getTopLevel().setTipiMenubar(tm);
    }

    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("layout")) {
        TipiLayout tl = context.instantiateLayout(child);
        tl.createLayout(context,this,child,null);
//        parseTable(context,this,child);
      } else if(child.getName().equals("tipi-instance")) {
        String windowName = (String)child.getAttribute("name");
//        TipiWindow t = context.instantiateTipiWindow(windowName);
        Tipi t = (Tipi)context.instantiateClass(this,child);

        addTipi(t,context,null);
        getContainer().add(t.getContainer());
//        t.setBounds();
      } else {
        throw new TipiException("Unexpected element found [" + child.getName() +
                                "]. Expected 'table'");
      }
    }
  }
}