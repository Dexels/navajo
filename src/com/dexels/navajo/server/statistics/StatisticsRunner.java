package com.dexels.navajo.server.statistics;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoRequestEvent;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.server.enterprise.statistics.StoreInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.jmx.NavajoNotification;
import com.dexels.navajo.util.AuditLog;

import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.monitor.MonitorNotification;

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

public final class StatisticsRunner extends GenericThread implements StatisticsRunnerMXBean, StatisticsRunnerInterface, NotificationListener {

  public int todoCount;
  public String storeClass;
  public static final String VERSION = "$Id$";
  
  private static volatile StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  @SuppressWarnings("unchecked")
  private Map todo = new HashMap();
  private static String id = "Navajo StatisticsRunner";
  
  private static Object semaphore = new Object();
  private boolean enabled = false;
  
  private int previousSize = 0;
  
  public StatisticsRunner() {
	 super(id);
  }
  
  public final static StatisticsRunner getInstance() {
	  return instance;
  }
  
  /**
   * Get an instance of the StatisticsRunner (singleton).
   *
   * @param storePath the file (or something else, e.g. JDBC url, datasource name, ...) location of the Navajo store.
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  public final static StatisticsRunner getInstance(String storePath, Map parameters, String storeClass) throws Throwable {
	  
	  if ( storeClass == null ) {
		  throw new Exception("No store class specified");
	  }
	  
	  if ( instance != null ) {
		  return instance;
	  }
	  
	  synchronized (semaphore) {
		  if (instance == null) {

			  instance = new StatisticsRunner();
			  instance.enabled = true;
			  JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			  JMXHelper.addGaugeMonitor(instance, JMXHelper.NAVAJO_DOMAIN, id, "TodoCount", new Integer(0), new Integer(30), 10000L);
			  
			  Class si = null;

			  si = Class.forName(storeClass);
			  instance.myStore = (StoreInterface) si.newInstance();
			  instance.myStore.setDatabaseParameters((parameters == null ? new HashMap() : parameters));
			  instance.myStore.setDatabaseUrl(storePath);
			  instance.storeClass = storeClass;

			  instance.setSleepTime(100);
			  instance.startThread(instance);
			  
			  NavajoEventRegistry.getInstance().addListener(NavajoRequestEvent.class, instance);
			  NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, instance);
			  
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
  @SuppressWarnings("unchecked")
  public final void worker() {

	  HashMap copyOfTodo = null;
	  synchronized ( semaphore ) {
		  if ( todo.size() == 0 ) {
			  return;
		  }
		  int level = todo.size();
		  if ( level != previousSize && level > 30 ) {
			  sendHealthCheck(level, 30, NavajoNotification.WARNING, "Todo list of statistics is rather large");
		  }
		  previousSize = level;
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
  @SuppressWarnings("unchecked")
  public final void addAccess(final Access a, final Exception e, AsyncMappable am) {
	  synchronized ( semaphore ) {
		  todo.put( a.accessID, new TodoItem(a, am) );
	  }
	  synchronized (this ) {
		  if ( todo.size() > 10 ) {
			  notifyAll();
		  }
	  }
	 
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
	  instance.enabled = false;
	  instance = null;
	  try {
		  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
	  } catch (Throwable e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Killed");
  }

  public void setEnabled(boolean b) {
	  boolean previousValue = enabled;
	  enabled = b;
	  Notification n = 
          new AttributeChangeNotification(this, 
					    GenericThread.notificationSequence++, 
					    System.currentTimeMillis(), 
					    (b ? "Statistics runner enabled" : "Statistics runner disabled"), 
					    "enabled", 
					    "boolean", 
					    Boolean.valueOf(previousValue), 
					    Boolean.valueOf(enabled)); 

	sendNotification(n); 
  }
  
  public boolean isEnabled() {
	  return enabled;
  }

  /**
   * Handle JMX notifications.
   * If todo size becomes too high, switch off statistics.
   */
  public void handleNotification(Notification notification, Object handback) {

	  if (notification instanceof MonitorNotification) { 

		  MonitorNotification mn = (MonitorNotification) notification;

		  if ( mn.getObservedObject().equals( JMXHelper.getObjectName(JMXHelper.NAVAJO_DOMAIN, id))) {
			  if ( mn.getType().equals("jmx.monitor.gauge.low") ) {
				  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Switching ON Statistics Runner, todo size low again...");
				  StatisticsRunner.getInstance().setEnabled(true);
			  }
			  if ( mn.getType().equals("jmx.monitor.gauge.high") ) {
				  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Switching OFF Statistics Runner, todo size too high");
				  StatisticsRunner.getInstance().setEnabled(false);
			  }
		  }
	  }

  }

  /**
   * Handle Navajo Events: request and response.
   */
  public void onNavajoEvent(NavajoEvent ne) {

	  if ( ne instanceof NavajoResponseEvent ) {
		  NavajoResponseEvent nre = (NavajoResponseEvent) ne;
		  if (  isEnabled() && !Dispatcher.isSpecialwebservice( nre.getAccess().getRpcName() )) {
			  addAccess(nre.getAccess(), nre.getException(), null);
		  }
	  } else if ( ne instanceof NavajoRequestEvent ) {
		  NavajoRequestEvent nre = (NavajoRequestEvent) ne;
	  }
  }
}