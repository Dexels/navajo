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

import java.io.InputStream;
import java.util.Iterator;

import org.apache.camel.impl.DefaultMessage;
import org.apache.james.protocols.smtp.MailAddress;
import org.apache.james.protocols.smtp.MailEnvelope;

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

    public MailEnvelopeMessage(MailEnvelope env) {
        populate(env);
    }
    
    /**
     * Populate the Message with values from the given {@link MailEnvelope}
     * 
     * @param env
     */
    public void populate(MailEnvelope env) {
     
        try {
            setBody(env.getMessageInputStream());
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
    }
}
