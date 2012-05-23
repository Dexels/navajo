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
  
	private static final long serialVersionUID = 4942222966862889552L;
public NavajoExceptionImpl() {
  }
  public NavajoExceptionImpl(String message) {
    super(message);
  }
  public NavajoExceptionImpl(Throwable root) {
	super(root);
  }
  public NavajoExceptionImpl(String message, Throwable root) {
		super(message,root);
	 }
  
}