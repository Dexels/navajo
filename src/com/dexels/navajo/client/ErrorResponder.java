package com.dexels.navajo.client;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ErrorResponder {
  public void check(String message);
  public void check(Navajo n);
  public void check(Exception e);
  public boolean didErrorsOccur();
  public void checkForAuthorization(Navajo n);
  public void checkForAuthentication(Navajo n);
 }
