package com.dexels.navajo.tipi.impl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

public class DefaultTipiDesktop extends DefaultTipi {

  public Container createContainer() {
    return new JDesktopPane();
  }

  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c,0);
  }

  public DefaultTipiDesktop() {
    initContainer();
  }

//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }

}