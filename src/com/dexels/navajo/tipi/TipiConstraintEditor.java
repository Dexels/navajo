package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.studio.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiConstraintEditor extends ComponentSelectionListener {
  public void parseString(String s);
  public Object getConstraint();
  public void setConstraint(Object o);
  public String getConstraintString();
}