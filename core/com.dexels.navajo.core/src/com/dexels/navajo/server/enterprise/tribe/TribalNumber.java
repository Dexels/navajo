package com.dexels.navajo.server.enterprise.tribe;

public interface TribalNumber {

	public long addAddGet(long delta);
	public boolean compareAndSet(long expect, long update);
	public long decrementAndGet();
	public long get();
	public long getAndAdd(long delta);
	public long getAndSet(long value);
	public long incrementAndGet();
	public void set(long value);
	
}
