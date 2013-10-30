package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JSeparator;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;

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
public class TipiMenuSeparator extends TipiSwingComponentImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7684025385194079500L;
	private JSeparator mySeparator = null;

	@Override
	public Object createContainer() {
		mySeparator = new JSeparator();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return mySeparator;
	}

}
