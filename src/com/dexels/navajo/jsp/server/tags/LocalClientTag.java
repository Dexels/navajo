package com.dexels.navajo.jsp.server.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.jsp.server.NavajoServerContext;
import com.dexels.navajo.jsp.tags.BaseNavajoTag;

/**
 * Maybe we won't need this tag at all..
 * @author frank
 *
 */
public class LocalClientTag extends BaseNavajoTag {

	
	public int doStartTag() throws JspException {
		NavajoServerContext nc = (NavajoServerContext) getPageContext().findAttribute("serverContext");
		try {
			if(nc!=null) {
				nc.setupClient();
			} else {
				System.err.println("No serverContext found!s");
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		getPageContext().setAttribute("Aap", "Noot");
		return EVAL_BODY_INCLUDE;
	}
}
