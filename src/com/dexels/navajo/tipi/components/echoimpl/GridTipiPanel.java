package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Grid;

import com.dexels.navajo.tipi.components.echoimpl.impl.MailScreen;

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
 * @author Frank Lyaruu
 * @version 1.0
 */

public class GridTipiPanel extends TipiEchoDataComponentImpl {

	public GridTipiPanel() {
	}

	public Object createContainer() {
		// Grid p = new Grid();
		// return p;
		return new MailScreen();
	}

	public void addToContainer(Object o, Object constraints) {
		// GridLayoutData gld = new GridLayoutData();
		// gld.setColumnSpan(2);
		// super.addToContainer(o,gld);
	}

	//
	// public void setContainerLayout(Object l){
	//
	// }

	public void setComponentValue(final String name, final Object object) {
		Grid p = (Grid) getContainer();
		if ("width".equals(name)) {
			p.setSize(((Integer) object).intValue());
		}
		// if ("h".equals(name)) {
		// TipiEchoPanel cont = (TipiEchoPanel) getContainer();
		// cont.setHeight( ( (Integer) object).intValue());
		// }
		super.setComponentValue(name, object);
	}

}
