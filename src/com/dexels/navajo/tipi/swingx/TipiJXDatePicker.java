package com.dexels.navajo.tipi.swingx;

import java.beans.*;
import java.util.*;

import org.jdesktop.swingx.*;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXDatePicker extends TipiSwingDataComponentImpl {

	@Override
	public Object createContainer() {
		JXDatePicker p = new JXDatePicker();
		p.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("Monkey: " + evt.getPropertyName());
			}
		});
		p.setDate(new Date());
		return p;
	}

}
