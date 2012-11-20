package com.dexels.navajo.tipi.components.swingimpl.parsers;

import java.awt.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
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
public class PaintParser extends TipiTypeParser {

	private static final long serialVersionUID = 3869299878939316108L;
	private String GRADIENT = "gradient";
	private ColorParser cp = new ColorParser();
	
	private final static Logger logger = LoggerFactory.getLogger(PaintParser.class);
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parsePaint(expression);
	}

	private TipiGradientPaint parsePaint(String s) {
		TipiGradientPaint p = null;
		if (s.startsWith(GRADIENT)) {
			try {
				String def = s.substring(s.indexOf("-") + 1);
				String direction = def.substring(0, def.indexOf("-"));
				def = def.substring(def.indexOf("-") + 1);
				String colorOne = def.substring(0, def.indexOf("-"));
				String colorTwo = def.substring(def.indexOf("-") + 1);
				Color c1 = (Color) cp.parse(null, colorOne, null);
				Color c2 = (Color) cp.parse(null, colorTwo, null);
				p = new TipiGradientPaint(direction, c1, c2);
			} catch (Exception e) {
				logger.warn("Could not construct Paint object. returning Color.gray");
				return p;
			}
		}
		return p;
	}

	public static TipiGradientPaint parse(String s) {
		logger.debug("PARSEDPAINT!!!!!: " + s);
		return new PaintParser().parsePaint(s);
	}
}
