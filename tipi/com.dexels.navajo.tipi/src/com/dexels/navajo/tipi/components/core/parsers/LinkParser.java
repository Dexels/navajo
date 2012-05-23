package com.dexels.navajo.tipi.components.core.parsers;

import java.util.StringTokenizer;

import com.dexels.navajo.tipi.PropertyLinkRequest;
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
public class LinkParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9168306770936378642L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		StringTokenizer st = new StringTokenizer(expression, ":");
		String path = st.nextToken();
		String aspect = null;
		if (st.hasMoreElements()) {
			aspect = st.nextToken();
		}
		PropertyLinkRequest r = new PropertyLinkRequest(path, aspect);
		return r;
	}
}