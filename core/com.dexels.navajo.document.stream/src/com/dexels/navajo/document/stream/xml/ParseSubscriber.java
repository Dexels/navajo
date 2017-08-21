package com.dexels.navajo.document.stream.xml;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.base.BackpressureAdministrator;
import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;

import io.reactivex.FlowableSubscriber;

public class ParseSubscriber implements FlowableSubscriber<byte[]> {

    final Subscriber<? super XMLEvent> child;
	private final SaxXmlFeeder feeder = new SaxXmlFeeder();
	private BackpressureAdministrator backpressureAdmin;
	
	public ParseSubscriber(Subscriber<? super XMLEvent> child) {
        this.child = child;
	}

	@Override
	public void onSubscribe(Subscription s) {
        this.backpressureAdmin = new BackpressureAdministrator("xmlParse",1, s);
		child.onSubscribe(backpressureAdmin);
		backpressureAdmin.initialize();
	}

	
	@Override
	public void onComplete() {
		feeder.endOfInput();
//		Iterable<XMLEvent> it = feeder.parse(new byte[]{});
//		for (XMLEvent xmlEvent : it) {
//	        queue.offer(xmlEvent);
//		}		
		
		// TODO check for additional events? TEST
		System.err.println("Done!");
		child.onComplete();
//		drain();
	}

	@Override
	public void onError(Throwable t) {
		child.onError(t);
	}

	@Override
	public void onNext(byte[] t) {
		backpressureAdmin.registerIncoming(1);
		Iterable<XMLEvent> it;
		try {
			it = feeder.parse(t);
		} catch (XMLStreamException e) {
			onError(e);
			return;
		}
		int count = 0;
		for (XMLEvent xmlEvent : it) {
			if(xmlEvent==null) {
				continue;
			}
			child.onNext(xmlEvent);
			backpressureAdmin.registerEmission(1);
//			emitted++;
			count++;
		}
		if(count==0) {
			backpressureAdmin.requestSingleFromUpstream();
		} else {
			backpressureAdmin.requestIfNeeded();
		}
	}
}
