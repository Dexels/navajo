package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ObjectParser extends TipiTypeParser {
  public ObjectParser() {
  }
  public Object parse(TipiComponent source, String expression) {
    throw new RuntimeException("Objects are unparsable");
  }

}
