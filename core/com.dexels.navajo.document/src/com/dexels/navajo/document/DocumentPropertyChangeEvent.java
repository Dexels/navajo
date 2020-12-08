/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import java.beans.PropertyChangeEvent;

public class DocumentPropertyChangeEvent extends PropertyChangeEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7352694985036390594L;
	private Boolean internal = Boolean.FALSE;

	public DocumentPropertyChangeEvent(Object source, String propertyName,
			Object oldValue, Object newValue) {
		this(source, propertyName, oldValue, newValue, false);
	}

	public DocumentPropertyChangeEvent(Object source, String propertyName,
			Object oldValue, Object newValue, Boolean internal) {
		super(source, propertyName, oldValue, newValue);
		this.internal = internal;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}
}
