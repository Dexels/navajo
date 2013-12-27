package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.AttributeRef;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class AttributeRefParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8285931156529653085L;

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getAttributeRefByPath(source, expression);
	}

	private AttributeRef getAttributeRefByPath(TipiComponent source, String path) {
		String componentPath = path.substring(0, path.indexOf(":"));
		String attr = path.substring(path.indexOf(":") + 1);
		TipiComponent tc = getTipiComponent(source, componentPath);
		if (tc == null) {
			source.getContext().showInternalError("Can not parse attributeref: " + path
					+ " component not found.");
			return null;
		}
		AttributeRef attributeRef = tc.getAttributeRef(attr);
		if (attributeRef != null) {
			return attributeRef;
		}
		source.getContext().showInternalError("Can not parse attributeref: " + path
				+ " attribute not found.");
		return null;
	}

}
