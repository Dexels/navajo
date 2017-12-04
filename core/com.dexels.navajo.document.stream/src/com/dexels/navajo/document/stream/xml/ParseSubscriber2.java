package com.dexels.navajo.document.stream.xml;

import java.util.concurrent.atomic.AtomicLong;

import javax.xml.stream.XMLStreamException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.stream.impl.SaxXmlFeeder;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.util.BackpressureHelper;

public class ParseSubscriber2 implements Subscription, FlowableSubscriber<byte[]> {

    final Subscriber<? super XMLEvent> child;
    final AtomicLong requested;
	long emitted;
	private Subscription parentSubscription;
	private final SaxXmlFeeder feeder = new SaxXmlFeeder();
	
	public ParseSubscriber2(Subscriber<? super XMLEvent> child) {
        this.child = child;
        this.requested = new AtomicLong();
	}

	@Override
	public void onSubscribe(Subscription s) {
		this.parentSubscription = s;
	    child.onSubscribe(this);
        s.request(1);
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
		Iterable<XMLEvent> it;
		try {
			it = feeder.parse(t);
		} catch (XMLStreamException e) {
			child.onError(e);
			return;
		}
		int count = 0;
		for (XMLEvent xmlEvent : it) {
			child.onNext(xmlEvent);
			emitted++;
			count++;
		}
		if(count==0 && !(requested.get()==Long.MAX_VALUE)) {
			parentSubscription.request(1);
		} else {
			requestIfNeeded();
		}
	}


	@Override
	public void cancel() {
        parentSubscription.cancel();
    }

	@Override
	public void request(long n) {
		BackpressureHelper.add(requested, n);
		if(n==Long.MAX_VALUE) {
			parentSubscription.request(Long.MAX_VALUE);
		}
		requestIfNeeded();
	}
	
	public long amountToRequest(long emitted,long requested) {
		if(requested == Long.MAX_VALUE) {
			return 0;
		}
		if(requested > emitted) {
			return requested - emitted;
		}
		return 0;
	}
	
	private void requestIfNeeded() {
		// locking incorrect I think
		long req = amountToRequest(emitted, requested.get());
		if(req>0) {
			parentSubscription.request(req);
		}
	}
}
