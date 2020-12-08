/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4685485178072104480L;

	public TipiException() {
	}

	public TipiException(String desc, Throwable cause) {
		super(desc, cause);
	}

	public TipiException(Throwable cause) {
		super(cause);
	}

	public TipiException(String desc) {
		super(desc);
	}
}
