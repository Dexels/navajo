/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import com.dexels.navajo.document.Property;

public class NavajoExpression extends NavajoType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098796186928338855L;
	private String contents;
	
	public NavajoExpression(String s) {
		super(Property.EXPRESSION_PROPERTY);
		contents = s.replaceAll("\n", " ");
	}

	@Override
	public String toString() {
		return contents;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
