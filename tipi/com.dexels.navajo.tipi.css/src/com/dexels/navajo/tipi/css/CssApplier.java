/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
