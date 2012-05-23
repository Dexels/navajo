package com.dexels.navajo.tipi.vaadin.components;

import com.vaadin.ui.Label;

public class TipiHtmlLabel extends TipiLabel {

	private static final long serialVersionUID = 1561760767992331660L;
	private Label label;

	@Override
	public Object createContainer() {
		label =(Label)super.createContainer();
		label.setContentMode(Label.CONTENT_XHTML);
		return label;
	}





}
