package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
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
public class AttributeParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6886949527263989746L;

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getAttributeByPath(source, expression);
	}

	private Object getAttributeByPath(TipiComponent source, String path) {
		String componentPath = path.substring(0, path.indexOf(":"));
		String attr = path.substring(path.indexOf(":") + 1);
		TipiComponent tc = getTipiComponent(source, componentPath);
		if (tc == null) {
			source.getContext().showInternalError("Can not parse attribute: " + path
					+ " component not found.");
			return null;
		}

		Object o = tc.getValue(attr);

		return o;
	}
}
