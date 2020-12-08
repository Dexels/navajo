/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi;

import java.io.Serializable;

import com.dexels.navajo.tipi.internal.TipiEvent;

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
public abstract class TipiTypeParser implements Serializable {
	private static final long serialVersionUID = 2334330060374132936L;
	private Class<?> myReturnType = null;

	/**
	 * Note to implementers: EXPRESSION CAN BE NULL. Deal with it.
	 * 
	 * @param source
	 * @param expression
	 * @param event
	 * @return
	 */
	public abstract Object parse(TipiComponent source, String expression,
			TipiEvent event);

	public Class<?> getReturnType() {
		return myReturnType;
	}

	public void setReturnType(Class<?> c) {
		myReturnType = c;
	}

}
