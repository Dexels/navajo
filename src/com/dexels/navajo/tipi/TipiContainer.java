package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import nanoxml.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiContainer extends TipiComponent {
  public void addProperty(String name, TipiComponent comp, TipiContext context, Map td);
  public void loadData(Navajo n, TipiContext tc);
  public void addTipiContainer(TipiContainer t, TipiContext context, Map td);
}