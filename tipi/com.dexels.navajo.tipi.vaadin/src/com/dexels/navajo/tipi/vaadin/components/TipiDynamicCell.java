package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.TableCell;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;

public class TipiDynamicCell extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TableCell dynamicCellComponent;
	private TipiDynamicTable tablePartner;
	
	
	@Override
	public Object createContainer() {
		dynamicCellComponent = new TableCell(this);
		return dynamicCellComponent;
	}

	public void layoutClick(LayoutClickEvent event) {
		int index = Integer.parseInt(getId());
		tablePartner.layoutClick(index);
	}
	
	public void setComponentValue(final String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("h1")) {
			dynamicCellComponent.setHeaderValue("" + object);
		}
		if (name.equals("h2")) {
			dynamicCellComponent.setSubHeaderValue("" + object);
		}
		if (name.equals("tag")) {
			dynamicCellComponent.setTagValue("" + object);
		}
		if (name.equals("body")) {
			dynamicCellComponent.setBodyValue("" + object);
		}
		if (name.equals("stylePrefix")) {
			dynamicCellComponent.setStylePrefix("" + object);
		}
		
	}


	public void setTablePartner(TipiDynamicTable tablePartner) {
		this.tablePartner = tablePartner;
	}

}
