/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.terminal.Resource;
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
public class TipiSwingTab extends VerticalLayout implements TipiTabbable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1869629608167928815L;
	private Resource tabIcon;
	private int index;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingTab.class);

	@Override
	public Resource getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(Resource tabIcon) {
		Resource old = this.tabIcon;
		this.tabIcon = tabIcon;
		if (old == tabIcon) {
			logger.debug("whoops, identical");
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}




}
