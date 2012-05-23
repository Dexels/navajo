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

