package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.tipi.*;

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
public class TipiMessageStoreImpl extends TipiDataComponentImpl implements TipiDataComponent {
	private Object myObject;

	public Object createContainer() {
		return null;
	}

	/**
	 * Where on earth is this construction good for?! Please dont use it, I may
	 * rip it out at any moment. If you need do save something, Use a global
	 */

	public void setComponentValue(String name, Object object) {
		// selectedMessage is the only name that occurs, I guess.
		myObject = object;
	}

	public Object getComponentValue(String name) {
		return super.getComponentValue(name);
	}
}
