package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

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
		if(message!=null) {
			getNavajoContext().popMessage();
		}
		return EVAL_PAGE;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public int doStartTag() throws JspException {
		Navajo n = null;
		Message parent = null;
		
		Object top = getNavajoContext().peek();
		if(top instanceof Message) {
			parent = (Message)top;
		} else {
			n = (Navajo)top;
		}
//		parent = getNavajoContext().getMessage();
		if(messageName!=null) {
			if(messageIndex>-1) {
				throw new JspException("Either set messageName or index, not both");
			}
				if (parent != null) {
					message = parent.getMessage(messageName);
				} else {
//					n = getNavajoContext().getNavajo();
					if(n!=null) {
						message = n.getMessage(messageName);
					}
				}
			if(message==null) {
				System.err.println("Warning, no message found at: "+messageName);
				return EVAL_BODY_INCLUDE;
			}
		}
		if(messageIndex>-1) {
			if(parent!=null) {
				message = parent.getMessage(messageIndex);
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
