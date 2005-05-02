package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiReference {
  /**
   * Sets the value of this reference.
   * @param expression The unevaluated expression. Only used by AttributeRef, because the
   * TipiComponentImpl likes to know the unevaluated expression.
   * @param value The evaluated object. This one is ignored by AttributeRef, as it will
   * evaluate the expression itself. todo: Change that.
   * @param tc The owner of this reference.
   */
  public void setValue(Object expression, TipiComponent tc);
}
