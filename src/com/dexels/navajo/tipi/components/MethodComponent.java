package com.dexels.navajo.tipi.components;
import nanoxml.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MethodComponent extends TipiButton implements TipiComponent {
  public MethodComponent() {
  }

  public void load(XMLElement elm, TipiContext context) {
    String name = (String)elm.getAttribute("name");
    setText(name);
  }
  public void addComponent(TipiComponent c, TipiContext context) {
  }
  public void addProperty(String name, TipiComponent comp, TipiContext context) {
  }
  public void addTipi(Tipi t, TipiContext context) {
  }
  public void addTipiContainer(TipiContainer t, TipiContext context) {
  }
}