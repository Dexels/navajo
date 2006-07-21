package com.dexels.navajo.server.statistics;

import java.util.HashSet;

import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.util.AuditLog;

import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

import org.apache.log4j.AsyncAppender;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
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

class TodoItem {
	
	public TodoItem(Access a, AsyncMappable am) {
		access = a;
		asyncobject = am;
	}
	
	Access access;
	AsyncMappable asyncobject;
}

public final class StatisticsRunner extends GenericThread {

  private static StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  private Set todo = Collections.synchronizedSet(new HashSet());
  
  private static Object semaphore = new Object();
  
  public StatisticsRunner() {
	 super("Navajo StatisticsRunner");
  }
  
  public final static StatisticsRunner getInstance(String storePath, Map parameters) {
  	return getInstance(storePath, parameters, "com.dexels.navajo.adapter.navajostore.HSQLStore");
  }
  
  /**
   * Get an instance of the StatisticsRunner (singleton).
   *
   * @param storePath the file (or something else, e.g. JDBC url, datasource name, ...) location of the Navajo store.
   *
   * @return
   */
  public final synchronized static StatisticsRunner getInstance(String storePath, Map parameters, String storeClass) {
	  
	  synchronized (semaphore) {
		  if (instance == null) {
			  
			  instance = new StatisticsRunner();
			  Class si = null;
			  try {
				  si = Class.forName(storeClass);
				  instance.myStore = (StoreInterface) si.newInstance();
				  instance.myStore.setDatabaseParameters(parameters);
				  instance.myStore.setDatabaseUrl(storePath);
			  }
			  catch (Exception ex) {
				  ex.printStackTrace(System.err);
			  }
			  instance.setSleepTime(2000);
			  instance.startThread(instance);
			  System.err.println("Started StatisticsRunner version $Id$ using store: " + instance.myStore.getClass().getName());
		  }
	  }
	  return instance;
  }

  /**
   * Main thread. Responsible for persisting queued access objects.
   *
   */
  public final void worker() {
	  
	  // Check for new access objects.
	  //System.err.println(">> StatisticsRunner TODO list size: " + todo.size());
	  synchronized (todo) {
		  Iterator iter = todo.iterator();
		  while (iter.hasNext()) {
			  TodoItem ti = (TodoItem) iter.next();
			  myStore.storeAccess(ti.access, ti.asyncobject);
			  iter.remove();
			  ti = null;
			  if (todo.size() > 50) {
				  System.err.println("WARNING: StatisticsRunner TODO list size:  " + todo.size());
			  }
		  }
	  }
	  
  }

  /**
   * Add an access object to the to-be-persisted-queue.
   *
   * @param a
   */
  public final void addAccess(final Access a, final Exception e, AsyncMappable am) {
    todo.add( new TodoItem(a, am) );
    // Add to webserviceaccesslistener.
    WebserviceAccessListener.getInstance().addAccess(a, e);
  }
  
  
  public void terminate() {
	  todo.clear();
	  instance = null;
	  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Killed");
  }

}