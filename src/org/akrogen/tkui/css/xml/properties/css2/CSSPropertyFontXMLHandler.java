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
package org.akrogen.tkui.css.xml.properties.css2;

import org.akrogen.tkui.css.core.dom.properties.css2.AbstractCSSPropertyFontHandler;
import org.akrogen.tkui.css.core.dom.properties.css2.ICSSPropertyFontHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

/**
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSPropertyFontXMLHandler extends AbstractCSSPropertyFontHandler {

	public final static ICSSPropertyFontHandler INSTANCE = new CSSPropertyFontXMLHandler();

	public boolean applyCSSProperty(Object node, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (node instanceof Element) {
			super.applyCSSProperty((Element) node, property, value, pseudo,
					engine);
			return true;
		}
		return false;
	}

	public void applyCSSPropertyFontSize(Object node, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Element element = (Element) node;
			// Add size attribute
			int size = (int) ((CSSPrimitiveValue) value)
					.getFloatValue(CSSPrimitiveValue.CSS_PT);
			element.setAttribute("size", size + "");
		}
	}

	public void applyCSSPropertyFontWeight(Object node, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Element element = (Element) node;
			String weight = ((CSSPrimitiveValue) value).getStringValue();
			if ("bold".equals(weight.toLowerCase())) {
				insertElement(element, "b");
			}
		}
	}

	public void applyCSSPropertyFontStyle(Object node, CSSValue value,
			String pseudo, CSSEngine engine) throws Exception {
		if (value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Element element = (Element) node;
			String style = ((CSSPrimitiveValue) value).getStringValue();
			if ("italic".equals(style)) {
				insertElement(element, "i");
			}
		}
	}

	public String retrieveCSSPropertyFontAdjust(Object element, CSSEngine engine)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontFamily(Object element, CSSEngine engine)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontSize(Object element, CSSEngine engine)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontStretch(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontStyle(Object element, CSSEngine engine)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontVariant(Object element,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String retrieveCSSPropertyFontWeight(Object element, CSSEngine engine)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private void insertElement(Element element, String elementName) {
		Element b = element.getOwnerDocument().createElement(elementName);
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			b.appendChild(node.cloneNode(true));
			node.getParentNode().removeChild(node);
		}
		element.appendChild(b);
	}

}
