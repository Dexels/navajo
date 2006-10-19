/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.server;

import java.util.HashMap;
import java.util.Iterator;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

public class GenericThread implements Runnable, Mappable {

	public String myId;
	public boolean killed = false;
	public GenericThread [] allThreads;
	public String status = EMBRYO;
	public long totalWorkTime = 0;
	public long totalSleepTime = 0;
	
	private Thread thread = null;
	private int sleepTime = 1000;
	
	private final static String EMBRYO = "Embryo";
	private final static String SLEEPING = "Sleeping";
	private final static String WORKING = "Working";
	private final static String DEAD = "Zombie";
	private final static String NOTSTARTED = "Not running";
	
	private static HashMap threadPool = new HashMap();
	
	public GenericThread() {
		// dummy.
		status = NOTSTARTED;
	}
	
	public GenericThread(String id) {
		myId = id;
		status = NOTSTARTED;
	}
	
	public void startThread(GenericThread instance) {
		
		thread = new Thread(instance,myId);
		thread.setDaemon(true);
		thread.start();
		
		threadPool.put(instance.myId, instance);
	
	}
	
	public void wakeUp() {
		if ( thread != null ) {
			thread.interrupt();
		}
	}
	
	public void run() {

		while ( !killed ) {
			try {
				status = WORKING;
				long start = System.currentTimeMillis();
				worker();
				totalWorkTime += ( System.currentTimeMillis() - start );
			} catch (Throwable t) {
				t.printStackTrace(System.err);
			}
			// Sleep for a while.
			try {
				status = SLEEPING;
				Thread.sleep(getSleepTime());
				totalSleepTime += getSleepTime();
			} catch (InterruptedException e) {
			}
		}
		status = DEAD;
	}
	
	public final void setSleepTime(int i) {
		sleepTime = i;
	}
	
	public final int getSleepTime() {
		return sleepTime;
	}
	
	/**
	 * Implements work.
	 */
	public void worker() 
	  { // implement this. 
	  }
	/**
	 * Implements what to do in case of termination.
	 */
	public void terminate()
	  { // implement this. 
	  }
	
	public void kill() {
		killed = true;
		if ( thread != null ) {
			thread.interrupt();
		}
		terminate();
	}
	
	public static void killAllThreads() {
		Iterator iter = threadPool.values().iterator();
		while ( iter.hasNext() ) {
			GenericThread gt = (GenericThread) iter.next();
			if ( gt.thread != null ) {
				try {
					gt.kill();
				} catch (Throwable t) {
					t.printStackTrace(System.err);
				}
			}
		}
		threadPool.clear();
	}
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}
	
	public void setKilled(boolean b) {
		if ( b ) {
			kill();
		}
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getMyId() {
		return this.myId;
	}
	
	public GenericThread [] getAllThreads() {
		if ( threadPool.size() > 0 ) {
			GenericThread [] all = new GenericThread[threadPool.size()];
			return (GenericThread []) threadPool.values().toArray(all);
		}
		return null;
	}
	
	public long getTotalWorkTime() {
		return this.totalWorkTime;
	}
	
	public long getTotalSleepTime() {
		return this.totalSleepTime;
	}

}
