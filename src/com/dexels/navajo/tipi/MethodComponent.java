package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import nanoxml.*;
import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface MethodComponent extends TipiBase {
  public void loadData(Navajo n, TipiContext tc);
  public void load(XMLElement elm,XMLElement instance, Tipi tc, TipiContext context);
}