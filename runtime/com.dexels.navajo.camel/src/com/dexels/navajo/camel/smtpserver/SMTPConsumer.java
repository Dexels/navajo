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

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.james.protocols.api.Protocol;
import org.apache.james.protocols.api.ProtocolServer;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.MailEnvelope;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;
import org.apache.james.protocols.smtp.SMTPSession;
import org.apache.james.protocols.smtp.core.AbstractAuthRequiredToRelayRcptHook;
import org.apache.james.protocols.smtp.hook.HookResult;
import org.apache.james.protocols.smtp.hook.HookReturnCode;
import org.apache.james.protocols.smtp.hook.MessageHook;

/**
 * Consumer which starts an SMTPServer and forward mails to the processer once they are received
 * 
 *
 */
public class SMTPConsumer extends DefaultConsumer {

    private SMTPURIConfiguration config;
    private NettyServer server;
    private SMTPProtocolHandlerChain chain;
    


    public SMTPConsumer(Endpoint endpoint, Processor processor, SMTPURIConfiguration config) {
        super(endpoint, processor);
        this.config = config;

        
    }

    /**
     * Startup the SMTP Server
     */
    @Override
    protected void doStart() throws Exception {
        try {
			super.doStart();
			
			
		    ProtocolServer server;
		      SMTPProtocolHandlerChain chain;
				chain = new SMTPProtocolHandlerChain();
				chain.add(new  AbstractAuthRequiredToRelayRcptHook(){

					@Override
					protected boolean isLocalDomain(String arg0) {
		              return true;
					}});
				chain.add(new ProcessorMessageHook());
				chain.wireExtensibleHandlers();
				final SMTPConfigurationImpl config = new SMTPConfigurationImpl();
				config.setUseAddressBracketsEnforcement(false);
				server =  createServer(new SMTPProtocol(chain, config, new ProtocolLogger(SMTPConsumer.class)), new InetSocketAddress("0.0.0.0", 8025)); // new NettyServer(new SMTPProtocol(chain, new SMTPConfigurationImpl(), new ProtocolLogger(SMTPConsumer.class)));
				server.bind();

			
			
			
			
//			chain = new SMTPProtocolHandlerChain();
//			chain.add(new AllowToRelayHandler());
//			chain.add(new ProcessorMessageHook());
//			chain.wireExtensibleHandlers();
//			server = new NettyServer(new SMTPProtocol(chain, new SMTPConfigurationImpl(), new ProtocolLogger(SMTPConsumer.class)));
//			server.setListenAddresses(new InetSocketAddress(config.getBindIP(), config.getBindPort()));
//			server.bind();
		} catch (Throwable e) {
			e.printStackTrace();
		}
    }

    protected ProtocolServer createServer(Protocol protocol, InetSocketAddress address) {
        server = new NettyServer(protocol);
        server.setListenAddresses(address);
        return server;
    }

    /**
     * Shutdown the SMTPServer
     */
    @Override
    protected void doStop() throws Exception {
        super.doStop();
        server.unbind();
    }

 
    /**
     * Check if the domain is local and if so accept the email. If not reject it
     * 
     *
     */
    private final class AllowToRelayHandler extends AbstractAuthRequiredToRelayRcptHook {

        @Override
        protected boolean isLocalDomain(String domain) {
            List<String> domains = config.getLocalDomains();
            if (domains == null) {
                // no restriction was set.. accept it!
                return true;
            } else {
                return domains.contains(domain.trim());
            }
        }
        
    }
    /**
     * Send the {@link Exchange} to the {@link Processor} after receiving a message via SMTP
     *
     */
    private final class ProcessorMessageHook implements MessageHook {

        /*
         * (non-Javadoc)
         * @see org.apache.james.protocols.smtp.hook.MessageHook#onMessage(org.apache.james.protocols.smtp.SMTPSession, org.apache.james.protocols.smtp.MailEnvelope)
         */
        @Override
		public HookResult onMessage(SMTPSession s, MailEnvelope env) {
            Exchange exchange = getEndpoint().createExchange();
            exchange.setIn(new MailEnvelopeMessage(env));
            try {
                getProcessor().process(exchange);
            } catch (Exception e) {
                return new HookResult(HookReturnCode.DENYSOFT);
            }
            return new HookResult(HookReturnCode.OK);
        }
        
    }

}
