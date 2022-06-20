/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.base;

import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscription;

import io.reactivex.internal.util.BackpressureHelper;

public class BackpressureAdministrator implements Subscription {

	private final long initialRequest;
	private final Subscription downstreamSubsciption;
	private long incoming = 0;
	private final AtomicLong requested = new AtomicLong();

	public BackpressureAdministrator(String operatorName, long initialRequest, Subscription downstreamSubscription) {
		this.initialRequest = initialRequest;
		this.downstreamSubsciption = downstreamSubscription;
	}
	

	public void registerIncoming(int i) {
		incoming += i;
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
		BackpressureHelper.add(requested, n);
		if(n==Long.MAX_VALUE) {
			downstreamSubsciption.request(Long.MAX_VALUE);
		} else {
			requestIfNeeded();
		}
	}
	

	public long amountToRequest(long emitted,long requested) {
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
	}

}
