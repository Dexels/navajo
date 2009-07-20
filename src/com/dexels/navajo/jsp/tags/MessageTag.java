package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

public class MessageTag extends BaseNavajoTag {

	private String messsageName;
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popMessage();
		return 0;
	}

	public void setMessageName(String messageName) {
		messsageName = messageName;
	}

	public int doStartTag() throws JspException {
			Navajo n;
			Message parent = getNavajoContext().getMessage();
			if(message==null) {
				if(parent!=null) {
					message = parent.getMessage(messsageName);
				} else {
					n = getNavajoContext().getNavajo();
					message = n.getMessage(messsageName);
				}
			}
			getNavajoContext().pushMessage(message);
			
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void release() {
		message = null; 
		messsageName = null;
		super.release();
	}
	
}
