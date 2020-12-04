/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

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
public class TipiArrayPanel extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -6921316557265986129L;
	private String messagePath = null;

	@Override
	public Object createContainer() {
		Component panel = new VerticalLayout();
		return panel;
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
	}

}
