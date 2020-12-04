/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StaticTypeValuePropertyBridge extends ValuePropertyBridge {

	private static final long serialVersionUID = 1316276877973092600L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(StaticTypeValuePropertyBridge.class);
	
	public StaticTypeValuePropertyBridge(com.dexels.navajo.document.Property src, boolean editable) {
		super(src,editable);
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		if(src.isDirOut() || !valueEditable) {
			throw new ReadOnlyException();
		}
		String oldType = src.getType();
		src.setValue(""+newValue);
		String newType = src.getType();
		if(!oldType.equals(newType)) {
			logger.warn("TYPE CHANGED. BAD NEWS. OLD: "+oldType+" new: "+newType);
		}
		// refresh
		this.value = src.getTypedValue();
	}
}
