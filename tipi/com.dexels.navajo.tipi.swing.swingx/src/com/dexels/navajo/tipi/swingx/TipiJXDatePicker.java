package com.dexels.navajo.tipi.swingx;

import java.beans.*;
import java.util.*;

import org.jdesktop.swingx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.*;

public class TipiJXDatePicker extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = -1234730396121068829L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiJXDatePicker.class);
	@Override
	public Object createContainer() {
		JXDatePicker p = new JXDatePicker();
		p.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				logger.info("Monkey: " + evt.getPropertyName());
			}
		});
		p.setDate(new Date());
		return p;
	}

}
