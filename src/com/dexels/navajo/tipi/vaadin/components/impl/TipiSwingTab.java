package com.dexels.navajo.tipi.vaadin.components.impl;

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
	private Object tabObject;



	public Resource getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(Resource tabIcon) {
		Resource old = this.tabIcon;
		this.tabIcon = tabIcon;
		if (old == tabIcon) {
			System.err.println("whoops, identical");
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}




}
