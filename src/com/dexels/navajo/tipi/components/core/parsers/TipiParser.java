package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiParser
    extends BaseTipiParser {
  public TipiParser() {
  }

  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    return getTipiByPath(source, expression);
  }

  public String toString(Object o, TipiComponent source) {
    TipiComponent tc = (TipiComponent)o;
    if (tc==null) {
      return null;
    }
    return tc.getPath();
  }
}
