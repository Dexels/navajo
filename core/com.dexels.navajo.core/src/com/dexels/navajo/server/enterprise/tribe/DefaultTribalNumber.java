package com.dexels.navajo.server.enterprise.tribe;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultTribalNumber implements TribalNumber {

	private final AtomicLong myAtomic;
	
	public DefaultTribalNumber() {
		myAtomic = new AtomicLong();
	}
	
	public long addAddGet(long delta) {
		return myAtomic.addAndGet(delta);
	}

	@Override
	public boolean compareAndSet(long expect, long update) {
		return myAtomic.compareAndSet(expect, update);
	}

	@Override
	public long decrementAndGet() {
		return myAtomic.decrementAndGet();
	}

	@Override
	public long get() {
		return myAtomic.get();
	}

	@Override
	public long getAndAdd(long delta) {
		return myAtomic.getAndAdd(delta);
	}

	@Override
	public long getAndSet(long value) {
		return myAtomic.getAndAdd(value);
	}

	@Override
	public long incrementAndGet() {
		return myAtomic.incrementAndGet();
	}

	@Override
	public void set(long value) {
		myAtomic.set(value);
	}
}
