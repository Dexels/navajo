package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JPanel;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
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
public class TipiArrayPanel extends TipiSwingDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6607929021126521895L;
	private String messagePath = null;

	public Object createContainer() {
		JPanel myPanel = new JPanel();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myPanel;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException,
			TipiBreakException {
		loadArrayData(n, messagePath);
		super.loadData(n, method);
		doLayout();
	}

	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("messagePath")) {
			return messagePath;
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("messagePath")) {
			messagePath = (String) object;
			return;
		}
		super.setComponentValue(name, object);
	}

	protected void cascadeLoad(Navajo n, String method) throws TipiException {
		// System.err.println("ArrayPanell: Not cascading: "+method);
	}

}
