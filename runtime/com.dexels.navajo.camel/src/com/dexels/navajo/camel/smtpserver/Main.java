package com.dexels.navajo.camel.smtpserver;

import java.net.InetSocketAddress;

import org.apache.james.protocols.api.Protocol;
import org.apache.james.protocols.api.ProtocolServer;
import org.apache.james.protocols.netty.NettyServer;
import org.apache.james.protocols.smtp.SMTPConfigurationImpl;
import org.apache.james.protocols.smtp.SMTPProtocol;
import org.apache.james.protocols.smtp.SMTPProtocolHandlerChain;
import org.apache.james.protocols.smtp.core.AbstractAuthRequiredToRelayRcptHook;

public class Main {
    public static void main(String[] args) throws Exception {
    	
//      SMTPURIConfiguration config;
      ProtocolServer server;
      SMTPProtocolHandlerChain chain;
		chain = new SMTPProtocolHandlerChain();
		chain.add(new  AbstractAuthRequiredToRelayRcptHook(){

			@Override
			protected boolean isLocalDomain(String arg0) {
              return true;
			}});
		chain.add(new NullMessageHook());
		chain.wireExtensibleHandlers();
		final SMTPConfigurationImpl config = new SMTPConfigurationImpl();
		config.setUseAddressBracketsEnforcement(false);
		server =  createServer(new SMTPProtocol(chain, config, new ProtocolLogger(SMTPConsumer.class)), new InetSocketAddress("0.0.0.0", 8025)); // new NettyServer(new SMTPProtocol(chain, new SMTPConfigurationImpl(), new ProtocolLogger(SMTPConsumer.class)));
		server.bind();
		System.err.println("bind complete.");
  }
    protected static ProtocolServer createServer(Protocol protocol, InetSocketAddress address) {
        NettyServer server =  new NettyServer(protocol);
        server.setListenAddresses(address);
        return server;
    }
}
