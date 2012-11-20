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
package org.akrogen.tkui.css.tipi.properties.css2;

import javax.swing.JComponent;
import javax.swing.border.Border;

import org.akrogen.tkui.css.core.dom.properties.CSSBorderProperties;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyBorderHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyBorderHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.impl.dom.properties.CSSBorderPropertiesImpl;
import org.akrogen.tkui.css.tipi.helpers.CSSSwingHelpers;
import org.akrogen.tkui.css.tipi.helpers.TipiElementHelpers;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBorderSwingHandler extends
		AbstractCSSPropertyBorderHandler implements ICSSPropertyHandler2 {

	public final static ICSSPropertyBorderHandler INSTANCE = new CSSPropertyBorderSwingHandler();

	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			CSSBorderProperties border = null;
			// (CSSBorderProperties) component
			// .getClientProperty(CSSSwingConstants.COMPONENT_CSS2BORDER_KEY);
			if (border == null) {
				border = new CSSBorderPropertiesImpl();
				// component.putClientProperty(
				// CSSSwingConstants.COMPONENT_CSS2BORDER_KEY, border);
			}
			super.applyCSSProperty(border, property, value, pseudo, engine);
			return true;
		} else {
			if (element instanceof CSSBorderProperties) {
				super
						.applyCSSProperty(element, property, value, pseudo,
								engine);
				return true;
			}
		}
		return false;

	}

	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null) {
			return super.retrieveCSSProperty(component, property, engine);
		}
		return null;
	}

	public String retrieveCSSPropertyBorderColor(Object element,
			CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null && component instanceof JComponent) {
			Border border = ((JComponent) component).getBorder();
			if (border != null) {
				// Insets insets = border.getBorderInsets(component);
				// if (insets != null) {
				//					
				// }
				return "gray";
			}
		}
		return "";
	}

	public String retrieveCSSPropertyBorderWidth(Object element,
			CSSEngine engine) throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null && component instanceof JComponent) {
			Border border = ((JComponent) component).getBorder();
			if (border != null) {
				return "1";
			}
		}
		return "0";
	}

	public void onAllCSSPropertiesApplyed(Object element, CSSEngine engine)
			throws Exception {
		TipiComponent component = TipiElementHelpers.getComponent(element);
		if (component != null && component instanceof JComponent) {
			CSSBorderProperties border = null;
			// (CSSBorderProperties) component
			// .getClientProperty(CSSSwingConstants.COMPONENT_CSS2BORDER_KEY);
			if (border == null)
				return;
			Border swingBorder = CSSSwingHelpers.getBorder(border, engine
					.getResourcesRegistry());
			((JComponent) component).setBorder(swingBorder);
		}
	}

}
