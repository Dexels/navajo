package com.dexels.navajo.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.impl.CamelConsumerImpl;
import com.dexels.navajo.camel.message.NavajoMessage;

/**
 * Represents a com.dexels.navajo.camel.component endpoint.
 */
public class CamelEndpoint extends DefaultEndpoint {

	private String instance;

	private final static Logger logger = LoggerFactory
			.getLogger(CamelEndpoint.class);

	private final NavajoCamelComponent myComponent;
	
	public CamelEndpoint(String uri, NavajoCamelComponent component) {
		super(uri, component);
		myComponent = component;
		logger.info("Endpoint created with URI: " + uri);
	}

	public void setInstance(String instance) {
		logger.info("Instance set to: " + instance);
		this.instance = instance;
	}

	public Producer createProducer() throws Exception {
		return new NavajoCamelProducer(this);
	}

	public Consumer createConsumer(Processor processor) throws Exception {
		final CamelConsumerImpl camelConsumerImpl = new CamelConsumerImpl(this, processor);
		myComponent.registerConsumer(camelConsumerImpl, getId());
		return camelConsumerImpl;
	}

	public boolean isSingleton() {
		return true;
	}

//	public Exchange createFakeNavajoExchange() {
//		Exchange e = createExchange();
//		Message m = new DefaultMessage();
//		e.setIn(m);
//		e.addOnCompletion(new Synchronization() {
//
//			@Override
//			public void onFailure(Exchange ex) {
//				logger.error("Firing on failure: ");
//
//			}
//
//			@Override
//			public void onComplete(Exchange ex) {
//				logger.error("Firing on complete: ");
//			}
//		});
//		return e;
//	}

//	public Exchange createNavajoExchange(final TmlRunnable tml) {
//
//		Exchange e = createExchange();
//		Message m = new DefaultMessage();
//		e.setIn(m);
//		try {
//			m.setBody(tml.getInputNavajo());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//
//		e.addOnCompletion(new Synchronization() {
//
//			@Override
//			public void onFailure(Exchange ex) {
//				logger.error("Firing on faillure: ");
//				tml.abort("trouble");
//			}
//
//			@Override
//			public void onComplete(Exchange ex) {
//				try {
//					logger.error("Firing on complete: ");
//					tml.endTransaction();
//				} catch (IOException e) {
//					logger.error("Error ending transaction.");
//				}
//
//			}
//		});
//		return e;

//	}

	@Override
	public Exchange createExchange() {
		Exchange e = super.createExchange();
		e.setIn(new NavajoMessage());
		return e;
		
	}

}
