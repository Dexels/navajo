package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipi extends TipiPanel implements Tipi{

  public DefaultTipi() {
    setBackground(Color.white);
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    /**@todo Implement this com.dexels.navajo.tipi.TipiObject abstract method*/
  }

  public void addComponent(TipiComponent c){
    this.add((JComponent)c);
  }




}