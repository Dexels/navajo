package com.dexels.navajo.jsp.server.tags;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.jsp.server.NavajoServerContext;
import com.dexels.navajo.jsp.tags.BaseNavajoTag;

/**
 * Maybe we won't need this tag at all..
 * @author frank
 *
 */
public class LocalClientTag extends BaseNavajoTag {

	
	private final static Logger logger = LoggerFactory
			.getLogger(LocalClientTag.class);
	
	public int doStartTag() throws JspException {
		NavajoServerContext nc = (NavajoServerContext) getPageContext().findAttribute("serverContext");
		try {
			if(nc!=null) {
				nc.setupClient();
			} else {
				logger.error("No serverContext found!");
			}
		} catch (Throwable e) {
			logger.error("Error configuring client tag: ",e);
		}
		return EVAL_BODY_INCLUDE;
	}
}
