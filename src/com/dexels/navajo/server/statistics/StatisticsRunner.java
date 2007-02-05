package com.dexels.navajo.server.statistics;

import java.util.HashSet;

import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

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

public final class StatisticsRunner extends GenericThread implements StatisticsRunnerMXBean {

  public int todoCount;
  public String storeClass;
  public static final String VERSION = "$Id$";
  
  private static StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  private Map todo = new HashMap();
  private static String id = "Navajo StatisticsRunner";
  
  private static Object semaphore = new Object();
  
  public StatisticsRunner() {
	 super(id);
  }
  
  public final static StatisticsRunner getInstance() {
	  return instance;
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
	  
	  if ( instance != null ) {
		  return instance;
	  }
	  
	  synchronized (semaphore) {
		  if (instance == null) {
			  
			  instance = new StatisticsRunner();
			  JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			  Class si = null;
			  try {
				  si = Class.forName(storeClass);
				  instance.myStore = (StoreInterface) si.newInstance();
				  instance.myStore.setDatabaseParameters((parameters == null ? new HashMap() : parameters));
				  instance.myStore.setDatabaseUrl(storePath);
				  instance.storeClass = storeClass;
			  }
			  catch (Exception ex) {
				  ex.printStackTrace(System.err);
			  }
			  instance.setSleepTime(100);
			  instance.startThread(instance);
			  System.err.println("Started StatisticsRunner version $Id$ using store: " + instance.myStore.getClass().getName());
		  }
	  }
	  return instance;
  }

  public synchronized void inactive() {
	  try {
		wait(60000);
	} catch (InterruptedException e) {
		
	}
  }
  
  /**
   * Main thread. Responsible for persisting queued access objects.
   *
   */
  public final void worker() {
  
	  HashMap copyOfTodo = null;
	  synchronized ( semaphore ) {
		  if ( todo.size() == 0 ) {
			  return;
		  }
		  copyOfTodo = new HashMap(todo);
		  todo.clear();
	  }

	  myStore.storeAccess(copyOfTodo);
	  
  }

  /**
   * Add an access object to the to-be-persisted-queue.
   *
   * @param a
   */
  public final void addAccess(final Access a, final Exception e, AsyncMappable am) {
	  synchronized ( semaphore ) {
		  todo.put( a.accessID, new TodoItem(a, am) );
	  }
	  synchronized (this ) {
		  if ( todo.size() > 10 ) {
			  notifyAll();
		  }
	  }
	  // Add to webserviceaccesslistener.
	  WebserviceAccessListener.getInstance().addAccess(a, e);
  }
  
  public int getTodoCount() {
	  if ( getInstance() != null ) {
		  return getInstance().todo.size();
	  } else {
		  return 0;
	  }
  }
  
  public String getStoreClass() {
	  if ( getInstance() != null ) {
		  return getInstance().storeClass;
	  } else {
		  return null;
	  }
  }
  
  public String getVERSION() {
	  return VERSION;
  }
  
  public void terminate() {
	  todo.clear();
	  instance = null;
	  try {
		  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
	  } catch (Throwable e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Killed");
  }

}