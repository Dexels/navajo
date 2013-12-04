package com.dexels.navajo.tipi.locale;

import com.dexels.navajo.tipi.TipiContext;

public interface LocaleListener {
	public void localeChanged(TipiContext context, String language, String region);
}
