package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JPanel;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;

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
public class TipiPanel extends TipiSwingDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6190251046684031410L;

	public Object createContainer() {
		JPanel myPanel = new TipiSwingPanel(TipiPanel.this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myPanel;
	}

}
