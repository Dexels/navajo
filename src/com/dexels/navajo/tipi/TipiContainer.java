package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import nanoxml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiContainer extends TipiComponent {
  public void addProperty(String name, TipiComponent comp);
  public void loadData(Navajo n);
}