package com.dexels.navajo.tipi;

import com.dexels.navajo.document.*;

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
public interface TipiErrorHandler {
	public String hasErrors(Navajo n);

	public void initResource();

	// public Object createContainer();
	public void setContext(TipiContext c);

	public TipiContext getContext();
}