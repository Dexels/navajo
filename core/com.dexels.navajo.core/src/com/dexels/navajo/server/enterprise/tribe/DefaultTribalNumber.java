/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultTribalNumber implements TribalNumber {

	private final AtomicLong myAtomic;
	private final String myName;
	
	public DefaultTribalNumber() {
		myAtomic = new AtomicLong();
		myName = myAtomic.toString();
	}
	
	@Override
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

	@Override
	public String getName() {
		return myName;
	}
}
