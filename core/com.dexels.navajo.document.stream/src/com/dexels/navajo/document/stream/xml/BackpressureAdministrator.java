package com.dexels.navajo.document.stream.xml;

import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.internal.util.BackpressureHelper;

public class BackpressureAdministrator implements Subscription {

	private final long initialRequest;
	private final Subscription parentSubscription;
	private final String operatorName;

	
	private final static Logger logger = LoggerFactory.getLogger(BackpressureAdministrator.class);

    final AtomicLong requested = new AtomicLong();
	long emitted = 0;

	public BackpressureAdministrator(String operatorName, long initialRequest, Subscription parentSubscription) {
		this.initialRequest = initialRequest;
		this.operatorName = operatorName;
		this.parentSubscription = parentSubscription;
	}
	
	public void initialize() {
		this.parentSubscription.request(initialRequest);
	}
	@Override
	public void cancel() {
        parentSubscription.cancel();
    }

	public void consumedEvent() {
		request(1);
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
	
	public void requestIfNeeded() {
		// locking incorrect I think
		long req = amountToRequest(emitted, requested.get());
		logger.info("Request name: {} emitted: {} requested: {} requesting more: {}",this.operatorName,emitted,requested.get(),req);
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
		logger.info("Emitted new items: {} name: {} emitted: {} requested: {}",i,this.operatorName,emitted,requested.get());

		emitted+=i;
	}

}
