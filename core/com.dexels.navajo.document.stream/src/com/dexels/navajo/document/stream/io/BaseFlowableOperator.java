/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.io;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.FlowableOperator;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.util.BackpressureHelper;

public abstract class BaseFlowableOperator<R,T> implements FlowableOperator<R,T> {

	public BaseFlowableOperator(int queueSize) {
		queue = new SpscArrayQueue<>(queueSize);
	}
	

	protected final SpscArrayQueue<R> queue;
	protected final AtomicInteger request = new AtomicInteger();
	protected final AtomicLong requested = new AtomicLong();
	protected Subscription subscription;
	protected volatile boolean done;
    protected Throwable error;
    protected volatile boolean cancelled;

	protected void drain(Subscriber<? super R> child) {
	    if (request.getAndIncrement() != 0) {
	        return;
	    }

	    int missed = 1;

	    for (;;) {
	        long r = requested.get();
	        long e = 0L;
	        
	        while (e != r) {
	            if (cancelled) {
	                return;
	            }
	            boolean d = done;

	            if (d) {
	                Throwable ex = error;
	                if (ex != null) {
	                    child.onError(ex);
	                    return;
	                }
	            }

	            R v = queue.poll();
	            boolean empty = v == null;

	            if (d && empty) {
	                child.onComplete();
	                return;
	            }

	            if (empty) {
	                break;
	            }

	            child.onNext(v);
	            
	            e++;
	        }

	        if (e == r) {
	            if (cancelled) {
	                return;
	            }

	            if (done) {
	                Throwable ex = error;
	                if (ex != null) {
	                    child.onError(ex);
	                    return;
	                }
	                if (queue.isEmpty()) {
	                    child.onComplete();
	                    return;
	                }
	            }
	        }

	        if (e != 0L) {
	            BackpressureHelper.produced(requested, e);
	            subscription.request(e);
	        }

	        missed = request.addAndGet(-missed);
	        if (missed == 0) {
	            break;
	        }
	    }
	}				    

	protected void offer(R item) {
		queue.offer(item);
	}
	protected void operatorNext(T input,Function<T,R> f,Subscriber<? super R> child) {
		R result = f.apply(input);
		if (result==null) {
			subscription.request(1);
		} else {
			queue.offer(result);
		}
        drain(child);						
	}

	protected void operatorComplete(Subscriber<? super R> child) {
        done = true;
        drain(child);						
	}

	protected void operatorError(Throwable t,Subscriber<? super R> child) {
        error = t;
        done = true;
        drain(child);						
	}
	
	protected void operatorRequest(long n) {
		subscription.request(n);
	}
		
	protected void operatorSubscribe(Subscription s,Subscriber<? super R> child) {
		subscription = s;
		child.onSubscribe(new Subscription() {
			
			@Override
			public void request(long n) {
		        BackpressureHelper.add(requested, n);
		        drain(child);
			}
			
			@Override
			public void cancel() {
		        cancelled = true;
		        s.cancel();								
			}
		});
		s.request(1);						
	}

}
