package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import echopointng.ContainerEx;

public class TipiColumn extends TipiEchoDataComponentImpl {
	private Column myColumn;

	public Object createContainer() {
		ContainerEx myContainer = new ContainerEx();
		myColumn = new Column();
		// myColumn.setLayoutData(new ColumnLayoutData());
		myContainer.add(myColumn);
		return myContainer;
	}

	public void addToContainer(Object c, Object constraints) {
		Component comp = (Component) c;
		myColumn.add(comp);
		System.err.println(">>>>>>>>>|||||||||||||||||| Added to container...");
	}

	// public Object getActualComponent() {
	// return myColumn;
	// }

}
