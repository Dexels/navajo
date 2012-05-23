/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.tipi.properties.css2.lazy.border;

import java.awt.Component;

import javax.swing.border.Border;

import org.akrogen.tkui.css.core.dom.properties.CSSBorderProperties;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingHelpers;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBorderSwingHandler2 implements ICSSPropertyHandler2 {

	public static final ICSSPropertyHandler2 INSTANCE = new CSSPropertyBorderSwingHandler2();

	public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			CSSBorderProperties border = null;
			// (CSSBorderProperties) component
			// .getClientProperty(CSSSwingConstants.COMPONENT_CSS2BORDER_KEY);
			if (border == null)
				return;
			Border swingBorder = CSSSwingHelpers.getBorder(border, engine
					.getResourcesRegistry());
			// component.setBorder(swingBorder);
		}
	}

}
