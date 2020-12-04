/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.document;

import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public class TipiFieldFactory extends DefaultFieldFactory {

	private static final long serialVersionUID = -7765576973788229067L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
        Class<?> type = item.getItemProperty(propertyId).getType();
//        Field field = createFieldByPropertyType(type);
        if (Boolean.class.isAssignableFrom(type)) {
            return new CheckBox();
        }
        return super.createField(item, propertyId, uiContext);
	}

	
	
}

