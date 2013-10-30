package com.dexels.navajo.tipi.actions;

import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/** @todo Refactor, move to NavajoSwingTipi */
public class TipiLog extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4618070574411577748L;

	
	private final static Logger logger = LoggerFactory.getLogger(TipiLog.class);
	
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		Object oo = getEvaluatedParameterValue("text", event);
		String level = (String) getEvaluatedParameterValue("level", event);
		String result = "";
		if (oo == null) {
			result = getText();
			if (result == null) {
				result = "Null value!";
			}
		} else {
			if(oo instanceof Message) {
				Message m = (Message)oo;
				StringWriter sw = new StringWriter();
				m.write(sw);
				result = sw.toString();
			}
	
			result = oo.toString();
		}
		if(level!=null) {
			if(level.equals("debug")) {
				logger.debug(result);
			} else
			if(level.equals("info")) {
				logger.info(result);
			} else
			if(level.equals("warn")) {
				logger.warn(result);
			} else 
			if(level.equals("error")) {
				logger.error(result);
			} else {
				logger.warn("Unsupported log level: "+level);
				logger.warn("Original log request: "+result);
			}
		} else {
			logger.info(result);
		}
//		final String txt = result.replaceAll("\n", " ");
//		myContext.showInfo(txt, "");
	}
}
