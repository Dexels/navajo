package com.dexels.navajo.document.nanoimpl;

import com.dexels.navajo.document.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class NavajoExceptionImpl extends NavajoException {
  private Exception rootCause = null;
  public NavajoExceptionImpl() {
  }
  public NavajoExceptionImpl(String message) {
    super(message);
  }
  public NavajoExceptionImpl(Exception root) {
    rootCause = root;
  }
  public Exception getRootCause() {
    return rootCause;
  }
  public Exception getException() {
    return rootCause;
  }

}