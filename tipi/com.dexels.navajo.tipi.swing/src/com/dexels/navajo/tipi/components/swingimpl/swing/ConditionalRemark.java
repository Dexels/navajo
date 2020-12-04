/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiDataComponent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class ConditionalRemark {
	private final TipiDataComponent myComponent;
	private final String remark;
	private final String condition;
	private final String myFont;

	private final String myColor;

	public ConditionalRemark(TipiDataComponent owner, String remark,
			String condition, String color, String font) {
		myComponent = owner;
		this.remark = remark;
		this.condition = condition;
		myColor = color;
		myFont = font;
	}

	public boolean complies() {
		Operand o = myComponent.getContext().evaluate(condition, myComponent,
				null);
		if (o.value != null) {
			Boolean b = (Boolean) o.value;
			return b.booleanValue();
		}
		return false;
	}

	public String getRemark() {
		return remark;
	}

	public String getCondition() {
		return condition;
	}

	public String getColor() {
		return myColor;
	}

	public String getFont() {
		return myFont;
	}
}
