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
	private int messageIndex = -1;
	
	public int getMessageIndex() {
		return messageIndex;
	}

	public void setMessageIndex(int index) {
		this.messageIndex = index;
	}

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
		if(messageName!=null) {
			if(messageIndex>-1) {
				throw new JspException("Either set messageName or index, not both");
			}
//			if (message == null) {
				System.err.println("No message");
				if (parent != null) {
					message = parent.getMessage(messageName);
				} else {
					n = getNavajoContext().getNavajo();
					message = n.getMessage(messageName);
				}
//			} else {
//			}
			if(message==null) {
				System.err.println("Warning, no message found at: "+messageName);
			}
		}
		if(messageIndex>-1) {
			message = parent.getMessage(messageIndex);
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
