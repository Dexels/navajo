package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class MessageTag extends BaseNavajoTag {

	private String messageName;
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
		this.messageName = messageName;
	}

	public int doStartTag() throws JspException {
		Navajo n;
		Message parent = getNavajoContext().getMessage();
		if(messageName==null) {
			try {
				getPageContext().getOut().write("Message without name!");
				parent.write(System.err);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else {
		if (message == null) {
			if (parent != null) {
				message = parent.getMessage(messageName);
			} else {
				n = getNavajoContext().getNavajo();
				message = n.getMessage(messageName);
			}
		}
		}
		getNavajoContext().pushMessage(message);

		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void release() {
		message = null; 
		messageName = null;
		super.release();
	}
	
}
