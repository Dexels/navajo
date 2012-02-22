package com.dexels.navajo.tipi.vaadin.components.impl;

import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.tipi.vaadin.document.CompositeArrayContainer;
import com.dexels.navajo.tipi.vaadin.document.SelectedItemValuePropertyBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionListBridge;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;

public class TmlTableFieldFactory extends DefaultFieldFactory {
	private static final long serialVersionUID = -7394569632662794450L;
	private final Table myTable;
	private final static Logger logger = LoggerFactory
			.getLogger(TmlTableFieldFactory.class);
	public TmlTableFieldFactory(Table tipiTable) {
		// TODO Auto-generated constructor stub

		this.myTable = tipiTable;
		
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field createdField = super.createField(item, propertyId, uiContext);
		createdField.setCaption("");
		return createdField;
	}

	@Override
	public Field createField(Container container, Object itemId,Object propertyId, Component uiContext) {
		Item message = container.getItem(itemId);
//		MessageTable mt = (MessageTable) uiContext;
		CompositeArrayContainer cac = (CompositeArrayContainer) container;
		Integer size = cac.getSizeForColumn((String) propertyId);
		Property property = message.getItemProperty(propertyId);
		if (property instanceof SelectedItemValuePropertyBridge) {
			SelectedItemValuePropertyBridge cmb = (SelectedItemValuePropertyBridge) property;
			if(!cmb.isReadOnly()) {
				return createDropdownBox(cmb);
			}
		}
        Property containerProperty = container.getContainerProperty(itemId,
                propertyId);
        Class<?> type = containerProperty.getType();
        if(type!=null && ClockTime.class.isAssignableFrom(type)) {
        	ClockTime ct = (ClockTime)message.getItemProperty(propertyId).getValue();
            ct.setShortFormat(true);
            //            
//            TextField tf = new TextField();
//            tf.setPropertyDataSource(new PropertyFormatter(property) {
//				private static final long serialVersionUID = 1L;
//
//				public String format(Object value) {
//                    return "000000000";
//                }
//
//                public Object parse(String formattedValue) throws Exception {
//                    return 123;
//                }

//            });
//            tf.setCaption("oempaloempa");
//    		return tf;
        }
        
        if (Date.class.isAssignableFrom(type)) {
            final DateField df = new DateField();
            df.setResolution(DateField.RESOLUTION_DAY);
            df.setLocale(new Locale("nl","NL"));
            df.setDateFormat("dd/MM/yy");
            return df;
        }
		Field createdField = super.createField(container, itemId, propertyId,
				uiContext);
		createdField.setWidth("600px");
		createdField.setCaption("");
		logger.info("Readonly: "+containerProperty.isReadOnly());
		if (size != null) {
			// System.err.println("Setting size: "+size);
//			createdField.setWidth(mt.getColumnWidth(propertyId),
//					Sizeable.UNITS_PIXELS);
//			;

		}
//		Label l = new Label(containerProperty);
//		return l;
		return createdField;
	}


	private Field createDropdownBox(final SelectedItemValuePropertyBridge cmb) {
		final SelectionListBridge slb = cmb.createListBridge();
//		Object found = null;
		for (Object ii : slb.getItemIds()) {
			SelectionBridge iii = (SelectionBridge) slb.getItem(ii);
			System.err.println("ii: " + iii.getClass());
			if (iii.isSelectedBool()) {
//				found = iii;
			}

		}
		final AbstractSelect select = new NativeSelect("Omm", slb);
		select.setNullSelectionItemId("-");
		select.setImmediate(true);
		select.setNewItemsAllowed(false);
		SelectionBridge selected = slb.getSelected();
		if (selected != null) {
			Property selectedName = selected.getItemProperty("name");
			System.err.println("SelectedNAme: " + selectedName.getValue());
			select.setValue(selected);
		}
//		SelectionBridge sb = (SelectionBridge) slb.addItem("konijn");
		select.setPropertyDataSource(cmb);
//		System.err.println(">> " + sb.getItemPropertyIds());
//		cmb.setValue("konijn");
		// select.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		// select.setItemCaptionPropertyId("name");
		// select.setPropertyDataSource(cmb);
		// cmb.setValue(slb.getSource().getSelected());
		// Item id = select.getItem("Ja");
		//
		// if(found!=null) {
		// System.err.println("SELECTED: "+found);
		// select.setValue(found);
		// select.commit();
		// }
		//
		select.addListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = -4325015230483421707L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// Selection property = (Selection) select.getValue();
				// System.err.println("class: "+property.getClass()+" val: "+property);
				// System.err.println("Selection changed: "+property);
				// SelectedItemValuePropertyBridge svpb =
				// (SelectedItemValuePropertyBridge) property;
				// Selection ss = (Selection) svpb.getValue();
				// System.err.println("Sel: "+ss);
			}

		});
		return select;
	}

}
