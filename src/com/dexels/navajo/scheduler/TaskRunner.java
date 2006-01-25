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
package com.dexels.navajo.scheduler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

public class TaskRunner implements Runnable {

	private int maxSize = 2;
	private static TaskRunner instance = null;
	private Map tasks = null;
	
	public static TaskRunner getInstance() {
		instance = new TaskRunner();
		Thread thread = new Thread(instance);
	    thread.setDaemon(true);
	    thread.start();
	    instance.tasks = Collections.synchronizedMap(new HashMap());
	    return instance;
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
				// Wait for commands.
				Iterator iter = tasks.values().iterator();
				while ( iter.hasNext() ) {
					Task t = (Task) iter.next();
					System.err.println("Status of task " + t.getId() + " is " + t.isRunning());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public int getTaskListSize() {
		return tasks.size();
	}
	
	public void removeTask(String id) {
		Task t = (Task) tasks.get(id);
		t.setRemove(true);
	}
	
	public boolean addTask(String id, Task t) {
		
		if ( tasks.containsKey(id) ) {
			System.err.println("Task already exists");
			return false;
		}
		
		if ( tasks.size() == maxSize ) {
			System.err.println("Task pool is full");
			return false;
		}
		System.err.println("Addind task: " + id);
		tasks.put(id, t);
		t.setId(id);
		Thread thread = new Thread(t);
		thread.start();
		System.err.println("Leaving addTask");
		return true;
	}
	
	public static void main(String [] args) throws Exception {
		TaskRunner tr = TaskRunner.getInstance();
		System.err.println("STARTED TASKRUNNER");
		Task t1 = new Task("InitBM", "ROOT", "", null, new TimeTrigger(1, 25, 11, 26, null));
		Task t2 = new Task("InitBM", "ROOT", "", null, new TimeTrigger(1, 25, 11, 27, null));
		System.err.println("ABOUT TO ADD TASK T1");
		tr.addTask("myTask", t1);
		System.err.println("ABOUT TO ADD TASK T2");
		tr.addTask("myOtherTask", t2);
		System.err.println("LEAVING MAIN");
	}
}
