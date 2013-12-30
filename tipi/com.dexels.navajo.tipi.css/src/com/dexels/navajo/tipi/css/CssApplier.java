package com.dexels.navajo.tipi.css;

import java.net.URL;
import java.util.List;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public interface CssApplier {
	public void applyCss(TipiComponent component, String styleString, URL styleResource, final TipiEvent event);

	public List<String> getCssDefinitions(String definition);

	public void reloadCssDefinitions(String string);

	public void clearCssDefinitions();

}
