package com.dexels.navajo.tipi;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiLayout {
  protected Map componentValues = new HashMap();

  public TipiLayout() {
  }

  public abstract void createLayout(TipiContext context,Tipi t, XMLElement def, Navajo n) throws TipiException;
  public abstract void reCreateLayout(TipiContext context,Tipi t, Navajo n) throws TipiException;
  public abstract boolean needReCreate();
  public abstract boolean customParser();
  protected abstract void setValue(String name, TipiValue tv);

  private void loadValues(XMLElement values) {
    Vector children = values.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement xx = (XMLElement) children.get(i);
      String valueName = xx.getStringAttribute("name");
      TipiValue tv = new TipiValue();
      tv.load(xx);
      componentValues.put(valueName, tv);
      if (tv.getValue() != null && !"".equals(tv.getValue())) {
        setValue(tv.getName(), tv);
      }
    }
  }
}