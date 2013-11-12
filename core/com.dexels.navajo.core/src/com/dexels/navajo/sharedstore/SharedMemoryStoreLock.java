package com.dexels.navajo.sharedstore;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemoryStoreLock extends SharedStoreLock implements Lock {

	private final Lock myLock = new ReentrantLock();
	
	public SharedMemoryStoreLock(String name, String parent) {
		super(name, parent);
	}
	
	@Override
	public void lock() {
		myLock.lock();
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		myLock.lockInterruptibly();	
	}

	@Override
	public Condition newCondition() {
		return myLock.newCondition();
	}

	@Override
	public boolean tryLock() {
		return myLock.tryLock();
	}

	@Override
	public boolean tryLock(long timeout, TimeUnit unit)
			throws InterruptedException {
		return myLock.tryLock(timeout, unit);
	}

	@Override
	public void unlock() {
		myLock.unlock();
	}
}
