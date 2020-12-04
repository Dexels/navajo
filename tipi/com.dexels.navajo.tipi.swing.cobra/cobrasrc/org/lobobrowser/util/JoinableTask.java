/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.util;

/**
 * A task that can be used in a thread or thread pool.
 * The caller can wait for the task to finish by joining it.
 */
public abstract class JoinableTask implements SimpleThreadPoolTask {
	private boolean done = false;
	
	public final void run() {
		try {
			this.execute();
		} finally {
			synchronized(this) {
				this.done = true;
				this.notifyAll();
			}
		}
	}

	public final void forceDone() {
		synchronized(this) {
			this.done = true;
			this.notifyAll();
		}		
	}
	
	public void join() throws InterruptedException {
		synchronized(this) {
			while(!this.done) {
				this.wait();
			}
		}
	}
	
	public void cancel() {		
		this.forceDone();
	}
	
	protected abstract void execute();
}
