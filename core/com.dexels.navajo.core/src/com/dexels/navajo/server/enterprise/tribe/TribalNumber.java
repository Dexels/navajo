/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	public String getName();
	
}
