/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.DescriptionProvider;
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
public class LabelBorderParser extends TipiTypeParser {

	private static final long serialVersionUID = -6929897138501606067L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(LabelBorderParser.class);
	
	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseBorder(expression,source);
	}

	private Border parseBorder(String s, TipiComponent source) {
		StringTokenizer st = new StringTokenizer(s, "-");
		String borderName = st.nextToken();
		if ("etched".equals(borderName)) {
			return BorderFactory.createEtchedBorder();
		}
		if ("raised".equals(borderName)) {
			return BorderFactory.createRaisedBevelBorder();
		}
		if ("lowered".equals(borderName)) {
			return BorderFactory.createLoweredBevelBorder();
		}
		if ("titled".equals(borderName)) {
			String title = st.nextToken();
			logger.debug("CREATING (LABEL) TITLED BORDER: " + title);
			DescriptionProvider dp = source.getContext().getDescriptionProvider();

			if (dp == null) {
				return BorderFactory.createTitledBorder("[" + title + "]");
			} else {
				return BorderFactory.createTitledBorder(source.getContext()
						.XMLUnescape(dp.getDescription(title)));
			}
		}
		if ("indent".equals(borderName)) {
			try {
				int top = Integer.parseInt(st.nextToken());
				int left = Integer.parseInt(st.nextToken());
				int bottom = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				return BorderFactory
						.createEmptyBorder(top, left, bottom, right);
			} catch (Exception ex) {
				logger.debug("Error while parsing border");
			}
		}
		return BorderFactory.createEmptyBorder();
	}
}
