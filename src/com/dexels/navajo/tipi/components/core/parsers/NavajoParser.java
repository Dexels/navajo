package com.dexels.navajo.tipi.components.core.parsers;

import java.util.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

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

public class NavajoParser extends TipiTypeParser {
	public NavajoParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		// System.err.println("Parsing navajo expression: "+expression);
		if (".".equals(expression)) {
			System.err.println("Home navajo found. Component path: " + source.getPath());
			return source.getValue(expression);
		}
		if (expression.indexOf(':') != -1) {
			StringTokenizer st = new StringTokenizer(expression, ":");
			String componentPath = st.nextToken();
			String attribute = st.nextToken();
			TipiComponent tc = source.getTipiComponentByPath(componentPath);
			if (tc == null) {
				throw new IllegalArgumentException("Error addressing navajo: Component not found: " + componentPath
						+ " original expression: " + expression);
			}
			return tc.getValue(attribute);
		}
		return myContext.getNavajo(expression);
	}
}
