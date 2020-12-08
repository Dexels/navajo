/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JPanel;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.QueryableComponent;
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
public class TipiArrayPanel extends TipiSwingDataComponentImpl implements QueryableComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6607929021126521895L;
	private String messagePath = null;

	@Override
	public Object createContainer() {
		JPanel myPanel = new JPanel();
		myPanel.setOpaque(false);
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

	@Override
	protected void cascadeLoad(Navajo n, String method) throws TipiException {
		// logger.debug("ArrayPanel: Not cascading: "+method);
	}

    @Override
    public Boolean containsDirtyPropertyComponents() {
        return false;
    }

}
