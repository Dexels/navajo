/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.mailmap.AttachementMap;

public interface MailMapInterface {

	public void setIgnoreFailures(boolean b);
	public void setRecipients(String s) ;
	public void setSender(String s) ;
	public void setMailServer(String s);
	public void setSubject(String s);
	public void setContentType(String s);
	public void setXslFile(String s);
	public void setText(String s);
	public void setBcc(String bcc);
	public void setCc(String cc);
	public void setMultipleAttachments(AttachementMap[] c);
	public void setRelatedMultipart(boolean b);
	public void setAttachment(AttachementMap m);
	
}
