package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.Row;
import nextapp.echo2.app.Style;

import com.dexels.navajo.echoclient.components.Styles;

public class ToolbarImpl extends Row {

	private static final long serialVersionUID = -1211947246421681203L;

	public ToolbarImpl() {
		super();
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(getClass(), "Default");
        setStyle(ss);
//        setInsets(new Insets(3));
	}

	
}
