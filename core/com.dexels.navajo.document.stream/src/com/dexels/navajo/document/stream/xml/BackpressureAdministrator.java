package com.dexels.navajo.document.stream.xml;

import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscription;

import io.reactivex.internal.util.BackpressureHelper;

public class BackpressureAdministrator implements Subscription {

	private final int initialRequest;
	private final Subscription parentSubscription;

    final AtomicLong requested = new AtomicLong();
	long emitted = 0;

	public BackpressureAdministrator(int initialRequest, Subscription parentSubscription) {
		this.initialRequest = initialRequest;
		this.parentSubscription = parentSubscription;
	}
	
	public void initialize() {
		this.parentSubscription.request(initialRequest);
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
		} else {
			requestIfNeeded();
		}
	}
	

	public long amountToRequest(long emitted,long requested) {
//		System.err.println("Request: emitted: "+emitted+" requested: "+requested);
		if(requested == Long.MAX_VALUE) {
			return 0;
		}
		if(requested > emitted) {
			return requested - emitted;
		}
		return 0;
	}
	
	void requestIfNeeded() {
		// locking incorrect I think
		long req = amountToRequest(emitted, requested.get());
		if(req>0) {
			parentSubscription.request(req);
		}
	}

	public void emitSingle() {
		if(!(requested.get()==Long.MAX_VALUE)) {
			parentSubscription.request(1);
		}
	}

	public void registerEmission(int i) {
		emitted+=i;
	}

}
