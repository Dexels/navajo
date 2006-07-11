package com.dexels.navajo.document.base;

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
  
  public NavajoExceptionImpl() {
  }
  public NavajoExceptionImpl(String message) {
    super(message);
  }
  public NavajoExceptionImpl(Exception root) {
	super(root);
  }
  
}