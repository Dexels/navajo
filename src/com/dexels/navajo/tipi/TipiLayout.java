package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.tipixml.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiLayout {
  public TipiLayout() {
  }

  public abstract void createLayout(TipiContext context,Tipi t, XMLElement def, Navajo n) throws TipiException;
  public abstract void reCreateLayout(TipiContext context,Tipi t, Navajo n) throws TipiException;
  public abstract boolean needReCreate();
}