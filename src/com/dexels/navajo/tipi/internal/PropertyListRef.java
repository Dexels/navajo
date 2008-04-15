package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PropertyListRef implements TipiReference {
	private final List<Property> propertyList = new ArrayList<Property>();
	public PropertyListRef(List<Property> in) {
		propertyList.addAll(in);
	}

	public void setValue(Object val, TipiComponent source) {
		for (Property p : propertyList) {
			p.setAnyValue(val);
		}
	}



}
