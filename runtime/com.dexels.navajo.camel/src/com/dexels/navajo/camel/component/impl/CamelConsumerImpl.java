package com.dexels.navajo.camel.component.impl;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.spi.Synchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.CamelEndpoint;
import com.dexels.navajo.camel.consumer.NavajoCamelConsumer;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.TmlRunnable;

/**
 * The com.dexels.navajo.camel.component consumer.
 */
public class CamelConsumerImpl extends DefaultConsumer implements Consumer, NavajoCamelConsumer {
    private final CamelEndpoint endpoint;
    
	private final static Logger logger = LoggerFactory
			.getLogger(CamelConsumerImpl.class);
    public CamelConsumerImpl(CamelEndpoint endpoint, Processor processor) {
    	super(endpoint, processor);
        
    	this.endpoint = endpoint;
    	logger.info("Created standard consumer");
    }
    
    
    /* (non-Javadoc)
	 * @see com.dexels.navajo.camel.component.impl.CamelConsumerImpl#process(com.dexels.navajo.script.api.TmlRunnable)
	 */
    @Override
	public void process(final TmlRunnable tr) throws Exception {
		long scheduledAt = System.currentTimeMillis();

    	Exchange ex = endpoint.createExchange();
    	for (String key : tr.getAttributeNames()) {
			ex.setProperty(key, tr.getAttribute(key));
		}
    	ex.setProperty("scheduledAt", scheduledAt);
    	ex.setProperty("asyncRequest", tr.getRequest());
    	ex.setProperty("tmlRunnable", tr);
    	Message m = new DefaultMessage();
    	Navajo in = tr.getInputNavajo();
    	Message out = new DefaultMessage();
    	m.setBody(in);
//    	tr.getInputNavajo();
    	ex.setIn(m);
    	ex.setOut(out);

    	String rpcName = in.getHeader().getRPCName();
    	String rpcUser = in.getHeader().getRPCUser();
    	m.setHeader("rpcName", rpcName);
    	m.setHeader("rpcUser", rpcUser);
    	
    	// TODO add all header attributes as camel headers.
    	
    	ex.addOnCompletion(new Synchronization() {

			@Override
			public void onFailure(Exchange ex) {
				logger.error("Firing on failure: ");
				tr.abort("Routing problem");
			}

			@Override
			public void onComplete(Exchange ex) {
//				logger.error("Firing on complete: ");
//				try {
//					tr.setResponseNavajo((Navajo) ex.getOut().getBody());
//					tr.endTransaction();
//				} catch (IOException e) {
//					logger.error("Network problem when ending transaction",e);
//				}
			}
		});
    	getProcessor().process(ex);
    }


}
