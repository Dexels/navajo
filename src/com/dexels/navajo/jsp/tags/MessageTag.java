package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

public class MessageTag extends BaseNavajoTag {

	private String myMessage;

	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popMessage();
		return 0;
	}

	public void setMessage(String message) {
		myMessage = message;
	}

	public int doStartTag() throws JspException {
			Navajo n;
			Message parent = getNavajoContext().getMessage();
			if(parent!=null) {
				Message mmm = parent.getMessage(myMessage);
//				getNavajoContext().writeMessage(mmm, getPageContext());
				getNavajoContext().pushMessage(mmm);
			}
			n = getNavajoContext().getNavajo();
			Message mmm = n.getMessage(myMessage);
			getNavajoContext().pushMessage(mmm);
//			getNavajoContext().writeMessage(mmm, getPageContext());
		return EVAL_BODY_INCLUDE;
	}
}
