/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import com.dexels.navajo.document.NavajoException;


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