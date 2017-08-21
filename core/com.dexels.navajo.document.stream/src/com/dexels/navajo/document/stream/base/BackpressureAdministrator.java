package com.dexels.navajo.document.stream.base;

import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscription;

import io.reactivex.internal.util.BackpressureHelper;

public class BackpressureAdministrator implements Subscription {

	private final long initialRequest;
	private final Subscription downstreamSubsciption;
	private final String operatorName;
	private long incoming = 0;
	private final AtomicLong requested = new AtomicLong();

	public BackpressureAdministrator(String operatorName, long initialRequest, Subscription downstreamSubscription) {
		this.initialRequest = initialRequest;
		this.operatorName = operatorName;
		this.downstreamSubsciption = downstreamSubscription;
	}
	

	public void registerIncoming(int i) {
		incoming += i;
		System.err.println("Incoming total: "+incoming);
	}

	public void initialize() {
		this.downstreamSubsciption.request(initialRequest);
	}
	@Override
	public void cancel() {
        downstreamSubsciption.cancel();
    }

	public void consumedEvent() {
		downstreamSubsciption.request(1);
	}
	@Override
	public void request(long n) {
		System.err.println("Request received by: "+this.operatorName+" : "+n);
		BackpressureHelper.add(requested, n);
		if(n==Long.MAX_VALUE) {
			downstreamSubsciption.request(Long.MAX_VALUE);
		} else {
			requestIfNeeded();
		}
	}
	

	public long amountToRequest(long emitted,long requested) {
//		System.err.println("Request: emitted: "+emitted+" requested: "+requested);
		if(requested == Long.MAX_VALUE) {
			return 0;
		}
		if(incoming < requested) {
			return 1;
		}
		return 0;
	}
	
	public void requestIfNeeded() {
		// locking incorrect I think
		long req = amountToRequest(incoming, requested.get());
		System.err.println("Request name: "+this.operatorName+" incoming: "+incoming+" requested: "+requested.get()+" requesting more: "+req);
		if(req>0) {
			downstreamSubsciption.request(req);
			requested.addAndGet(req);
		}
	}

	public void requestSingleFromUpstream() {
		if(!(requested.get()==Long.MAX_VALUE)) {
//			emitted++;
			downstreamSubsciption.request(1);
		}
	}

	public void registerEmission(int i) {
//		System.err.println("WHOOP!");
//		emitted+=i;
		System.err.println("Emitted new items: "+i+" name: "+this.operatorName+" requested: "+requested.get()+" incoming: "+incoming);
	}

}
