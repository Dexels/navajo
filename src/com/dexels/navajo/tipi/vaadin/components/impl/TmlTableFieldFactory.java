package com.dexels.navajo.tipi.vaadin.components.impl;

import com.dexels.navajo.tipi.vaadin.document.CompositeArrayContainer;
import com.dexels.navajo.tipi.vaadin.document.SelectedItemValuePropertyBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionBridge;
import com.dexels.navajo.tipi.vaadin.document.SelectionListBridge;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Select;

public class TmlTableFieldFactory extends DefaultFieldFactory {
	private static final long serialVersionUID = -7394569632662794450L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field createdField = super.createField(item, propertyId, uiContext);
		createdField.setCaption("");
		return createdField;
	}

	@Override
	public Field createField(Container container, Object itemId,Object propertyId, Component uiContext) {
		Item message = container.getItem(itemId);
		MessageTable mt = (MessageTable) uiContext;
		CompositeArrayContainer cac = (CompositeArrayContainer) container;
		Integer size = cac.getSizeForColumn((String) propertyId);
		Property property = message.getItemProperty(propertyId);
		if (property instanceof SelectedItemValuePropertyBridge) {
			SelectedItemValuePropertyBridge cmb = (SelectedItemValuePropertyBridge) property;
			if(!cmb.isReadOnly()) {
				return createDropdownBox(cmb);
			}
		}
		// ShirtNumber
		Field createdField = super.createField(container, itemId, propertyId,
				uiContext);
		createdField.setWidth(null);
		createdField.setCaption("");
		if (size != null) {
			// System.err.println("Setting size: "+size);
//			createdField.setWidth(mt.getColumnWidth(propertyId),
//					Sizeable.UNITS_PIXELS);
//			;

		}
		return createdField;
	}

	private Field createDropdownBoxTmp(SelectedItemValuePropertyBridge cmb) {
		IndexedContainer ic = new IndexedContainer();
		ic.addItem("pim");
		ic.addItem("pam");
		ic.addItem("pet");
		final AbstractSelect select = new NativeSelect();

//		
//		final AbstractSelect select = new NativeSelect() {
//			private static final long serialVersionUID = -2068792059283327616L;
//
//			public Object getValue() {
//				return super.getValue();
//			}
//			public void setValue(Object newValue)
//					throws Property.ReadOnlyException,
//					Property.ConversionException {
////				if (newValue == getNullSelectionItemId()) {
////					newValue = null;
////				}
////
////				setValue(newValue, false);
//				if (newValue==null) {
//					System.err.println("Setring to null");
//				} else {
//					System.err.println("Setting to: "+newValue+" newValue: "+newValue.getClass()+" hash: "+newValue.hashCode());
//				}
//				super.setValue("aap");
//			}
//		};
		select.setContainerDataSource(ic);
		
		select.setMultiSelect(false);
		select.setImmediate(true);
		select.setNewItemsAllowed(false);
//		Object obj = select.addItem("aap");
//		System.err.println("obj: " + obj + " hash: " + obj.hashCode());
//		select.addItem("noot");
		select.setValue("pam");
//		select.commit();
		select.setNullSelectionItemId("-");
		Object val = select.getValue();
//		System.err.println("Valll obj: " + val + " hash: " + val.hashCode());
		
		return select;

	}

	private Field createDropdownBox(final SelectedItemValuePropertyBridge cmb) {
		final SelectionListBridge slb = cmb.createListBridge();
		Object found = null;
		for (Object ii : slb.getItemIds()) {
			SelectionBridge iii = (SelectionBridge) slb.getItem(ii);
			System.err.println("ii: " + iii.getClass());
			if (iii.isSelectedBool()) {
				found = iii;
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
