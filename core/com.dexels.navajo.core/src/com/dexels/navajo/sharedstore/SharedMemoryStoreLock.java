/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedMemoryStoreLock extends SharedStoreLock implements Lock {


	private static final long serialVersionUID = 3528476039156586866L;
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
