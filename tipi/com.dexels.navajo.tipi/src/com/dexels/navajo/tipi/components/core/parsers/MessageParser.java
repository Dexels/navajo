package com.dexels.navajo.tipi.components.core.parsers;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
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
public class MessageParser extends BaseTipiParser {

	private static final long serialVersionUID = 9083510332113488333L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MessageParser.class);
	
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		if (expression.indexOf(":") != -1) {
			StringTokenizer st = new StringTokenizer(expression, ":");
			String navajo = st.nextToken();
			String path = st.nextToken();
			Navajo nn = source.getContext().getNavajo(navajo);
			try {
				Message myMessage = nn.getMessage(path);
				return myMessage;
			} catch (Throwable e) {
				logger.error("Message not found by MessageParser: " + expression,e);
				return null;
			}
		}
		Message m = getMessageByPath(source, expression);
		// if (m != null) {
		// m.write(System.err);
		// }
		return m;
	}

	public String toString(Object o, TipiComponent source) {
		return "Not possible";
	}
}
