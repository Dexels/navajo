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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;

import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoHealthCheckEvent;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.jmx.NavajoNotification;

public class GenericThread implements Runnable, Mappable {

	public String myId;
	public boolean killed = false;
	public GenericThread [] allThreads;
	public String status = EMBRYO;
	public long totalWorkTime = 0;
	public long totalSleepTime = 0;
	
	private Thread thread = null;
	private int sleepTime = 1000;
	
	public final static String EMBRYO = "Embryo";
	public final static String SLEEPING = "Sleeping";
	public final static String WORKING = "Working";
	public final static String DEAD = "Zombie";
	public final static String NOTSTARTED = "Not running";
	
	private static Map<String,GenericThread> threadPool = Collections.synchronizedMap(new HashMap<String,GenericThread>());
	
	public GenericThread() {
		myId = "dummy";
	}
	
	public GenericThread(String id) {
		myId = id;
		status = NOTSTARTED;
	}
	
	public void finishThread() {
		System.err.println("Finishing GenericThread: " + myId);
		if ( threadPool != null ) {
			threadPool.remove(myId);
		}
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
	
	public void inactive() {
		// Sleep for a while.
		try {
			Thread.sleep(getSleepTime());
			totalSleepTime += getSleepTime();
		} catch (InterruptedException e) {
		}
	}
	
	public void run() {
		try {
			System.err.println("in run, killed " + killed);
			while ( !killed ) {
				try {
					status = WORKING;
					long start = System.currentTimeMillis();
					worker();
					totalWorkTime += ( System.currentTimeMillis() - start );
				} catch (Throwable t) {
					t.printStackTrace(System.err);
				}
				status = SLEEPING;
				inactive();
			}
			System.err.println("Thread " + myId + " is dying");
			status = DEAD;
		} finally {
			finishThread();
		}
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
	public  void worker() {};
	 
	/**
	 * Implements what to do in case of termination.
	 */
	public  void terminate() {};
	 
	public void kill() {
		killed = true;
		if ( thread != null ) {
			thread.interrupt();
		}
		terminate();
	}
	
	public static void killAllThreads() {

		HashMap<String, GenericThread> copyOfThreadPool = new HashMap<String, GenericThread>(threadPool); 
		Iterator<GenericThread> iter = copyOfThreadPool.values().iterator();
		while ( iter.hasNext() ) {
			GenericThread gt = iter.next();
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
	
	public void load(Access access) throws MappableException, UserException {
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
			return threadPool.values().toArray(all);
		}
		return null;
	}
	
	public long getTotalWorkTime() {
		return this.totalWorkTime;
	}
	
	public long getTotalSleepTime() {
		return this.totalSleepTime;
	}
	
	/**
	 * This method can be used by GenericThread object to signal health problems.
	 * 
	 * @param level
	 * @param warningLevel
	 * @param severity
	 * @param message
	 */
	public final void sendHealthCheck(int level, int warningLevel, Level severity, String message) {

		String m = severity + ":" + "level=" + level + ",warninglevel=" + warningLevel + ",message=" + message;
		NavajoEventRegistry.getInstance().publishAsynchronousEvent(new NavajoHealthCheckEvent(severity, m));

	}

	public Thread getThread() {
		return thread;
	} 

}
