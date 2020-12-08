/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class MapItem extends Object implements Item {

	private static final long serialVersionUID = 3106826560149876082L;
//	private final Map<String,String> mymap = new HashMap<String, String>();
	@Override
	public Property getItemProperty(Object id) {
		
		return null;
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return null;
	}

	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		return false;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		return false;
	}

}
