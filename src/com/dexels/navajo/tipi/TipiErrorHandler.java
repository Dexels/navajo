package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface TipiErrorHandler {
  public boolean hasErrors(Navajo n);
  public void showError();
  public void showError(String text);
  public void showError(Exception e);
  public Object createContainer();
  public void setContext(TipiContext c);
  public TipiContext getContext();
}