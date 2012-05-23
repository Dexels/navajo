package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Style;

import com.dexels.navajo.echoclient.components.Styles;

public class GradientContentPane extends ContentPane {

	private static final long serialVersionUID = 4320779262967456985L;

	public GradientContentPane() {
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(getClass(), "Default");
		setStyle(ss);
	}
}
