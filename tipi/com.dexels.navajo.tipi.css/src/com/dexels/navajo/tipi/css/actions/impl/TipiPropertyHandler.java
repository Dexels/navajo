/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.css.actions.impl;

import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.dom.TipiElement;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiPropertyHandler implements ICSSPropertyHandler {

	private final TipiEvent tipiEvent;


	public TipiPropertyHandler(TipiEvent te) {
		this.tipiEvent = te;
	}
	@Override
	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		TipiElement te = (TipiElement)element;
//		TipiComponent tc = (TipiComponent) element;
		TipiComponent tc = (TipiComponent) te.getNativeWidget();
//		Object parsed = tc.evaluateExpression(value.getCssText());
//		String valueString = value.getCssText();
		Operand o = tc.getContext().evaluate(stripQuotes(value.getCssText()), tc, tipiEvent);
		if(o==null) {
			return false;
		}
		Object parsed = o.value;
		//		Object parsed = tc.getContext().evaluateExpression(value.getCssText(), tc, tipiEvent);
		tc.setValue(property, parsed);

		return true;
	}
	
	private String stripQuotes(String label) {
		if(label==null) {
			return null;
		}
		if(label.startsWith("\"") && label.endsWith("\"")) {
			return label.substring(1, label.length()-1);
		}
		return label;
	}

	@Override
	public String retrieveCSSProperty(Object element, String property,
			CSSEngine engine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
