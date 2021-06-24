/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
		child.onComplete();
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
