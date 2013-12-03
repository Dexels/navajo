package com.dexels.navajo.tipi.css;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.css.actions.ComponentTransformerImpl;
import com.dexels.navajo.tipi.css.impl.CssComponentResponderImpl;

public class CssContextExtension implements TipiContextListener {

	@Override
	public void setContext(TipiContext tc) {
		CssComponentResponderImpl cti = new CssComponentResponderImpl(tc);
		tc.addComponentInstantiatedListener(cti);
	}

}
