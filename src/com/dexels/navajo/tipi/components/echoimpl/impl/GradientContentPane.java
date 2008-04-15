package com.dexels.navajo.tipi.components.echoimpl.impl;

import com.dexels.navajo.echoclient.components.*;

import nextapp.echo2.app.*;

public class GradientContentPane extends ContentPane {

	public GradientContentPane() {
		Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(getClass(), "Default");
		setStyle(ss);
	}
}
