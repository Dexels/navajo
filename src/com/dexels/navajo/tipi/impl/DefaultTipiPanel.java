package com.dexels.navajo.tipi.impl;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiPanel extends DefaultTipi {

  public Container createContainer() {
    return new JPanel();
  }

  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c,constraints);
  }

  public DefaultTipiPanel() {
    setContainer(createContainer());
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);
  }
}