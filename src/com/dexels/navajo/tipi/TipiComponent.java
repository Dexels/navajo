package com.dexels.navajo.tipi;

import nanoxml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiComponent {
  public void load(XMLElement elm, TipiContext context) throws TipiException;
  public void addComponent(TipiComponent c);
}