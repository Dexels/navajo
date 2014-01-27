package com.dexels.navajo.camel.smtpserver;

import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.HookReturnCode;
import org.apache.james.protocols.smtp.hook.MessageHook;

public class NullMessageHook implements MessageHook {

	@Override
	public HookResult onMessage(SMTPSession session, MailEnvelope mail) {
		System.err.println("Incoming mail. From: "+mail.getSender()+" to: "+mail.getRecipients());
		
        return new HookResult(HookReturnCode.OK);
   }

}
