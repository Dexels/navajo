package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;

public class TipiParser
    extends BaseTipiParser {
  public TipiParser() {
  }

  public Object parse(TipiComponent source, String expression) {
    return getTipiByPath(source, expression);
  }

  public String toString(Object o, TipiComponent source) {
    TipiComponent tc = (TipiComponent)o;
    return tc.getPath();
  }
}
