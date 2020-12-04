/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.impl.TableCell;

public class TipiDynamicCell extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TableCell dynamicCellComponent;
	private TipiDynamicTable tablePartner;
	
	
	@Override
	public Object createContainer() {
		dynamicCellComponent = new TableCell(this);
		return dynamicCellComponent;
	}

	public void layoutClick() {
		int index = Integer.parseInt(getId());
		tablePartner.layoutClick(index);
	}
	
	@Override
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
