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

public interface MethodComponent extends TipiComponent {
  public void loadData(Navajo n, TipiContext tc);
  public void load(XMLElement elm, TipiComponent tc, TipiContext context);
}