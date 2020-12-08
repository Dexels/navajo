/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components.sort;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/**
 * @deprecated
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

/**
 * @deprecated
 */
@Deprecated
public class PropertyFilter {
	private Property myProperty;
	private String myValue;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyFilter.class);
	
	public PropertyFilter(Property p, String value) {
		myProperty = p;
		myValue = value;
	}

	public boolean compliesWith(Message m) {
		Property p = m.getProperty(myProperty.getName());
		if (p == null) {
			return true;
		}
		if (myProperty.getType().equals(Property.SELECTION_PROPERTY)) {
			Selection s = null;
			try {
				s = p.getSelectionByValue(myValue);
			} catch (NavajoException ex) {
				logger.error("Error: ", ex);
				return true;
			}
			if (s != null) {
				return s.isSelected();
			} else {
				return false;
			}
		}

		return p.getValue().equals(myProperty.getValue());
	}
}
