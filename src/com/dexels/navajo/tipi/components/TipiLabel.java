package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiLabel extends BaseLabel implements TipiComponent {
  public TipiLabel() {
  }
  public void addTipiEvent(TipiEvent te) {
  }

  public void addComponent(TipiComponent c) {
  }

  public void load(XMLElement e, TipiContext tc) {
    setText((String)e.getAttribute("value"));
  }
}