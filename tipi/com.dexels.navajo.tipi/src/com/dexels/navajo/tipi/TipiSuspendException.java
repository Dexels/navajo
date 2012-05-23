package com.dexels.navajo.tipi;

import java.util.Stack;

import com.dexels.navajo.tipi.internal.TipiEvent;

//import com.dexels.navajo.document.nanoimpl.*;
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
public class TipiSuspendException extends RuntimeException {

	private static final long serialVersionUID = -2971767727074056454L;

	public TipiSuspendException(TipiEvent sourceEvent, Stack<TipiExecutable> continuationSource) {
		
	}
}