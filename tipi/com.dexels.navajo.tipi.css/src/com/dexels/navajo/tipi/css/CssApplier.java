package com.dexels.navajo.tipi.css;

import java.net.URL;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public interface CssApplier {
	public void applyCss(TipiComponent component, String styleString, URL styleResource, final TipiEvent event);

}
