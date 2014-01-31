/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package com.dexels.navajo.camel.smtpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;

import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.camel.impl.DefaultMessage;
import org.apache.james.protocols.smtp.MailAddress;
import org.apache.james.protocols.smtp.MailEnvelope;

import com.dexels.navajo.document.types.Binary;

/**
 * Message implementation which can holds all data for a {@link MailEnvelope}.
 * 
 * The sender, recipient list and message size get stored in the headers.
 * The message itself get stored a {@link InputStream} in the body
 *
 */
public class MailEnvelopeMessage extends DefaultMessage{

    /**
     * String representation of the sender specified in the mail from transmission
     */
    public final static String SMTP_SENDER_ADRRESS = "SMTP_SENDER_ADDRESS";
    
    /**
     * String representation of the recipients specified in the rcpt to transmissions. 
     * The recipients are comma-seperated
     */
    public final static String SMTP_RCPT_ADRRESS_LIST = "SMTP_RCPT_ADRRESS_LIST";
    
    /**
     * The message size
     */
    public final static String SMTP_MESSAGE_SIZE = "SMTP_MESSAGE_SIZE";

    public MailEnvelopeMessage(MailEnvelope env) throws MessagingException, IOException {
        populate(env);
    }
    
    /**
     * Populate the Message with values from the given {@link MailEnvelope}
     * 
     * @param env
     * @throws IOException 
     * @throws MessagingException 
     */
    public void populate(MailEnvelope env) throws MessagingException, IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	copyResource(baos, env.getMessageInputStream());
    	byte[] data = baos.toByteArray();
		MimeMessage msg = new MimeMessage(null, new ByteArrayInputStream(data));
		Enumeration en =  msg.getAllHeaders();
		while (en.hasMoreElements()) {
			Header object = (Header) en.nextElement();
			System.err.println(">>>>>> "+object.getName()+" val: "+object.getValue());
			setHeader(object.getName(), object.getValue());
		}
        try {
            setBody(new ByteArrayInputStream(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setHeader(SMTP_SENDER_ADRRESS, env.getSender().toString());
        
        StringBuilder rcptBuilder = new StringBuilder();
        
        
        Iterator<MailAddress> rcpts = env.getRecipients().iterator();
        while(rcpts.hasNext()) {
            String rcpt = rcpts.next().toString();
            rcptBuilder.append(rcpt);
            if (rcpts.hasNext()) {
                rcptBuilder.append(",");
            }
        }
        setHeader(SMTP_RCPT_ADRRESS_LIST, rcptBuilder.toString());
        setHeader(SMTP_MESSAGE_SIZE, env.getSize());

		MimeMultipart mmp = new MimeMultipart(new InputStreamDataSource(new ByteArrayInputStream(data)));
		System.err.println("PREAMBLE: "+mmp.getPreamble());
		for (int i=0; i<mmp.getCount();i++) {
			BodyPart bp = mmp.getBodyPart(i);
//			String type = bp.getContentType();
//			Binary partBinary = new Binary( bp.getInputStream(),false);
			addAttachment("part"+i, bp.getDataHandler());
			System.err.println("Adding attachment: "+i);
		}
    }
    

	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
			}
			if (read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {

		}
	}
}
